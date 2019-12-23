package model.bean;

import java.sql.Date;

public class ComponenteDellaSquadraBean {

	//Variabili d'istanza

	private String tipologiaSquadra;
	private String emailVF;
	private Date giornoLavorativo;
	
	//Costruttore

	public ComponenteDellaSquadraBean() {
		
	}

	
	public ComponenteDellaSquadraBean(String tipologiaSquadra, String emailVF, Date giornoLavorativo) {
		this.tipologiaSquadra = tipologiaSquadra;
		this.emailVF = emailVF;
		this.giornoLavorativo = giornoLavorativo;
	}
	
	
	public String getTipologiaSquadra() {
		return tipologiaSquadra;
	}
	public void setTipologiaSquadra(String tipologiaSquadra) {
		this.tipologiaSquadra = tipologiaSquadra;
	}
	public String getEmailVF() {
		return emailVF;
	}
	public void setEmailVF(String emailVF) {
		this.emailVF = emailVF;
	}
	public Date getGiornoLavorativo() {
		return giornoLavorativo;
	}
	public void setGiornoLavorativo(Date giornoLavorativo) {
		this.giornoLavorativo = giornoLavorativo;
	}
	

}
