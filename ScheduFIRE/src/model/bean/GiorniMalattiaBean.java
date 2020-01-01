package model.bean;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GiorniMalattiaBean {

	//Variabili d'istanza
	private int id;
	private Date dataInizio = null; 
	private Date dataFine = null;
	private String emailCT;
	private String emailVF;
	
	//Costruttore

	public GiorniMalattiaBean(int id, Date dataInizio, Date dataFine, String emailCT, String emailVF) {
		this.id=id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.emailCT = emailCT;
		this.emailVF = emailVF;
	}


	public GiorniMalattiaBean() {
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id=id;
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
