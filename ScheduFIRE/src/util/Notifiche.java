package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.dao.VigileDelFuocoDao;
import util.Util;

/**
 * La classe si occupa della generazione, della gestione e dell'aggiornamento delle
 * notifiche del sistema. 
 * @author Emanuele Bombardelli
 *
 */
public class Notifiche {
	private static List<Notifica> listaNotifiche;
	
	public Notifiche() {
		listaNotifiche = new ArrayList<>();
		update(UPDATE_PER_AVVIO);
	}

	/**
	 * Esegue l'update delle notifiche nel sistema. 
	 * @param cause Causa dell'update (usare i final)
	 */
	public static void update(int cause) {
		if(cause == 1) {
			firstUpdate();
		}
		
		Collections.sort(listaNotifiche, (Notifica n1, Notifica n2) -> 
		(n2.getSeverita() - n1.getSeverita()));
	}
	
	private static void firstUpdate() {
		VigileDelFuocoDao.getDisponibili(GiornoLavorativo.nextLavorativo())
	}

	/**
	 * Utilizzare quando l'avvio
	 */
	public static final int UPDATE_PER_AVVIO = 1;
}
