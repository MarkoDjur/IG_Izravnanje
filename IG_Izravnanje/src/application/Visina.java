package application;

import javafx.beans.property.SimpleStringProperty;

public class Visina {
	
	private SimpleStringProperty oznaka;
	private SimpleStringProperty visina;
	
	public Visina(String oznaka, String visina) {
		this.oznaka = new SimpleStringProperty(oznaka);
		this.visina = new SimpleStringProperty(visina);
	}

	public String getOznaka() {
		return oznaka.get();
	}

	public String getVisina() {
		return visina.get();
	}
	
	public void setVisina(String visina) {
		this.visina = new SimpleStringProperty(visina);
	}
	
}
