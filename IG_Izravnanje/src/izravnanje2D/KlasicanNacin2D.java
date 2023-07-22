package izravnanje2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import izravnanje1D.Visina;
import javafx.collections.ObservableList;

public class KlasicanNacin2D {
	
	private static final double RO = 180 * 3600 / Math.PI;
	
	private ObservableList<Pravac> pravci;
	private ObservableList<Ugao> uglovi;
	private ObservableList<Duzina> duzine;
	private ObservableList<Koordinata> koordinate;
	private double s0;
	private double nivo_znacajnosti;
	
	// Broj mjerenja i broj nepoznatih parametara
	int n;
	int broj_nepoznatih_koordinata;
	int broj_uglova_z;
	int u;
	
	public KlasicanNacin2D(ObservableList<Pravac> pravci, ObservableList<Ugao> uglovi,
			ObservableList<Duzina> duzine, ObservableList<Koordinata> koordinate, double s0, double nivo_znacajnosti) {
		this.pravci = pravci;
		this.uglovi = uglovi;
		this.duzine = duzine;
		this.koordinate = koordinate;
		this.s0 = s0;
		this.nivo_znacajnosti = nivo_znacajnosti;
		
		// Odredjivanje n
		n = pravci.size() + uglovi.size() + duzine.size();
		for(int i = 0; i < koordinate.size(); i++) {
			if(!koordinate.get(i).definiseDatum()) {
				broj_nepoznatih_koordinata += 2;
			}
		}
		
		// Odredjivanje u
		List<String> p = new ArrayList<String>();
		for(int i = 0; i < pravci.size(); i++) {
			if(pravci.get(i).getSelektovanoNepoznata()) {
				p.add(pravci.get(i).getOd());
			}
		}
		
		broj_uglova_z = prebrojDuplikate(p);
		u = broj_nepoznatih_koordinata + broj_uglova_z;
	}
	
	public void napraviIzvjestaj() {
		//formirajMatricuA();
		System.out.println("n = " + n + ",   u = " + u);
	}
	
	private void formirajMatricuA() {
		List<Koordinata> nepoznate_koordinate = new ArrayList<Koordinata>();
		for(int i = 0; i < koordinate.size(); i++) {
			if(!koordinate.get(i).definiseDatum()) {
				nepoznate_koordinate.add(koordinate.get(i));
			}
		}
		double niz[][] = new double[n][u];
		for(int i = 0; i < pravci.size(); i++) {
			for(int j = 0; j < broj_nepoznatih_koordinata; j += 2) {
				if(nepoznate_koordinate.get(j).getOznaka().equals(pravci.get(i).getOd())) {
					// Sracunaj koeficijent a
					// Sracunaj koeficijent b
				}
			}
		}
		
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
	
	private int prebrojDuplikate(List<String> p) {
		String s[] = new String[p.size()];
		for(int i = 0; i < s.length; i++) {
			s[i] = p.get(i);
		}
		
		s = Arrays.stream(s).distinct().toArray(String[]::new);
		
		return s.length;
		
	}
	

}
