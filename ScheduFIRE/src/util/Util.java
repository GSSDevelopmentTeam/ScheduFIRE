package util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import control.NotEnoughMembersException;
import model.bean.ListaSquadreBean;
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

	public static List<ListaSquadreBean> generaSquadra(Date data) throws NotEnoughMembersException {
		//Prendiamo i vigili disponibili
		List<VigileDelFuocoBean> disponibili = VigileDelFuocoDao.getDisponibili(data);
		
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
		if(abbastanzaPerTurno(caposquadra.size(), autista.size(), vigile.size())) {
			sorting(caposquadra);
			sorting(autista);
			sorting(vigile);
			
			List<ListaSquadreBean> squadra = assegnaMansioni(caposquadra, autista, vigile);
			return squadra;
		}
		else {
			throw new NotEnoughMembersException();
		}
	}

	private static List<ListaSquadreBean> assegnaMansioni(List<VigileDelFuocoBean> caposquadra,
			List<VigileDelFuocoBean> autista, List<VigileDelFuocoBean> vigile) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void sorting(List<VigileDelFuocoBean> caposquadra) {
		// TODO Auto-generated method stub
		
	}

	public static boolean abbastanzaPerTurno(int numCS, int numAut, int numVF) throws NotEnoughMembersException {
		if(numCS >= 4) {
			if((numAut >= 3) && (numVF >= 5)) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (numCS >= 2) {
			if((numAut >= 3) && (numVF >= (9 - numCS))) {
				return true;
			}
			else {
				return false;
			}
		}
		else return false;
	}
}
