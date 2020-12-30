package secretsanta.rules;

import java.util.List;

public class NotSamePersonRule implements SantaRule {

	@Override
	public boolean test(final String sender, final String recipient, final List<String> assignedSenders) {
		return !sender.equalsIgnoreCase(recipient);
	}
}
