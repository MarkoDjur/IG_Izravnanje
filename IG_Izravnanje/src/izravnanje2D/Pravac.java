package izravnanje2D;

import javafx.beans.property.SimpleStringProperty;

public class Pravac {
	
	private SimpleStringProperty Od;
	private SimpleStringProperty Do;
	private SimpleStringProperty stepen;
	private SimpleStringProperty minut;
	private SimpleStringProperty sekund;
	private SimpleStringProperty tacnost;
	
	public Pravac(String Od, String Do, String stepen, String minut, String sekund, String tacnost) {
		this.Od = new SimpleStringProperty(Od);
		this.Do = new SimpleStringProperty(Do);
		this.stepen = new SimpleStringProperty(stepen);
		this.minut = new SimpleStringProperty(minut);
		this.sekund = new SimpleStringProperty(sekund);
		this.tacnost = new SimpleStringProperty(tacnost);
	}
	
	public String getOd() {
		return Od.get();
	}
	
	public String getDo() {
		return Do.get();
	}
	
	public String getVrijednost() {
		double st = Double.parseDouble(stepen.get());
		double m = Double.parseDouble(minut.get());
		double sk = Double.parseDouble(sekund.get());
		
		double vr = st + (m / 60) + (sk / 3600);
		
		String vrijednost = "" + vr;
		
		return vrijednost;
	}
	
	public String getStepen() {
		return stepen.get();
	}
	
	public String getMinut() {
		return minut.get();
	}
	
	public String getSekund() {
		return sekund.get();
	}
	
	public String getTacnost() {
		return tacnost.get();
	}

}
