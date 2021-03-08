package secretsanta.rules;

import secretsanta.model.User;

import java.util.List;

public class NotSpecificCombinationRule implements SantaRule {

	private final User giver;
	private final User receiver;

	public NotSpecificCombinationRule(final User giver, final User receiver) {
		this.giver = giver;
		this.receiver = receiver;
	}

	@Override
	public boolean test(User giver, User receiver, List<User> alreadyAssignedReceivers) {
		return !(giver.getUserEmail().equalsIgnoreCase(this.giver.getUserEmail())
				&& receiver.getUserEmail().equalsIgnoreCase(this.receiver.getUserEmail()));
	}
}
