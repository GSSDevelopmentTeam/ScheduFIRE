package com.sendmail;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Iterator;
import java.util.Locale;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.bean.VigileDelFuocoBean;


/**
 * Classe che si occupa di mandare le email a tutti i vigili del fuoco
 * dopo aver generato il turno
 * @author Francesca Pia Perillo
 * @author Ciro Cipolletta
 * @author Emanuele Bombardelli
 *
 */
public class SendMail {

	private static final
	String CSS_td_PARI = 
	"style =\""
			+ " border: 1px solid #454d55;"
			+ " background-color: #b1b5ba;"
			+ " font-size: 15px;"
			+ "\"";

	private static final
	String CSS_td_DISPARI = 
	"style =\""
			+ " border: 1px solid #454d55;"
			+ " font-size: 15px;"
			+ "\"";

	private static final
	String CSS_table =
	"style = \""
			+ "  border-collapse: collapse;"
			+ "	 border: 1px solid #454d55;"
			+ "	 font-size: 15px;"
			+ "  width: 100%;"
			+ "	 height:20px;"
			+ "	 text-align: center;"
			+ "  margin-top: 20px;"
			+ "\"";

	private static final
	String CSS_th = 
	"style = \""
			+ "border:1px solid white;" + 
			"  height: 50px;" + 
			"  background-color: #454d55;" + 
			"  color:white;" + 
			"  font-size: 18px;"
			+ "\"";

	private static final
	String CSS_titolo=
	"style =\""
			+ "text-align: center;"
			+ "font-size:30px;"
			+ "margin-bottom:10px"
			+ "\"";

	private static final
	String CSS_sottotitolo=
	"style =\""
			+ "text-align: center;"
			+ "font-size:25px;"
			+ "margin-bottom:7px"
			+ "\"";


	private static String td_pari(String valore) {
		return "<td "+CSS_td_PARI+">"+valore+"</td>";
	}


	private static String td_dispari(String valore) {
		return "<td "+CSS_td_DISPARI+">"+valore+"</td>";
	}

	private static String tr_pari(String nome,String cognome,String squadra) {
		return "<tr>"+td_pari(nome)+td_pari(cognome)+td_pari(squadra)+"</tr>";
	}

	private static String tr_dispari(String nome,String cognome,String squadra) {
		return "<tr>"+td_dispari(nome)+td_dispari(cognome)+td_dispari(squadra)+"</tr>";
	}
	private static String tabella(ArrayList<String> tdList) {
		String tabella="<table "+CSS_table+">" + 
				"    <tr>" + 
				"      <th "+CSS_th+">Cognome</th>" + 
				"      <th "+CSS_th+">Nome</th>" + 
				"      <th "+CSS_th+">Squadra</th>" + 
				"    </tr>";

		for(String td:tdList) {
			tabella+=td;
		}
		tabella+="</table>";
		return tabella;
	}

	private static String titolo(String titolo) {
		return"<h3 "+CSS_titolo+">"+titolo+"</h3>";
	}

	private static String sottotitolo (String sottotitolo) {
		return"<p "+CSS_sottotitolo+">"+sottotitolo+"</p>";
	}

	/**
	 * Il metodo si occupa del mandare le mail ai vigili del fuoco. La mail è formattata in HTML.
	 * @param data La data del turno mattutino. 
	 * @param squadraDiurno La mappa dei vigili del fuoco e delle relative squadre associate mattutina.
	 * @param squadraNotturno La mappa dei vigili del fuoco e delle relative squadre associate notturna.
	 */
	public static void sendMail(Date data, HashMap<VigileDelFuocoBean, String> squadraDiurno, HashMap<VigileDelFuocoBean, String> squadraNotturno) {
		//Lista dei destinatari

		ArrayList<String>  email = new ArrayList<String>();
		/*
		 * EmailDao allEmail = new EmailDao();
		 * email=allEmail.getEmail();*/


		
		email.add("c.cipolletta2@studenti.unisa.it");
		email.add("f.perillo11@studenti.unisa.it");
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


		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(proprieta, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("schedufire@gmail.com", "schedufire20");
			}

		});

		// Used to debug SMTP issues
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
			}
			message.setRecipients(Message.RecipientType.TO, address);


			// Set Subject: header field
			message.setSubject("Nuova generazione turni");


			// Now set the actual message
			ArrayList<String> nomiDiurno=new ArrayList<String>();
			ArrayList<String> cognomiDiurno=new ArrayList<String>();
			ArrayList<String> squadreDiurno=new ArrayList<String>();

			ArrayList<String> nomiNotturno=new ArrayList<String>();
			ArrayList<String> cognomiNotturno=new ArrayList<String>();
			ArrayList<String> squadreNotturno=new ArrayList<String>();

			ArrayList<String> diurnoLista=new ArrayList<String>();
			ArrayList<String> notturnoLista=new ArrayList<String>();

			//scorrimento sugli HashMap passati come parametro alla funziona 
			//squadra diurna
			Iterator i = squadraDiurno.entrySet().iterator();
			while(i.hasNext()) {
				Map.Entry<VigileDelFuocoBean, String> coppia = (Map.Entry<VigileDelFuocoBean, String>) i.next();
				nomiDiurno.add(coppia.getKey().getCognome());
				cognomiDiurno.add(coppia.getKey().getNome());
				squadreDiurno.add(coppia.getValue());
			}

			//squadra notturna 
			i = squadraNotturno.entrySet().iterator();
			while(i.hasNext()) {
				Map.Entry<VigileDelFuocoBean, String> coppia = (Map.Entry<VigileDelFuocoBean, String>) i.next();
				nomiNotturno.add(coppia.getKey().getCognome());
				cognomiNotturno.add(coppia.getKey().getNome());
				squadreNotturno.add(coppia.getValue());
			}

			int count; 

			for(count=0;count<cognomiDiurno.size();count++) {
				if(count%2!=0)
					diurnoLista.add(tr_pari(cognomiDiurno.get(count),nomiDiurno.get(count),squadreDiurno.get(count)));
				else
					diurnoLista.add(tr_dispari(cognomiDiurno.get(count),nomiDiurno.get(count),squadreDiurno.get(count)));
			}

			for(count=0;count<cognomiNotturno.size();count++) {
				if(count%2!=0)
					notturnoLista.add(tr_pari(cognomiNotturno.get(count),nomiNotturno.get(count),squadreNotturno.get(count)));
				else
					notturnoLista.add(tr_dispari(cognomiNotturno.get(count),nomiNotturno.get(count),squadreNotturno.get(count)));
			}

			String titolo = titolo("Generazione della squadra per il turno del");
			titolo+=titolo(data.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM YYYY", new Locale("it", "IT"))) 
					+ " - " + data.toLocalDate().plusDays(1).format(DateTimeFormatter.ofPattern("dd MMMM YYYY", new Locale("it", "IT"))));
			String tabellaDiurno= sottotitolo("Squadra diurna:");
			tabellaDiurno+=tabella(diurnoLista);


			String tabellaNotturno= sottotitolo("Squadra notturna:");
			tabellaNotturno+=tabella(notturnoLista);


			String htmlFinale = titolo+tabellaDiurno+tabellaNotturno;

			message.setText(htmlFinale, "utf-8", "html");

			// Send message
			Transport.send(message);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}	
}