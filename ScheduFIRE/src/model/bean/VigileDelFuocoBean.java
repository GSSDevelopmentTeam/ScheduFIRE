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

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (adoperabile ? 1231 : 1237);
		result = prime * result + caricoLavoro;
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + giorniFerieAnnoCorrente;
		result = prime * result + giorniFerieAnnoPrecedente;
		result = prime * result + ((grado == null) ? 0 : grado.hashCode());
		result = prime * result + ((mansione == null) ? 0 : mansione.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((turno == null) ? 0 : turno.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VigileDelFuocoBean other = (VigileDelFuocoBean) obj;
		if (adoperabile != other.adoperabile)
			return false;
		if (caricoLavoro != other.caricoLavoro)
			return false;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (giorniFerieAnnoCorrente != other.giorniFerieAnnoCorrente)
			return false;
		if (giorniFerieAnnoPrecedente != other.giorniFerieAnnoPrecedente)
			return false;
		if (grado == null) {
			if (other.grado != null)
				return false;
		} else if (!grado.equals(other.grado))
			return false;
		if (mansione == null) {
			if (other.mansione != null)
				return false;
		} else if (!mansione.equals(other.mansione))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (turno == null) {
			if (other.turno != null)
				return false;
		} else if (!turno.equals(other.turno))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
