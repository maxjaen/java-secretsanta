package secretsanta;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import secretsanta.emails.EmailSender;
import secretsanta.model.User;
import secretsanta.rules.NotSamePersonRule;
import secretsanta.rules.NotSameReceiverTwiceRule;
import secretsanta.rules.NotSpecificCombinationRule;
import secretsanta.rules.SantaRule;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SecretSantaGameTest {

    public static final int MIN_USERS_COUNT = 3;
    public static final int MAX_RECEIVER_RULE_CHECKS = 50;

    private SecretSantaGame cut;

    @Mock
    private EmailSender emailSender;

    private List<SantaRule> rules;
    private List<User> users;

    @BeforeEach
    void setUp() {
        this.rules = this.createRules();
        this.users = this.createUsers();
    }

    @Test
    void notEnoughUsers() {
        final List<User> users = Arrays.asList( //
                new User( "name 1", "email address 1"),
                new User( "name 2", "email address 2"));
        this.cut = new SecretSantaGame(users, this.rules, this.emailSender);

        try {

            this.cut.run();

            Assertions.fail("Expected IllegalStateException.");
        } catch (final IllegalStateException e) {
            assertThat(e.getMessage()) //
                .isEqualTo(String.format("You need at least %s recipients to play secret santa", MIN_USERS_COUNT));
        }
    }

    @Test
    void sendEmailsToGivers() {
        this.cut = new SecretSantaGame(this.users, this.rules, this.emailSender);

        this.cut.run();

        verify(emailSender, times(this.users.size())).sendEmail( //
                anyString(), eq("You will be the secret santa for ..."),  anyString());
    }

    @Test
    void sendEmailsToGivers_withUnfulfilledRules() {
        final List<User> users = Arrays.asList( //
                new User( "name 1", "email address 1"),
                new User( "name 1", "email address 1"),
                new User( "name 1", "email address 1"),
                new User( "name 1", "email address 1"));
        this.cut = new SecretSantaGame(users, this.rules, this.emailSender);

        try {

            this.cut.run();

            Assertions.fail("Expected IllegalStateException.");
        } catch (final IllegalStateException e) {
            assertThat(e.getMessage()) //
                .isEqualTo(String.format("Cannot find valid receiver within %s tries.", MAX_RECEIVER_RULE_CHECKS));
        }
    }

    @Test
    void usersAreNull() {
        try {

            this.cut = new SecretSantaGame(null, this.rules, this.emailSender);

            Assertions.fail("Expected NullPointerException.");
        } catch (final NullPointerException e) {
            assertThat(e.getMessage()) //
                    .isEqualTo("Users should not be null.");
        }
    }

    @Test
    void rulesAreNull() {
        try {

            this.cut = new SecretSantaGame(this.users, null, this.emailSender);

            Assertions.fail("Expected NullPointerException.");
        } catch (final NullPointerException e) {
            assertThat(e.getMessage()) //
                    .isEqualTo("Rules should not be null.");
        }
    }

    @Test
    void emailSenderIsNull() {
        try {

            this.cut = new SecretSantaGame(this.users, this.rules, null);

            Assertions.fail("Expected NullPointerException.");
        } catch (final NullPointerException e) {
            assertThat(e.getMessage()) //
                    .isEqualTo("Email sender should not be null.");
        }
    }

    private List<User> createUsers() {
        return Arrays.asList( //
                new User( "name 1", "email address 1"), //
                new User( "name 2", "email address 2"), //
                new User( "name 3", "email address 3"), //
                new User( "name 4", "email address 4"));
    }

    private List<SantaRule> createRules() {
        return Arrays.asList( //
                new NotSamePersonRule(), //
                new NotSameReceiverTwiceRule(), //
                new NotSpecificCombinationRule( //
                        new User( "name 1", "email address 1"), //
                        new User( "name 2", "email address 2")));
    }
}
