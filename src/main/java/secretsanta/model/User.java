package secretsanta.model;

import java.util.Objects;

public class User {

    private final String userName;
    private final String userEmail;

    public User(final String userName, final String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userEmail.equalsIgnoreCase(user.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
