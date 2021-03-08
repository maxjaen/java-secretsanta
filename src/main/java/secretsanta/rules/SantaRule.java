package secretsanta.rules;

import secretsanta.model.User;

import java.util.List;

public interface SantaRule {
	boolean test(User giver, User receiver, List<User> alreadyAssignedReceivers);
}
