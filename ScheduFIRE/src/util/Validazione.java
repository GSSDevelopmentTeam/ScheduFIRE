package util;

import java.util.regex.Pattern;

/**
 * Classe che si occupa della validazione dei parametri in input.
 * @author Eugenio Sottile
 */

public class Validazione {
	
	//Costanti
	
	private static final Pattern PATTERN_NOME_COGNOME = Pattern.compile("^[A-Z]{1}[a-z]{0,19}+$");
	
	private static final Pattern PATTERN_EMAIL = Pattern.compile("^[a-z]+[0-9]*{3,50}$"); // da rifare
	
	private static final String[] MANSIONI = {"Capo Squadra", "Autista", "Vigile"};
	
	private static final String[] GRADI = {};
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param nome è una stringa che indica il nome di una persona.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean nome(String nome) {
		return PATTERN_NOME_COGNOME.matcher(nome).matches();
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param cognome è una stringa che indica il cognome di una persona.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean cognome(String cognome) {
		return PATTERN_NOME_COGNOME.matcher(cognome).matches();
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param email è una stringa che rappresenta una email.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean email(String email) {
		return PATTERN_EMAIL.matcher(email.toLowerCase()).matches();
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param mansione è una stringa che rappresenta una mansione.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean mansione(String mansione) {
		
		for(String m : MANSIONI) {
			if(m.equals(mansione))
				return true;
		}
		
		return false;
		
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param ferieAnnoCorrente è un intero che rappresenta il numero 
	 * di ferie relative all'anno corrente.
	 * @return @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean ferieAnnoCorrente(int ferieAnnoCorrente) {
		return (ferieAnnoCorrente < 0);
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param ferieAnniPrecedenti è un intero che rappresenta il numero 
	 * di ferie relative agli anni precedenti.
	 * @return @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean ferieAnniPrecedenti(int ferieAnniPrecedenti) {
		return (ferieAnniPrecedenti < 0);
	}
	
	
	public static boolean grado(String grado) {
		
		//da implementare
		
		return false;
		
	}
	
}
