package model.bean;

import java.sql.Date;

public class faParteBean {

	//Variabili d'istanza

	private String tipologiaSquadra;
	private String emailVF;
	private Date composizioneSquadra;
	
	
	//Costruttore

	public faParteBean() {
		
	}

	
	public faParteBean(String tipologiaSquadra, String emailVF, Date composizioneSquadra) {
		this.tipologiaSquadra = tipologiaSquadra;
		this.emailVF = emailVF;
		this.composizioneSquadra = composizioneSquadra;
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
	public Date getComposizioneSquadra() {
		return composizioneSquadra;
	}
	public void setComposizioneSquadra(Date composizioneSquadra) {
		this.composizioneSquadra = composizioneSquadra;
	}
	

}
