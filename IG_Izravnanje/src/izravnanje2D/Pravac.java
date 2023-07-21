package izravnanje2D;

import javafx.beans.property.SimpleStringProperty;

public class Pravac {
	
	private SimpleStringProperty Od;
	private SimpleStringProperty Do;
	private SimpleStringProperty stepen;
	private SimpleStringProperty minut;
	private SimpleStringProperty sekund;
	private SimpleStringProperty tacnost;
	private boolean selektovano_poznata;
	private boolean selektovano_nepoznata;
	
	public Pravac(String Od, String Do, String stepen, String minut, String sekund, String tacnost, boolean selektovano_poznata, boolean selektovano_nepoznata) {
		this.Od = new SimpleStringProperty(Od);
		this.Do = new SimpleStringProperty(Do);
		this.stepen = new SimpleStringProperty(stepen);
		this.minut = new SimpleStringProperty(minut);
		this.sekund = new SimpleStringProperty(sekund);
		this.tacnost = new SimpleStringProperty(tacnost);
		this.selektovano_poznata = selektovano_poznata;
		this.selektovano_nepoznata = selektovano_nepoznata;
	}
	
	public String getOd() {
		return Od.get();
	}
	
	public String getDo() {
		return Do.get();
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
	
	public String getVrijednost() {
		double st = Double.parseDouble(stepen.get());
		double m = Double.parseDouble(minut.get());
		double sk = Double.parseDouble(sekund.get());
		
		double vr = st + (m / 60) + (sk / 3600);
		
		String vrijednost = "" + vr;
		
		return vrijednost;
	}
	
	public boolean getSelektovanoPoznata() {
		return selektovano_poznata;
	}
	
	public boolean getSelektovanoNepoznata() {
		return selektovano_nepoznata;
	}

}
