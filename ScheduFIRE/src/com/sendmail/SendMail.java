package com.sendmail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.dao.EmailDao;

/**
 * Classe per mandare le mail ai vigili del fuoco quando viene generato un turno.
 * @author Ciro Cipolletta
 * @see javax.mail
 */
public class SendMail {

	/**
	 * Il metodo si occupa del mandare le mail ai vigili del fuoco. 
	 * @param data
	 */
	public static void sendMail(Date data) {
		//Lista dei destinatari
		EmailDao allEmail = new EmailDao();
		ArrayList<String>  email = new ArrayList<String>();
		email=allEmail.getEmail();

		//L'id del mittente
		String from = "schedufire@gmail.com";

		//Supponendo che tu stia inviando e-mail da e-mail smtp
		String host = "smtp.gmail.com";

		//Ottieni proprietà del sistema
		Properties proprietà = System.getProperties();

		//setup mail server
		proprietà.put("mail.smtp.host", host);
		proprietà.put("mail.smtp.port", "465");
		proprietà.put("mail.smtp.ssl.enable", "true");
		proprietà.put("mail.smtp.auth", "true");

		// Prende la sessione in oggetto // e passa l'username e password
		Session session = Session.getInstance(proprietà, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("schedufire@gmail.com", "schedufire20");
			}
		});

		//Utilizzato per il debug di problemi SMTP
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.


			InternetAddress [] address = new InternetAddress[email.size()];
			for(int i = 0 ; i<email.size(); i++) {
				address[i]= new InternetAddress(email.get(i));
				System.out.println(address[i]);
			}
			message.setRecipients(Message.RecipientType.TO, address);


			// Set Subject: header field
			message.setSubject("OGGETTO");

			// Now set the actual message
			message.setText("MESSAGGIO");

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
