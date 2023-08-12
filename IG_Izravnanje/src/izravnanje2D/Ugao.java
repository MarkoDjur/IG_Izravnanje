package izravnanje2D;

import javafx.beans.property.SimpleStringProperty;

public class Ugao {

	private SimpleStringProperty oznaka;
	private SimpleStringProperty stepen;
	private SimpleStringProperty minut;
	private SimpleStringProperty sekund;
	private SimpleStringProperty tacnost;

	public Ugao(String oznaka, String stepen, String minut, String sekund, String tacnost) {
		this.oznaka = new SimpleStringProperty(oznaka);
		this.stepen = new SimpleStringProperty(stepen);
		this.minut = new SimpleStringProperty(minut);
		this.sekund = new SimpleStringProperty(sekund);
		this.tacnost = new SimpleStringProperty(tacnost);
	}

	public String getOznaka() {
		return oznaka.get();
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

	public void setOznaka(String oznaka) {
		this.oznaka.set(oznaka);
	}

	public void setStepen(String stepen) {
		this.stepen.set(stepen);
	}

	public void setMinut(String minut) {
		this.minut.set(minut);
	}

	public void setSekund(String sekund) {
		this.sekund.set(sekund);
	}

	public void setTacnost(String tacnost) {
		this.tacnost.set(tacnost);
	}

	public Ugao(String oznaka) {
		this.oznaka = new SimpleStringProperty(oznaka);
	}

	public String getLijevo() {
		return oznaka.get().split("-")[0];
	}

	public String getSredina() {
		return oznaka.get().split("-")[1];
	}

	public String getDesno() {
		return oznaka.get().split("-")[2];
	}

}
