package model.bean;

public class VigileDelFuocoBean {
	//Variabili d'istanza
	private String nome, cognome, email, turno, mansione, username, grado;

	private int giorniFerieAnnoCorrente, giorniFerieAnnoPrecedente, caricoLavoro;
	private boolean adoperabile;
	
	//Costruttore	
	public VigileDelFuocoBean(){
		
	}

	public VigileDelFuocoBean(String nome, String cognome, String email, String turno, String mansione, String username,
			String grado, int giorniFerieAnnoCorrente, int giorniFerieAnnoPrecedente) {

		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.turno = turno;
		this.mansione = mansione;
		this.username = username;
		this.grado = grado;
		this.giorniFerieAnnoCorrente = giorniFerieAnnoCorrente;
		this.giorniFerieAnnoPrecedente = giorniFerieAnnoPrecedente;
		this.caricoLavoro = 0;
		this.adoperabile = true;
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
	
	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public int getGiorniFerieAnnoCorrente() {
		return giorniFerieAnnoCorrente;
	}

	public void setGiorniFerieAnnoCorrente(int giorniFerieAnnoCorrente) {
		this.giorniFerieAnnoCorrente = giorniFerieAnnoCorrente;
	}

	public int getGiorniFerieAnnoPrecedente() {
		return giorniFerieAnnoPrecedente;
	}

	public void setGiorniFerieAnnoPrecedente(int giorniFerieAnnoPrecedente) {
		this.giorniFerieAnnoPrecedente = giorniFerieAnnoPrecedente;
	}

	public int getCaricoLavoro() {
		return caricoLavoro;
	}

	public void setCaricoLavoro(int caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}

	public boolean isAdoperabile() {
		return adoperabile;
	}

	public void setAdoperabile(boolean adoperabile) {
		this.adoperabile = adoperabile;

	}

	public String toString() {
		return "VigileDelFuocoBean [nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", turno=" + turno
				+ ", mansione=" + mansione + ", username=" + username + ", grado=" + grado
				+ ", giorniFerieAnnoCorrente=" + giorniFerieAnnoCorrente + ", giorniFerieAnnoPrecedente="
				+ giorniFerieAnnoPrecedente + ", caricoLavoro=" + caricoLavoro + ", adoperabile=" + adoperabile + "]";
	}

	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		else if(!getClass().equals(obj.getClass())) {
			return false;
		}
		VigileDelFuocoBean other = (VigileDelFuocoBean) obj;
		return this.getCaricoLavoro() == other.getCaricoLavoro() &&
				this.getCognome().equals(other.getCognome()) &&
				this.getEmail().equals(other.getEmail()) &&
				this.getGiorniFerieAnnoCorrente() == other.getGiorniFerieAnnoCorrente() &&
				this.getGiorniFerieAnnoPrecedente() == other.getGiorniFerieAnnoPrecedente() &&
				this.getGrado().equals(other.getGrado()) &&
				this.getMansione().equals(other.getMansione()) &&
				this.getNome().equals(other.getNome()) &&
				this.getTurno().equals(other.getTurno()) &&
				this.getUsername().equals(other.getUsername());
	}
	
	
	public boolean equalsVF (VigileDelFuocoBean v) {
		if (this.getNome().equalsIgnoreCase(v.getNome()) &&
			this.getCognome().equalsIgnoreCase(v.getCognome()) &&
			this.getEmail().equalsIgnoreCase(v.getEmail())) {
			return true;
		}
		else return false;
	}

	
}
