package util;

import java.util.regex.Pattern;

/**
 * Classe che si occupa della validazione dei parametri in input.
 * @author Eugenio Sottile
 */

public class Validazione {
	
	//Costanti
	private static final Pattern USERNAME=Pattern.compile("^[A-Za-z0-9]{3,20}+$");
	
	private static final Pattern PASSWORD=Pattern.compile("^[A-Za-z0-9]{6,16}+$");
	
	private static final Pattern PATTERN_NOME_COGNOME = Pattern.compile("^[A-Z]{1}[A-Z a-z]{0,19}+$");
	
	private static final Pattern PATTERN_EMAIL = Pattern.compile("[A-Za-z]+([1-9][0-9]*)?\\.[A-Za-z]+"); 
	
	private static final String[] MANSIONI = {"Capo Squadra", "Autista", "Vigile"};
	
	private static final String[] GRADI = {"Qualificato", "Esperto", "Coordinatore", "Semplice"};
	
	private static final String[] TURNI = {"B"};
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param username E' una stringa che indica l username.
	 * @return true se il formato e' rispettato, false altrimenti.
	 */
	public static boolean username(String username) {
		return (username != null) && (USERNAME.matcher(username).matches());
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param password E' una stringa che indica la password.
	 * @return true se il formato e' rispettato, false altrimenti.
	 */
	public static boolean password(String password) {
		return (password != null) && (PASSWORD.matcher(password).matches());
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param nome è una stringa che indica il nome di una persona.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean nome(String nome) {
		return (nome != null) && (PATTERN_NOME_COGNOME.matcher(nome).matches());
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param cognome è una stringa che indica il cognome di una persona.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean cognome(String cognome) {
		return (cognome !=null) && (PATTERN_NOME_COGNOME.matcher(cognome).matches());
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param email è una stringa che rappresenta una email.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean email(String email) {
		
		return (email != null) && (PATTERN_EMAIL.matcher(email.toLowerCase()).matches());

	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param mansione è una stringa che rappresenta una mansione.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean mansione(String mansione) {
		
		if(mansione == null)
			return false;
		
		for(String m : MANSIONI) {
			if(m.equals(mansione))
				return true;
		}
		
		return false;
		
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param giorniFerieAnnoCorrente e' un intero che rappresenta il numero 
	 * di ferie relative all'anno corrente.
	 * @return true se il formato e' rispettato, false altrimenti.
	 */
	public static boolean giorniFerieAnnoCorrente(int giorniFerieAnnoCorrente) {
		return (giorniFerieAnnoCorrente >= 0 && giorniFerieAnnoCorrente <= 22);
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param giorniFerieAnniPrecedenti E' un intero che rappresenta il numero 
	 * di ferie relative agli anni precedenti.
	 * @return true se il formato e' rispettato, false altrimenti.
	 */
	public static boolean giorniFerieAnniPrecedenti(int giorniFerieAnniPrecedenti) {
		return (giorniFerieAnniPrecedenti >= 0 && giorniFerieAnniPrecedenti < 1000);
	}
	
	/**
	 * Si occupa di controllare se il grado passato come parametro è corretto.
	 * @param grado il grado da testare
	 * @return TRUE se il grado è corretto, FALSE altrimenti
	 */
	public static boolean grado(String grado) {
		
		if(grado == null)
			return false;
		
		for(String g : GRADI) {
			if(g.equals(grado))
				return true;
		}
		
		return false;
		
	}
	
	/**
	 * Si occupa di controllare se il turno passato come parametro è corretto.
	 * @param turno il turno da testare
	 * @return TRUE se il turno è corretto, FALSE altrimenti
	 */
	public static boolean turno(String turno) {
		
		if(turno == null)
			return false;
		
		for(String t : TURNI) {
			if(t.equals(turno))
				return true;
		}
		
		return false;
		
	}
	
}
