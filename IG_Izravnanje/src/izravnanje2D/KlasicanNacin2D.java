package izravnanje2D;

import izravnanje1D.Visina;
import javafx.collections.ObservableList;

public class KlasicanNacin2D {
	
	private ObservableList<Pravac> pravci;
	private ObservableList<Ugao> uglovi;
	private ObservableList<Duzina> duzine;
	private ObservableList<Koordinata> koordinate;
	private double s0;
	private double nivo_znacajnosti;
	
	// Broj mjerenja i broj nepoznatih parametara
	int n;
	int u;
	
	public KlasicanNacin2D(ObservableList<Pravac> pravci, ObservableList<Ugao> uglovi,
			ObservableList<Duzina> duzine, ObservableList<Koordinata> koordinate, double s0, double nivo_znacajnosti) {
		this.pravci = pravci;
		this.uglovi = uglovi;
		this.duzine = duzine;
		this.koordinate = koordinate;
		this.s0 = s0;
		this.nivo_znacajnosti = nivo_znacajnosti;
		n = pravci.size() + uglovi.size() + duzine.size();
		for(int i = 0; i < koordinate.size(); i++) {
			if(!koordinate.get(i).definiseDatum()) {
				u += 2;
			}
		}
		for(int i = 0; i < pravci.size(); i++) {
			if(pravci.get(i).getSelektovanoNepoznata()) {
				u++;
			}
		}
	}
	
	public void napraviIzvjestaj() {
		//formirajMatricuA();
		System.out.println("n = " + n + ",   u = " + u);
	}
	
	private boolean postojiPravacOd(String od) {
		boolean postoji = false;
		for(int i = 0; i < pravci.size(); i++) {
			if(pravci.get(i).getOd().equals(od)) {
				postoji = true;
			}
		}
		
		return postoji;
	}
	
	private Pravac pronadjiPravacOd(String od) {
		Pravac pravac = null;
		for(int i = 0; i < pravci.size(); i++) {
			if(pravci.get(i).getOd().equals(od)) {
				pravac = pravci.get(i);
			}
		}
		
		return pravac;
	}

}
