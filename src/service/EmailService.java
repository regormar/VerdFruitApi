package service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import config.ConstantsApi;

public class EmailService {

	public static boolean sendEmail(String recepient, String msg, String subject) throws MessagingException {	
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
	
		String myAccountEmail = ConstantsApi.USER_EMAIL;
		String password = ConstantsApi.USER_PASS;
		
		Session session  =  Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		Message message = prepareMessage(session, myAccountEmail, recepient, msg, subject);
		
		Transport.send(message);

		return true;
	}

	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String msg, String subject) {
		try {
			Message message =  new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject(subject);
			message.setContent(msg, "text/html");
			return message;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}
