package izravnanje2D;

import javafx.beans.property.SimpleStringProperty;

public class Duzina {
	
	private SimpleStringProperty Od;
	private SimpleStringProperty Do;
	private SimpleStringProperty vrijednost;
	private SimpleStringProperty tacnost;
	
	public Duzina(String Od, String Do, String vrijednost, String tacnost) {
		this.Od = new SimpleStringProperty(Od);
		this.Do = new SimpleStringProperty(Do);
		this.vrijednost = new SimpleStringProperty(vrijednost);
		this.tacnost = new SimpleStringProperty(tacnost);
	}
	
	public String getOd() {
		return Od.get();
	}
	
	public String getDo() {
		return Do.get();
	}
	
	public String getVrijednost() {
		return vrijednost.get();
	}
	
	public String getTacnost() {
		return tacnost.get();
	}

}
