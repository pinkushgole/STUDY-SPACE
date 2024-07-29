package com.Library.service;

import org.springframework.stereotype.Service;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class Email_Service {

	public static void sendEmail(String to, String from, String subject, String message) {

		// variable for gamil
		String host = "";

		// get the system properties
		Properties properies = System.getProperties();

		// seting information to properties object
		properies.put("mail.smtp.host", host);
		properies.put("mail.smtp.port", "465");
		properies.put("mail.smtp.ssl.enable", "true");
		properies.put("mail.smtp.auth", "true");

		// setp 1: get the session object
		// Session session=Session.getInstance(properies,new Authenticator()
		Session session = Session.getInstance(properies, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("");
			}

		});
		session.setDebug(true);

		// setp 2: compose the message(text,multi medea)
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			// from set
			mimeMessage.setFrom(from);

			// adding recipient to message
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// adding subject
			mimeMessage.setSubject(subject);

			// adding text to
			mimeMessage.setText(message);

			// send
			// step 3: sending the message transport class
			Transport.send(mimeMessage);
			System.out.println("sucess the message");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int opt_Generted() {
		Random random = new Random();
		int min = 1000;
		int max = 9999;

		int randomNumber = random.nextInt((max - min) + 1) + min;

		return randomNumber;
	}

}
