package model.bean;

import java.sql.Date;

public class SquadraBean {

	//Variabili d'istanza

	private String tipologia;
	private int caricLavoro;
	private Date data;
	
	
	//Costruttore

	public SquadraBean() {
	}


	public SquadraBean(String tipologia, int caricLavoro, Date data) {
		this.tipologia = tipologia;
		this.caricLavoro = caricLavoro;
		this.data = data;
	}
	
	
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public int getCaricLavoro() {
		return caricLavoro;
	}
	public void setCaricLavoro(int caricLavoro) {
		this.caricLavoro = caricLavoro;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
