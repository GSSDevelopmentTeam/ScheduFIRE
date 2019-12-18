package model.bean;

import java.sql.Date;

public class ComposizioneSquadreBean {
	
	//Variabili d'istanza
	private Date data;
	private String email;
	
	//Costruttore
	
	public ComposizioneSquadreBean() {
	}
	
	public ComposizioneSquadreBean(Date data, String email) {
		this.data = data;
		this.email = email;
	}
	
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
