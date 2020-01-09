package control;

import javax.servlet.ServletException;

/**
 * Classe che si occupa della gestione degli errori.
 * @author Eugenio Sottile
 */

public class AutenticazioneException extends ServletException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 */
	public AutenticazioneException() {
		super();
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message � una stringa che indica il messaggio di errore.
	 * @param rootCause � un oggetto Throwable
	 */
	public AutenticazioneException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message � una stringa che indica il messaggio di errore.
	 */
	public AutenticazioneException(String message) {
		super(message);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param rootCause � un oggetto Throwable
	 */
	public AutenticazioneException(Throwable rootCause) {
		super(rootCause);
	}
	
}
