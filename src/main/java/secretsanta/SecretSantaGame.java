package secretsanta;

import java.util.*;

import secretsanta.emails.EmailSender;
import secretsanta.model.User;
import secretsanta.rules.SantaRule;
import secretsanta.rules.SantaRuleChecker;

public class SecretSantaGame {

	public static final int MIN_USERS_COUNT = 3;
	public static final int MAX_RECEIVER_RULE_CHECKS = 1000;

	private final List<User> users;
	private final SantaRuleChecker santaRuleChecker;
	private final EmailSender emailSender;

	private final Map<User, User> secretSantaPairs;
	final List<User> alreadyAssignedReceivers;

	public SecretSantaGame(List<User> users, List<SantaRule> rules, EmailSender emailSender) {
		this.secretSantaPairs = new HashMap<>();
		this.alreadyAssignedReceivers = new ArrayList<>();
		this.users = Objects.requireNonNull(users, "Users should not be null.");
		this.emailSender = Objects.requireNonNull(emailSender, "Email sender should not be null.");
		this.santaRuleChecker = new SantaRuleChecker(Objects.requireNonNull(rules, "Rules should not be null."));
	}

	/**
	 * Sends a random user name via email to each participant of the secret santa tradition.
	 * The pair of giver and receiver can be influenced with a custom set of secret santa rules
	 * which should be checked during the creation of each user pair.
	 */
	public void run() {
		if (this.users.size() < MIN_USERS_COUNT) {
			throw new IllegalStateException(String.format("You need at least %s recipients to play secret santa", MIN_USERS_COUNT));
		}

		final Map<User, User> giftPairs = createGiverReceiverPairs(this.users);
		sendReceiverNamesToGivers(giftPairs);
	}

	public static User getRandomUser(final List<User> users) {
		return users.get(new Random().nextInt(users.size()));
	}

	private Map<User, User> createGiverReceiverPairs(List<User> users) {
		for (final User giver : users) {
			pairGiverWithRandomReceiver(giver);
		}

		return secretSantaPairs;
	}

	private void pairGiverWithRandomReceiver(final User giver) {
		int receiverTried = 0;
		while (true) {
			if (receiverTried > MAX_RECEIVER_RULE_CHECKS) {
				throw new IllegalStateException(String.format("Cannot find valid receiver within %s tries.", MAX_RECEIVER_RULE_CHECKS));
			}

			final User receiver = getRandomUser(this.users);
			final boolean santaRuleFulfilled = santaRuleChecker.check(giver, receiver, this.alreadyAssignedReceivers);
			if (santaRuleFulfilled) {
				this.alreadyAssignedReceivers.add(receiver);
				this.secretSantaPairs.put(giver, receiver);
				break;
			}
			receiverTried++;
		}
	}

	private void sendReceiverNamesToGivers(final Map<User, User> userPair) {
		for (final Map.Entry<User, User> secretSantaPair : userPair.entrySet()) {
			final User giver = secretSantaPair.getKey();
			final User receiver = secretSantaPair.getValue();

			sendReceiverNameToGiver(giver, receiver);
		}
	}

	private void sendReceiverNameToGiver(final User giver, final User receiver) {
		emailSender.sendEmail(giver.getUserEmail(), "You will be the secret santa for ...", receiver.getUserName());
	}
}
