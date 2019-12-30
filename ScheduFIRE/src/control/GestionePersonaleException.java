package control;

import javax.servlet.ServletException;

/**
 * Classe che si occupa della gestione degli errori.
 * @author Eugenio Sottile
 */

public class GestionePersonaleException extends ServletException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 */
	public GestionePersonaleException() {
		super();
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message , è una stringa che indica il messaggio di errore.
	 * @param rootCause , è un oggetto Throwable
	 */
	public GestionePersonaleException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param message , è una stringa che indica il messaggio di errore.
	 */
	public GestionePersonaleException(String message) {
		super(message);
	}
	
	/**
	 * Instanzia un oggetto ScheduFIREException chiamando il costruttore del padre.
	 * @param rootCause , è un oggetto Throwable
	 */
	public GestionePersonaleException(Throwable rootCause) {
		super(rootCause);
	}
	
}