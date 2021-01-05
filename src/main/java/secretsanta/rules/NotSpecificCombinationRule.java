package secretsanta.rules;

import java.util.List;

public class NotSpecificCombinationRule implements SantaRule {

	private final String sender;
	private final String recipient;

	public NotSpecificCombinationRule(final String sender, final String recipient) {
		this.sender = sender;
		this.recipient = recipient;
	}

	@Override
	public boolean test(final String sender, final String recipient, final List<String> assignedSenders) {
		return ((this.sender != sender) || (this.recipient != recipient));
	}
}
