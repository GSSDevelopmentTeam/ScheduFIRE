package util;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Classe che si occupa della gestione del giorno lavorativo. 
 * @author Alfredo Giuliano 
 */
public class GiornoLavorativo {
	
	/**
	 * Serve a capire quale è il turno alla data passata come parametro
	 * @param data la data in cui si vuole sapere il turno
	 * @return il nome del turno della data
	 */
	 public static String nomeTurnoB(Date data) {
	        String turno="";
	        int differenza=differenza(data);
	        
	        int resto=differenza%32;
	        
	        if(differenza>=0) {
		        if(resto==0 || resto==1)
		            turno="B7";
		        else if(resto==4 || resto==5)
		            turno="B8";
		        else if(resto==8 || resto==9)
		            turno="B1";
		        else if(resto==12 || resto==13)
		            turno="B2";
		        else if(resto==16 || resto==17)
		            turno="B3";
		        else if(resto==20 || resto==21)
		            turno="B4";
		        else if(resto==24 || resto==25)
		            turno="B5";
		        else if(resto==28 || resto==29)
		            turno="B6";
	        }else {
	        		differenza = (-differenza)+1;
	        		resto=differenza%32;
	        	   if(resto==0 || resto==1)
			            turno="B7";
			        else if(resto==4 || resto==5)
			            turno="B6";
			        else if(resto==8 || resto==9)
			            turno="B5";
			        else if(resto==12 || resto==13)
			            turno="B4";
			        else if(resto==16 || resto==17)
			            turno="B3";
			        else if(resto==20 || resto==21)
			            turno="B2";
			        else if(resto==24 || resto==25)
			            turno="B1";
			        else if(resto==28 || resto==29)
			            turno="B8";
	        }	 
	        return turno;
	    }
	 
	/**
	 * @param data  la data del giorno di cui si vuole sapere se lavorativo o meno
	 * @return true se il giorno � lavorativo, false altrimenti
	 */
	public static boolean isLavorativo(Date data) {
		int differenza=differenza(data);
		if(differenza<0)
			differenza=(-differenza)+1;
		int resto=differenza%4;
		if(resto==0 || resto==1)
			return true;
		else
			return false;
	}
	
	/**
	 * @param data  la data del giorno di cui si vuole sapere se il turno lavorativo � diurno o meno
	 * @return true se il turno di lavoro � diurno, false altrimenti
	 */
	public static boolean isDiurno(Date data) {
		if(isLavorativo(data)) {
			LocalDate giornosucc=data.toLocalDate().plusDays(1);
			if(isLavorativo(Date.valueOf(giornosucc)))
				return true;
			else return false;
		}
		else return false;
	
	}
	
	/**
	 * @param data  la data del giorno da cui parte la misurazione
	 * @return data la data del prossimo giorno lavorativo,escluso il giorno stesso passato come parametro
	 */
	public static Date nextLavorativo(Date data) {
		LocalDate fine=data.toLocalDate();
		do {
			fine=fine.plusDays(1);
		} 
			while (!isLavorativo(Date.valueOf(fine)));
		return Date.valueOf(fine);
	}
	
	
	/**
	 * @param data  la data del giorno da cui parte la misurazione
	 * @return data la data del precedente giorno lavorativo,escluso il giorno stesso passato come parametro
	 */
	public static Date precLavorativo(Date data) {
		LocalDate fine=data.toLocalDate();
		do {
			fine=fine.plusDays(-1);
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
