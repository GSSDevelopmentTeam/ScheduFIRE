package model.bean;

public class CapoTurnoBean {

	//Variabili d'istanza
		private String nome, cognome, email,turno,username;
		
		//Costruttore
		public CapoTurnoBean(){
			
		}
		
		
		public CapoTurnoBean(String nome, String cognome,String email, String turno, String username) {
			this.nome=nome;
			this.cognome=cognome;
			this.email=email;
			this.turno=turno;
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

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
			result = prime * result + ((email == null) ? 0 : email.hashCode());
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
			CapoTurnoBean other = (CapoTurnoBean) obj;
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
