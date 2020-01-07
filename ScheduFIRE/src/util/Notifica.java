package util;

public class Notifica {
	private int severita;
	private String testo;
	private String path;
	
	public Notifica(int severita, String testo, String path) {
		this.severita = severita;
		this.testo = testo;
		this.path = path;
	}

	public int getSeverita() {
		return severita;
	}

	public void setSeverita(int severita) {
		this.severita = severita;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
