package secretsanta.rules;

import java.util.List;

public interface SantaRule {
	boolean test(String sender, String recipient, List<String> assignedSenders);
}
