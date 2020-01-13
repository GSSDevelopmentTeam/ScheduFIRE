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

		public boolean equals(Object obj) {
			if(obj == null) {
				return false;
			}
			else if(this.getClass() != obj.getClass()) {
				return false;
			}
			CapoTurnoBean other = (CapoTurnoBean) obj;
			return this.getCognome().equals(other.getCognome()) &&
					this.getEmail().equals(other.getEmail()) &&
					this.getNome().equals(other.getNome()) &&
					this.getTurno().equals(other.getTurno()) &&
					this.getUsername().equals(other.getUsername());
		}
	
}
