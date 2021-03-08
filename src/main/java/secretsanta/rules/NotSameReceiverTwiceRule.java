package secretsanta.rules;

import secretsanta.model.User;

import java.util.List;

public class NotSameReceiverTwiceRule implements SantaRule {

	@Override
	public boolean test(User giver, User receiver, List<User> alreadyAssignedReceivers) {
		return alreadyAssignedReceivers.stream() //
			.noneMatch(e -> e.getUserEmail().equalsIgnoreCase(receiver.getUserEmail()));
	}
}
