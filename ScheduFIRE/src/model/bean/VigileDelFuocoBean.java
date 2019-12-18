package model.bean;

public class VigileDelFuocoBean {
	//Variabili d'istanza
	private String nome, cognome, email, turno, mansione, username;
	
	//Costruttore
	
	public VigileDelFuocoBean() {
		
	}
	
	public VigileDelFuocoBean(String nome, String cognome, String email,String turno,String mansione, String username) {
		this.nome=nome;
		this.cognome=cognome;
		this.email=email;
		this.turno=turno;
		this.mansione=mansione;
		this.username=username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getMansione() {
		return mansione;
	}

	public void setMansione(String mansione) {
		this.mansione = mansione;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
