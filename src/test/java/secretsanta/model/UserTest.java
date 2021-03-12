package secretsanta.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void equals_whenSameObject() {
        final User giver = new User("name", "test@test.de");

        assertThat(giver.equals(giver)).isTrue();
    }

    @Test
    void equals_whenSameEmail() {
        final User giver = new User("name", "test@test.de");
        final User receiver = new User("name2", "test@test.de");

        assertThat(giver.equals(receiver)).isTrue();
        assertThat(giver.hashCode() == receiver.hashCode()).isTrue();
    }

    @Test
    void notEquals_whenDifferentEmail() {
        final User giver = new User("name", "test@test.de");
        final User receiver = new User("name2", "test2@test.de");

        assertThat(giver.equals(receiver)).isFalse();
        assertThat(giver.hashCode() == receiver.hashCode()).isFalse();
    }

    @Test
    void notEquals_withNull() {
        final User giver = new User("name", "test@test.de");

        assertThat(giver.equals(null)).isFalse();
    }

    @Test
    void checkToString(){
        final User giver = new User("name", "test@test.de");

        assertThat(giver.toString()).isEqualTo("User{userName='name', userEmail='test@test.de'}");
    }
}
