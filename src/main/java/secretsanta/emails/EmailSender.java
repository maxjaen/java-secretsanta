package secretsanta.emails;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	private static final Logger LOG = Logger.getLogger(EmailSender.class.getName());

	private final String username;
	private final String password;
	private final String displayedAddress;
	private final String host;
	private final String personal;

	public EmailSender(final String username, final String password, final String host, final String displayedAddress,
			final String personal) {
		this.username = username;
		this.password = password;
		this.displayedAddress = displayedAddress;
		this.host = host;
		this.personal = personal;
	}

	public void sendEmail(final List<String> recipientAddresses, final String subject, final String message) {
		for (final String recipientAddress : recipientAddresses) {
			this.sendEmail(recipientAddress, subject, message);
		}
	}

	public void sendEmail(final String recipientAddress, final String subject, final String message) {

		try {
			final Properties props = System.getProperties();
			props.put("mail.smtp.host", this.host);
			props.put("mail.smtp.awuth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.ssl.enable", "true");

			final Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(true);

			final Message mailMessage = new MimeMessage(mailSession);
			mailMessage.setFrom(new InternetAddress(this.displayedAddress, this.personal));
			mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
			mailMessage.setContent(message, "text/plain");
			mailMessage.setSubject(subject);

			final Transport transport = mailSession.getTransport("smtp");
			transport.connect(this.host, this.username, this.password);
			transport.sendMessage(mailMessage, mailMessage.getAllRecipients());

		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	public void logEmail(final List<String> recipientAddresses, final String subject, final String message) {
		for (final String recipientAddress : recipientAddresses) {
			this.logEmail(recipientAddress, subject, message);
		}
	}

	public void logEmail(final String recipientAddress, final String subject, final String message) {
		LOG.info("=============================================");
		LOG.info("From: " + this.displayedAddress);
		LOG.info("To: " + recipientAddress);
		LOG.info("Subject: " + subject);
		LOG.info("Message: " + message);
		LOG.info("=============================================");
	}
}
