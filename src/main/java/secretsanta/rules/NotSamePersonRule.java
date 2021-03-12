package secretsanta.rules;

import secretsanta.model.User;

import java.util.List;

public class NotSamePersonRule implements SantaRule {

	@Override
	public boolean test(User giver, User receiver, List<User> alreadyAssignedReceivers) {
		return !giver.equals(receiver);
	}
}
