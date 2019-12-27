package model.bean;

import java.sql.Date;

public class ListaSquadreBean {
	
	//Variabili d'istanza
	private Date giornoLavorativo;
	private String emailCT;
	private int oraIniziale;
	
	//Costruttore
	public ListaSquadreBean(Date giornoLavorativo, String emailCT, int oraIniziale) {
		this.giornoLavorativo = giornoLavorativo;
		this.emailCT = emailCT;
		this.oraIniziale = oraIniziale;
	}

	public ListaSquadreBean() {
	}

	public Date getGiornoLavorativo() {
		return giornoLavorativo;
	}

	public void setGiornoLavorativo(Date giornoLavorativo) {
		this.giornoLavorativo = giornoLavorativo;
	}

	public String getEmailCT() {
		return emailCT;
	}

	public void setEmailCT(String emailCT) {
		this.emailCT = emailCT;
	}

	public int getOraIniziale() {
		return oraIniziale;
	}

	public void setOraIniziale(int oraIniziale) {
		this.oraIniziale = oraIniziale;
	}
	
	

}
