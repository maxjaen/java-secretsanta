package secretsanta.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import secretsanta.model.User;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotSameReceiverTwiceRuleTest {

    private final NotSameReceiverTwiceRule cut = new NotSameReceiverTwiceRule();

    private User giver;
    private User receiver;

    @BeforeEach
    void setUp() {
        this.giver = new User("name", "test@test.de");
        this.receiver = new User("name2", "test2@test.de");
    }

    @Test
    void notSameReceiverTwice_noReceiversAssigned() {
        final List<User> alreadyAssignedReceivers = Arrays.asList();

        final boolean ruleFulfilled = cut.test(this.giver, this.receiver, alreadyAssignedReceivers);

        assertThat(ruleFulfilled).isTrue();
    }

    @Test
    void notSameReceiverTwice_receiverAlreadyAssigned() {
        final List<User> alreadyAssignedReceivers = Arrays.asList(this.receiver);

        final boolean ruleFulfilled = cut.test(this.giver, this.receiver, alreadyAssignedReceivers);

        assertThat(ruleFulfilled).isFalse();
    }
}
