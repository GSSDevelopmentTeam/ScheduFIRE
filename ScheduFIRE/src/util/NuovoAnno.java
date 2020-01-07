package util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;
import model.dao.VigileDelFuocoDao;

public class NuovoAnno {

	
	/**
	 * Verifica se sono la una delle due date passate è il primo giorno lavorativo dell'anno, se lo è cancella dal database
	 * il calendario dell'anno precedente e aggiorna le ferie ai vigili 
	 * @param diurnoDate il giorno lavorativo diurno
	 * @param notturnoDate il giorno lavorativo notturno
	 */
	public static void aggiornaDB(Date diurnoDate,Date notturnoDate) {
		LocalDate diurno=diurnoDate.toLocalDate();
		LocalDate notturno=notturnoDate.toLocalDate();
		boolean modifica=false;
		int annoNuovo=0;
		LocalDate precLavorativo=GiornoLavorativo.precLavorativo(diurnoDate).toLocalDate();
		
		//Se l'anno del diurno è diverso da quello del notturno, sto cambiando anno
		if(diurno.getYear()!=notturno.getYear()) {
			modifica=true;
			annoNuovo=notturno.getYear();
		}

		//Se il diurno ha un anno diverso dal precedente giorno lavorativo, sto in un nuovo anno
		else if(diurno.getYear()!= precLavorativo.getYear()) {
			modifica=true;	
			annoNuovo=diurno.getYear();
		}
		
		
		if(modifica==true) {
			setFerie();
			Date inizioAnnoNuovo=Date.valueOf(LocalDate.of(annoNuovo, 1, 1));
			ComponenteDellaSquadraDao.rimuoviTutti(inizioAnnoNuovo);
			SquadraDao.rimuoviTutti(inizioAnnoNuovo);
			ListaSquadreDao.rimuoviTutte(inizioAnnoNuovo);
			System.out.println("rimozione avvenuta con successo");
		}
		
	}
	
	
	private static void setFerie() {
		List<VigileDelFuocoBean> vigili=VigileDelFuocoDao.ottieni();
		for(VigileDelFuocoBean vigile:vigili) {
			int ferieAnnoPrecedente=vigile.getGiorniFerieAnnoPrecedente();
			int ferieAnnoCorrente=vigile.getGiorniFerieAnnoCorrente();
			ferieAnnoPrecedente+=ferieAnnoCorrente;
			VigileDelFuocoDao.aggiornaFeriePrecedenti(vigile.getEmail(), ferieAnnoPrecedente);
			VigileDelFuocoDao.aggiornaFerieCorrenti(vigile.getEmail(), 22);
		}
	}
	
	

	
	
	
	
	
}
