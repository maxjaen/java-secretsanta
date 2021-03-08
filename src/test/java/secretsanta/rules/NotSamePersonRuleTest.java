package secretsanta.rules;

import org.junit.jupiter.api.Test;
import secretsanta.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class NotSamePersonRuleTest {

    private final NotSamePersonRule cut = new NotSamePersonRule();

    @Test
    void samePersonWhenSameEmail() {
        final User giver = new User("name", "test@test.de");
        final User receiver = new User("name2", "test@test.de");

        final boolean ruleFulfilled = cut.test(giver, receiver, Arrays.asList());

        assertThat(ruleFulfilled).isFalse();
    }

    @Test
    void notSamePersonWhenDifferentEmail() {
        final User giver = new User("name", "test@test.de");
        final User receiver = new User("name", "test2@test.de");

        final boolean ruleFulfilled = cut.test(giver, receiver, Arrays.asList());

        assertThat(ruleFulfilled).isTrue();
    }
}
