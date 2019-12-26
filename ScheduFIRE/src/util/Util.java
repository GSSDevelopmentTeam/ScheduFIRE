package util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import control.NotEnoughMembersException;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.SquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.*;

/**
 * La classe Util contiene diversi metodi statici utili per essere chiamati
 * da diverse classi del sistema. 
 * @author Emanuele Bombardelli
 *
 */
public class Util {

	/**
	 * Il metodo codifica la stringa passata come parametro in Base64.
	 * @param pwd la stringa da codificare
	 * @return la stringa codificata
	 * @see Base64.Encoder
	 */
	public static String codificaPwd(String pwd){

		String pwdCodificata = Base64.getEncoder().encodeToString(pwd.getBytes());
		return pwdCodificata;
	} 

	/**
	 * Il metodo decodifica una stringa codificata in Base64.
	 * @param pwd_Cod la stringa da decodificare
	 * @return la stringa decidificata
	 * @see Base64.Decoder
	 */
	public static String decodificaPwd(String pwd_Cod){

		byte[] ByteDecodificati = Base64.getDecoder().decode(pwd_Cod);
		String pwdDecodificata = new String(ByteDecodificati);
		return pwdDecodificata;
	} 

	public static List<ComponenteDellaSquadraBean> generaSquadra(Date data) throws NotEnoughMembersException {
		//Prendiamo i vigili disponibili
		List<VigileDelFuocoBean> disponibili = VigileDelFuocoDao.getDisponibili(data);
		//Li dividiamo in 3 liste
		List<VigileDelFuocoBean> caposquadra = new ArrayList<>();
		List<VigileDelFuocoBean> autista = new ArrayList<>();
		List<VigileDelFuocoBean> vigile = new ArrayList<>();

		for(VigileDelFuocoBean membro : disponibili) {
			if(membro.getMansione().toLowerCase() == "capo squadra") {
				caposquadra.add(membro);
			}
			else if(membro.getMansione().toLowerCase() == "autista") {
				autista.add(membro);
			}
			else if(membro.getMansione().toLowerCase() == "vigile") {
				vigile.add(membro);
			}
		}
		//Controlliamo se abbiamo abbastanza personale per fare squadra, altrimenti lanciamo l'eccezione
		if(abbastanzaPerTurno(caposquadra.size(), autista.size(), vigile.size())) {
			//Ordiniamo in ordine ascendente
			caposquadra.sort((VigileDelFuocoBean cs1, VigileDelFuocoBean cs2) -> 
				cs1.getCaricoLavoro() - cs2.getCaricoLavoro());
			autista.sort((VigileDelFuocoBean a1, VigileDelFuocoBean a2) ->
				a1.getCaricoLavoro() - a2.getCaricoLavoro());
			vigile.sort((VigileDelFuocoBean v1, VigileDelFuocoBean v2) ->
				v1.getCaricoLavoro() - v2.getCaricoLavoro());
			//Assegnamo in ordine decrescente
			List<ComponenteDellaSquadraBean> squadra = assegnaMansioni(caposquadra, autista, vigile, data);
			return squadra;
		}
		else {
			throw new NotEnoughMembersException();
		}
	}

	/**
	 * Il metodo conta il personale disponibile in caserma per vedere se è possibile creare un turno con 
	 * le persone considerate. Il numero di persone disponibili minime è considerato come un vettore
	 * (N. Capo Squadra, N. Autisti, N. Vigili del Fuoco) con due diverse configurazioni: (2, 3, 7), 
	 * (3, 3, 6) oppure (4, 3, 5).
	 * @param numCS il numero di Capo Squadra
	 * @param numAut il numero di Autisti
	 * @param numVF il numero di Vigili del Fuoco
	 * @return TRUE se è possibile creare un turno con i disponibili, FALSE altrimenti
	 */
	public static boolean abbastanzaPerTurno(int numCS, int numAut, int numVF) {
		if(numAut < 3) {
			return false;
		}
		else if((numCS >= 2) && (numCS <=4) && (numCS + numVF >= 9)) {
			return true;
		}
		else if((numCS < 2) || (numVF < 5) || ((numCS > 4) && (numVF < 5))) {
			return false;
		}
		else return true;
	}
	
	private static List<ComponenteDellaSquadraBean> assegnaMansioni(List<VigileDelFuocoBean> caposquadra,
			List<VigileDelFuocoBean> autista, List<VigileDelFuocoBean> vigile, Date data) {
		List<ComponenteDellaSquadraBean> toReturn = new ArrayList<>();
		SquadraBean salaOp = new SquadraBean("Sala Operativa", 3, data);
		SquadraBean primaP = new SquadraBean("Prima Partenza", 3, data);
		SquadraBean autoSc = new SquadraBean("Autoscala", 2, data);
		SquadraBean autoBo = new SquadraBean("Autobotte", 1, data);
		int contaSala = 0;
		int contaPrim = 0;
		int contaAutS = 0;
		for(VigileDelFuocoBean vf : vigile) {
			if(contaSala <= 2) {
				toReturn.add(new ComponenteDellaSquadraBean(salaOp.getTipologia(), vf.getEmail(), data));
				contaSala++;
			}
			else if(contaPrim <= 3) {
				toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), vf.getEmail(), data));
				contaPrim++;
			}
			else if(contaAutS <= 1) {
				toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), vf.getEmail(), data));
				contaAutS++;
			}
			else {
				toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), vf.getEmail(), data));
			}
		}

		for(VigileDelFuocoBean cs : caposquadra) {
			if(contaSala <= 3) {
				toReturn.add(new ComponenteDellaSquadraBean(salaOp.getTipologia(), cs.getEmail(), data));
				contaSala++;
			}
			else if(contaPrim <= 4) {
				toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), cs.getEmail(), data));
				contaPrim++;
			}
			else if(contaAutS <= 2) {
				toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), cs.getEmail(), data));
				contaAutS++;
			}
			else {
				toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), cs.getEmail(), data));
				break;
			}
		}

		int i = 0;
		for(VigileDelFuocoBean au : autista) {
			if(i == 0) {
				toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), au.getEmail(), data));
				i++;
			}
			else if(i == 1) {
				toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), au.getEmail(), data));
				i++;
			}
			else {
				toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), au.getEmail(), data));
			}
		}

		return toReturn;
	}
	
	public static List<VigileDelFuocoBean> ottieniSquadra(Date data) {
		List<ComponenteDellaSquadraBean> lista = ComponenteDellaSquadraDao.getComponenti(data);
		List<VigileDelFuocoBean> squadra = new ArrayList<>();
		for(ComponenteDellaSquadraBean membro : lista) {
			squadra.add(VigileDelFuocoDao.ottieni(membro.getEmailVF()));
		}
		return squadra;
	}
}
