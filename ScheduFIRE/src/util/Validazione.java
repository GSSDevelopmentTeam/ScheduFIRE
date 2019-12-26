package util;

import java.util.regex.Pattern;

/**
 * Classe che si occupa della validazione dei parametri in input.
 * @author Eugenio Sottile
 */

public class Validazione {
	
	//Costanti
	
	private static final Pattern PATTERN_NOME_COGNOME = Pattern.compile("^[A-Z]{1}[a-z]{0,19}+$");
	
	private static final Pattern PATTERN_EMAIL = Pattern.compile(""); // da rifare
	
	private static final String[] MANSIONI = {"Capo Squadra", "Autista", "Vigile"};
	
	private static final String[] GRADI = {"Qualificato", "Esperto", "Coordinatore"};
	
	private static final String[] TURNI = {"B"};
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param nome è una stringa che indica il nome di una persona.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean nome(String nome) {
		return (PATTERN_NOME_COGNOME.matcher(nome).matches()) && (nome != null);
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param cognome è una stringa che indica il cognome di una persona.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean cognome(String cognome) {
		return (PATTERN_NOME_COGNOME.matcher(cognome).matches()) && (cognome !=null);
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param email è una stringa che rappresenta una email.
	 * @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean email(String email) {
		//return (PATTERN_EMAIL.matcher(email.toLowerCase()).matches()) && (email != null);
		return true;
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
	 * @param ferieAnnoCorrente è un intero che rappresenta il numero 
	 * di ferie relative all'anno corrente.
	 * @return @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean giorniFerieAnnoCorrente(int giorniFerieAnnoCorrente) {
		return (giorniFerieAnnoCorrente >= 0);
	}
	
	/**
	 * Si occupa di controllare se il formato del parametro passato è corretto.
	 * @param ferieAnniPrecedenti è un intero che rappresenta il numero 
	 * di ferie relative agli anni precedenti.
	 * @return @return true se il formato è rispettato, false altrimenti.
	 */
	public static boolean giorniFerieAnniPrecedenti(int giorniFerieAnniPrecedenti) {
		return (giorniFerieAnniPrecedenti >= 0);
	}
	
	
	public static boolean grado(String grado) {
		
		if(grado == null)
			return false;
		
		for(String g : GRADI) {
			if(g.equals(grado))
				return true;
		}
		
		return false;
		
	}
	
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
