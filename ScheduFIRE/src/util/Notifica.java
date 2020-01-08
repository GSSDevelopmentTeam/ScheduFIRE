package util;

public class Notifica {
	private int severita;
	private String testo;
	private String path;
	private int id;
	
	public Notifica(int severita, String testo, String path, int id) {
		this.severita = severita;
		this.testo = testo;
		this.path = path;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
