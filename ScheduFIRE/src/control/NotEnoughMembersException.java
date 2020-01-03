package control;

import javax.servlet.ServletException;

/**
 * Classe che si occupa della gestione degli errori.
 * @author Eugenio Sottile
 */

public class NotEnoughMembersException extends ServletException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 */
	public NotEnoughMembersException() {
		super();
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message � una stringa che indica il messaggio di errore.
	 * @param rootCause � un oggetto Throwable
	 */
	public NotEnoughMembersException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message � una stringa che indica il messaggio di errore.
	 */
	public NotEnoughMembersException(String message) {
		super(message);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param rootCause � un oggetto Throwable
	 */
	public NotEnoughMembersException(Throwable rootCause) {
		super(rootCause);
	}
	
}
