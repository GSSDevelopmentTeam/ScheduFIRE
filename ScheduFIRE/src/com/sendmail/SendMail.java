package com.sendmail;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.bean.VigileDelFuocoBean;
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
	 * @param squadraNotturno 
	 * @param squadraDiurno 
	 */
	public static void sendMail(Date data, HashMap<VigileDelFuocoBean, String> squadraDiurno, HashMap<VigileDelFuocoBean, String> squadraNotturno) {
		//Lista dei destinatari

		ArrayList<String>  email = new ArrayList<String>();
		/*
		 * EmailDao allEmail = new EmailDao();
		 * email=allEmail.getEmail();*/


		email.add("c.cipolletta2@studenti.unisa.it");
		email.add("f.perillo11@stduenti.unisa.it");
		email.add("e.sottile@studenti.unisa.it");
		email.add("g.annunziata49@studenti.unisa.it");
		email.add("e.bombardelli@studenti.unisa.it");
		email.add("a.giuliano21@studenti.unisa.it");
		email.add("n.labanca3@studenti.unisa.it");
		email.add("b.bruno4@studenti.unisa.it");


		//L'id del mittente
		String from = "schedufire@gmail.com";

		//Supponendo che tu stia inviando e-mail da e-mail smtp
		String host = "smtp.gmail.com";

		//Ottieni proprieta del sistema
		Properties proprieta = System.getProperties();

		//setup mail server
		proprieta.put("mail.smtp.host", host);
		proprieta.put("mail.smtp.port", "465");
		proprieta.put("mail.smtp.ssl.enable", "true");
		proprieta.put("mail.smtp.auth", "true");

		// Prende la sessione in oggetto // e passa l'username e password
		Session session = Session.getInstance(proprieta, new javax.mail.Authenticator() {
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
			message.setSubject("(noreply) Squadre per il turno del " + 
					data.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")) + " - " + 
					data.toLocalDate().plusDays(1L).format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));

			// Now set the actual message
			message.setText(formattaMessaggio(squadraDiurno, squadraNotturno));

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private static String formattaMessaggio(HashMap<VigileDelFuocoBean, String> squadraDiurno,
			HashMap<VigileDelFuocoBean, String> squadraNotturno) {
		String toReturn = "Si informa per presa visione il personale del turno B "
				+ "che le squadre per il turno in oggetto sono le seguenti:\n\tSQUADRA DIURNA:\n";
		Iterator i = squadraDiurno.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry<VigileDelFuocoBean, String> coppia = (Map.Entry<VigileDelFuocoBean, String>) i.next();
			toReturn += "" + coppia.getKey().getCognome() + " " + coppia.getKey().getNome() + "\t" + coppia.getValue() + "\n";
		}
		
		toReturn += "\n\n\tSQUADRA NOTTURNA:\n";
		
		i = squadraNotturno.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry<VigileDelFuocoBean, String> coppia = (Map.Entry<VigileDelFuocoBean, String>) i.next();
			toReturn += "" + coppia.getKey().getCognome() + " " + coppia.getKey().getNome() + "\t" + coppia.getValue() + "\n";
		}
		
		toReturn += "\n\nSi ringrazia per l'attenzione e la presa visione.";
		
		return toReturn;
	}
}
