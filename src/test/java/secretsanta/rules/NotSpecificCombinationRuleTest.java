package secretsanta.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import secretsanta.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class NotSpecificCombinationRuleTest {

    private NotSpecificCombinationRule cut;
    private User giver;
    private User receiver;

    @BeforeEach
    void setUp() {
        this.giver = new User("name", "test@test.de");
        this.receiver = new User("name2", "test2@test.de");
    }

    @Test
    void combinationGiverToReceiver() {
        this.cut = new NotSpecificCombinationRule(this.giver, this.receiver);

        final boolean ruleFulfilled = cut.test(this.giver, this.receiver, Arrays.asList());

        assertThat(ruleFulfilled).isFalse();
    }

    @Test
    void combinationReceiverToGiver() {
        this.cut = new NotSpecificCombinationRule(this.giver, this.receiver);

        final boolean ruleFulfilled = cut.test(this.receiver, this.giver, Arrays.asList());

        assertThat(ruleFulfilled).isTrue();
    }
}
