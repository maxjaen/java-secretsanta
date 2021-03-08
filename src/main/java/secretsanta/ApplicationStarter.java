package secretsanta;

import secretsanta.emails.EmailSender;
import secretsanta.model.User;
import secretsanta.rules.NotSamePersonRule;
import secretsanta.rules.NotSameReceiverTwiceRule;
import secretsanta.rules.NotSpecificCombinationRule;
import secretsanta.rules.SantaRule;

import java.util.Arrays;
import java.util.List;

public class ApplicationStarter {

	/* CHANGE FOR YOUR EMAIL PROVIDER */
	private final static String EMAIL_USERNAME = "<your username>"; // used to authenticate
	private final static String EMAIL_PASSWORD = "<your password>"; // used to authenticate
	private final static String EMAIL_HOST = "your host server"; // host f.e. mail.gmx.net
	private final static String DISPLAYED_EMAIL_ADDRESS = "<anonymous email address>"; // could be some throw away email
	private final static String DISPLAYED_PERSONAL = "<anonymous personal>"; // could be any string

	/* ADD NEW USERS */
	private final static List<User> USERS = Arrays.asList( //
			new User( "name 1", "email address 1"), //
			new User( "name 2", "email address 2"), //
			new User( "name 3", "email address 3"), //
			new User( "name 4", "email address 4"));

	/* ADD NEW SANTA RULES */
	private final static List<SantaRule> RULES = Arrays.asList( //
			new NotSamePersonRule(), //
			new NotSameReceiverTwiceRule(), //
			new NotSpecificCombinationRule(USERS.get(0), USERS.get(1))); // user 1 will never give user 2 a present

	private final static EmailSender EMAIL_SENDER = new EmailSender( //
			EMAIL_USERNAME, //
			EMAIL_PASSWORD, //
			EMAIL_HOST, //
			DISPLAYED_EMAIL_ADDRESS, //
			DISPLAYED_PERSONAL);

	public static void main(final String[] args) {
		final SecretSantaGame app = new SecretSantaGame(USERS, RULES, EMAIL_SENDER);
		app.run();
	}
}
