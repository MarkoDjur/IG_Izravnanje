package izravnanje2D;

import javafx.beans.property.SimpleStringProperty;

public class Koordinata {
	
	private SimpleStringProperty oznaka;
	private SimpleStringProperty y;
	private SimpleStringProperty x;
	private boolean definise_datum;
	
	public Koordinata(String oznaka, String y, String x, boolean definise_datum) {
		this.oznaka = new SimpleStringProperty(oznaka);
		this.y = new SimpleStringProperty(y);
		this.x = new SimpleStringProperty(x);
		this.definise_datum = definise_datum;
	}
	
	public String getOznaka() {
		return oznaka.get();
	}
	
	public String getY() {
		return y.get();
	}
	
	public String getX() {
		return x.get();
	}
	
	public boolean definiseDatum() {
		return definise_datum;
	}

}
