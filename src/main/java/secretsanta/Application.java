package secretsanta;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import secretsanta.emails.EmailSender;
import secretsanta.rules.NotSamePersonRule;
import secretsanta.rules.NotSameSenderTwiceRule;
import secretsanta.rules.NotSpecificCombinationRule;
import secretsanta.rules.SantaRule;
import secretsanta.rules.SantaRuleChecker;

public class Application {

	/* Change the following properties */

	private final static String EMAIL_USERNAME = "<your username>"; // used to authenticate
	private final static String EMAIL_PASSWORD = "<your password>"; // used to authenticate
	private final static String EMAIL_HOST = "your host server"; // host f.e. mail.gmx.net
	private final static String DISPLAYED_EMAIL_ADDRESS = "<anonymous email address>"; // could be some throw away email
	private final static String DISPLAYED_PERSONAL = "<anonymous personal>"; // could be any string

	private final static List<SantaRule> RULES = Arrays.asList( //
			new NotSamePersonRule(), //
			new NotSameSenderTwiceRule(), //
			new NotSpecificCombinationRule("email address 2", "email address 1"));
	private final static Map<String, String> PERSONS = Map.ofEntries( // f.e. entry("max.muster@muster.de", "max")
			entry("email address 1", "nickname 1"), //
			entry("email address 2", "nickname 2"), //
			entry("email address 3", "nickname 3"), //
			entry("email address 4", "nickname 4"));

	public void run() {

		if (PERSONS.size() < 3) {
			throw new IllegalStateException("You need at least three recipients to play secret santa");
		}

		final Map<String, String> secretSantaPairs = new HashMap<>();
		final List<String> assignedSenders = new ArrayList<>();

		for (final Map.Entry<String, String> recipient : PERSONS.entrySet()) {
			while (true) {
				// chose sender randomly
				final String chosenSender = new ArrayList<>(PERSONS.entrySet())
						.get(new Random().nextInt(PERSONS.size())).getKey();

				final SantaRuleChecker santaRuleChecker = new SantaRuleChecker(RULES);
				// check if all rules can be fulfilled for random chosen sender
				if (santaRuleChecker.check(chosenSender, recipient.getKey(), assignedSenders)) {
					assignedSenders.add(chosenSender);
					secretSantaPairs.put(chosenSender, recipient.getValue());
					break;
				}
			}
		}

		final EmailSender emailSender = new EmailSender( //
				EMAIL_USERNAME, //
				EMAIL_PASSWORD, //
				EMAIL_HOST, //
				DISPLAYED_EMAIL_ADDRESS, //
				DISPLAYED_PERSONAL);

		for (final Map.Entry<String, String> secretSantaPair : secretSantaPairs.entrySet()) {
			final String senderAddress = secretSantaPair.getKey();
			final String recipientName = secretSantaPair.getValue();
			emailSender.logEmail(senderAddress, "You will be the secret santa for ...", recipientName);
		}
	}
}
