package control;

import javax.servlet.ServletException;

/**
 * Classe che si occupa della gestione degli errori.
 * @author Eugenio Sottile
 */

public class ScheduFIREException extends ServletException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 */
	public ScheduFIREException() {
		super();
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message è una stringa che indica il messaggio di errore.
	 * @param rootCause è un oggetto Throwable
	 */
	public ScheduFIREException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message è una stringa che indica il messaggio di errore.
	 */
	public ScheduFIREException(String message) {
		super(message);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param rootCause è un oggetto Throwable
	 */
	public ScheduFIREException(Throwable rootCause) {
		super(rootCause);
	}
	
}
