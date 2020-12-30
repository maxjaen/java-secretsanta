package secretsanta.rules;

import java.util.List;

public class NotSameSenderTwiceRule implements SantaRule {

	@Override
	public boolean test(final String sender, final String recipient, final List<String> assignedSenders) {
		return !assignedSenders.contains(sender);
	}
}
