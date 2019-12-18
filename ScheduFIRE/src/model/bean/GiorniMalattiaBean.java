package model.bean;

import java.sql.Date;

public class GiorniMalattiaBean {

	//Variabili d'istanza

	private Date dataInizio;
	private Date dataFine;
	private String emailCT;
	private String emailVF;
	
	//Costruttore

	public GiorniMalattiaBean(Date dataInizio, Date dataFine, String emailCT, String emailVF) {
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.emailCT = emailCT;
		this.emailVF = emailVF;
	}


	public GiorniMalattiaBean() {
	}


	public Date getDataInizio() {
		return dataInizio;
	}


	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}


	public Date getDataFine() {
		return dataFine;
	}


	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}


	public String getEmailCT() {
		return emailCT;
	}


	public void setEmailCT(String emailCT) {
		this.emailCT = emailCT;
	}


	public String getEmailVF() {
		return emailVF;
	}


	public void setEmailVF(String emailVF) {
		this.emailVF = emailVF;
	}
	
	
	
	
}
