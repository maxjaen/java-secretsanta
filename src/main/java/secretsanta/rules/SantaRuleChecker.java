package secretsanta.rules;

import secretsanta.model.User;

import java.util.ArrayList;
import java.util.List;

public class SantaRuleChecker {

	private final List<SantaRule> santaRules;

	public SantaRuleChecker() {
		this.santaRules = new ArrayList<>();
	}

	public SantaRuleChecker(final List<SantaRule> santaRules) {
		this.santaRules = santaRules;
	}

	public void addSantaRule(final SantaRule santaRule) {
		this.santaRules.add(santaRule);
	}

	public void addSantaRules(final List<SantaRule> santaRules) {
		for (final SantaRule santaRule : santaRules) {
			this.santaRules.add(santaRule);
		}
	}

	public boolean check(final User giver, final User receiver, final List<User> alreadyAssignedReceivers) {
		return this.santaRules.stream().allMatch(e -> e.test(giver, receiver, alreadyAssignedReceivers));
	}
}
