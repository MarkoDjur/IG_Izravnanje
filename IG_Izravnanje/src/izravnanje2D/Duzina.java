package izravnanje2D;

import javafx.beans.property.SimpleStringProperty;

public class Duzina {

	private SimpleStringProperty Od;
	private SimpleStringProperty Do;
	private SimpleStringProperty vrijednost;
	private SimpleStringProperty tacnost;
	private double mm;
	private double ppm;
	private double duz;

	

	public Duzina(String Od, String Do, String vrijednost, String tacnost) {
		this.Od = new SimpleStringProperty(Od);
		this.Do = new SimpleStringProperty(Do);
		this.vrijednost = new SimpleStringProperty(vrijednost);
		this.tacnost = new SimpleStringProperty(tacnost);
	}

	public static double calculateTacnost(double mm, double ppm, double duz) {
        return mm + (ppm * (duz / 1000));
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

	public void setOd(String Od) {
		this.Od.set(Od);
	}

	public void setDo(String Do) {
		this.Od.set(Do);
	}

	public void setVrijednost(String vrijednost) {
		this.vrijednost.set(vrijednost);
	}

	public void setTacnost(String tacnost) {
		this.tacnost.set(tacnost);
	}

}
