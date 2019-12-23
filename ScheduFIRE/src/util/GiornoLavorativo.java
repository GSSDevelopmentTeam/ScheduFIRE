package util;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Classe che si occupa della gestione del giorno lavorativo. 
 * @author Alfredo Giuliano 
 */
public class GiornoLavorativo {
	
	
	/**
	 * @param data , la data del giorno di cui si vuole sapere se lavorativo o meno
	 * @return true se il giorno è lavorativo, false altrimenti
	 */
	public static boolean isLavorativo(Date data) {
		int differenza=differenza(data);
		if(differenza<0)
			differenza=-differenza;
		int resto=differenza%4;
		if(resto==0 || resto==1)
			return true;
		else
			return false;
	}
	
	/**
	 * @param data , la data del giorno di cui si vuole sapere se il turno lavorativo è diurno o meno
	 * @return true se il turno di lavoro è diurno, false altrimenti
	 */
	public static boolean isDiurno(Date data) {
		int differenza=differenza(data);
		if(differenza<0)
			differenza=-differenza;
		int resto=differenza%4;
		if(resto==0)
			return true;
		else
			return false;
	
	}
	
	/**
	 * @param data , la data del giorno da cui parte la misurazione
	 * @return data, la data del prossimo giorno lavorativo,escluso il giorno stesso passato come parametro
	 */
	public static Date nextLavorativo(Date data) {
		LocalDate fine=data.toLocalDate();
		do {
			fine=fine.plusDays(1);
		} 
			while (!isLavorativo(Date.valueOf(fine)));
		return Date.valueOf(fine);
	}
	
	
	
	private static int differenza(Date data){
		LocalDate start=LocalDate.of(2019, 12, 20);
		LocalDate fine=data.toLocalDate();
		long days = Duration.between(start.atStartOfDay(), fine.atStartOfDay()).toDays();
        return (int)days;
	}
	
   
}
