package izravnanje2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import izravnanje1D.Visina;
import javafx.collections.ObservableList;
import matematika.Direkcioni;
import matematika.Matrix;

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
		formirajMatricuA();
	}
	
	private void formirajMatricuA() {
		List<Koordinata> nepoznate_koordinate = new ArrayList<Koordinata>();
		for(int i = 0; i < koordinate.size(); i++) {
			if(!koordinate.get(i).definiseDatum()) {
				nepoznate_koordinate.add(koordinate.get(i));
			}
		}
		double niz[][] = new double[n][u];
		
		// Za pravce
		int a = 0;
		for(int i = 0; i < pravci.size(); i++) {
			a = i;
			for(int j = 0; j < broj_nepoznatih_koordinata; j += 2) {
				if(nepoznate_koordinate.get(j).getOznaka().equals(pravci.get(i).getOd())) {
					niz[i][j] = a(pravci.get(i).getOd(), pravci.get(i).getDo());
					niz[i][j+1] = b(pravci.get(i).getOd(), pravci.get(i).getDo());
				}
				else if(nepoznate_koordinate.get(j).getOznaka().equals(pravci.get(i).getDo())) {
					niz[i][j] = a(pravci.get(i).getDo(), pravci.get(i).getOd());
					niz[i][j+1] = b(pravci.get(i).getDo(), pravci.get(i).getOd());
				}else {
					niz[i][j] = 0;
					niz[i][j+1] = 0;
				}
			}
		}
		a++;
		
		// Za uglove
		int b = 0;
		for(int i = 0; i < uglovi.size(); i++) {
			b = i;
			for(int j = 0; j < broj_nepoznatih_koordinata; j += 2) {
				if(nepoznate_koordinate.get(j).getOznaka().equals(pravci.get(i).getOd())) {
					String o[] = uglovi.get(i).getOznaka().split("-");
					String lijevi = o[0];
					String sredina = o[1];
					String desni = o[2];
					double a1 = 0;
					double a2 = 0;
					double b1 = 0;
					double b2 = 0;
					try {
						a1 = a(sredina, desni);
					}catch(Exception e) {}
					try {
						a2 = a(sredina, lijevi);
					}catch(Exception e) {}
					try {
						b1 = b(sredina, desni);
					}catch(Exception e) {}
					try {
						b2 = b(sredina, lijevi);
					}catch(Exception e) {}
					
					niz[i+a][j] = a1 - a2;
					niz[i+a][j+1] = b1 - b2;
				}
				else if(nepoznate_koordinate.get(j).getOznaka().equals(pravci.get(i).getOd())) {
					String o[] = uglovi.get(i).getOznaka().split("-");
					String lijevi = o[0];
					String sredina = o[1];
					String desni = o[2];
					double a1 = 0;
					double a2 = 0;
					double b1 = 0;
					double b2 = 0;
					try {
						a1 = a(sredina, lijevi);
					}catch(Exception e) {}
					try {
						a2 = a(sredina, desni);
					}catch(Exception e) {}
					try {
						b1 = b(sredina, lijevi);
					}catch(Exception e) {}
					try {
						b2 = b(sredina, desni);
					}catch(Exception e) {}
					
					niz[i+a][j] = a1 - a2;
					niz[i+a][j+1] = b1 - b2;
				}else {
					niz[i+a][j] = 0;
					niz[i+a][j+1] = 0;
				}
			}
		}
		b++;
		
		// Za duzine
		for(int i = 0; i < duzine.size(); i++) {
			for(int j = 0; j < broj_nepoznatih_koordinata; j += 2) {
				if(nepoznate_koordinate.get(j).getOznaka().equals(duzine.get(i).getOd())) {
					niz[i+a+b][j] = A(duzine.get(i).getOd(), duzine.get(i).getDo());
					niz[i+a+b][j+1] = B(duzine.get(i).getOd(), duzine.get(i).getDo());
				}
				else if(nepoznate_koordinate.get(j).getOznaka().equals(duzine.get(i).getDo())) {
					niz[i+a+b][j] = A(duzine.get(i).getDo(), duzine.get(i).getOd());
					niz[i+a+b][j+1] = B(duzine.get(i).getDo(), duzine.get(i).getOd());
				}else {
					niz[i+a+b][j] = 0;
					niz[i+a+b][j+1] = 0;
				}
			}
		}
		
		Matrix matrix = new Matrix(niz);
		System.out.println(matrix);
		
	}
	
	private double a(String oznakaOd, String oznakaDo) {
		Koordinata Od = pronadjiKoordinatu(oznakaOd);
		Koordinata Do = pronadjiKoordinatu(oznakaDo);
		double direkcioni = direkcioni(Od, Do);
		double duzina = duzina(Od, Do);
		double a = -RO*Math.cos(Math.toRadians(direkcioni))/(duzina * 1000);
		
		return a;
	}
	
	private double b(String oznakaOd, String oznakaDo) {
		Koordinata Od = pronadjiKoordinatu(oznakaOd);
		Koordinata Do = pronadjiKoordinatu(oznakaDo);
		double direkcioni = direkcioni(Od, Do);
		double duzina = duzina(Od, Do);
		double b = RO*Math.sin(Math.toRadians(direkcioni))/(duzina * 1000);
		
		return b;
	}
	
	private double A(String oznakaOd, String oznakaDo) {
		Koordinata Od = pronadjiKoordinatu(oznakaOd);
		Koordinata Do = pronadjiKoordinatu(oznakaDo);
		double direkcioni = direkcioni(Od, Do);
		double A = -Math.sin(Math.toRadians(direkcioni));
		
		return A;
	}
	
	private double B(String oznakaOd, String oznakaDo) {
		Koordinata Od = pronadjiKoordinatu(oznakaOd);
		Koordinata Do = pronadjiKoordinatu(oznakaDo);
		double direkcioni = direkcioni(Od, Do);
		double B = -Math.cos(Math.toRadians(direkcioni));
		
		return B;
	}
	
	private double direkcioni(Koordinata sa, Koordinata na) {
		double saY = Double.parseDouble(sa.getY());
		double saX = Double.parseDouble(sa.getX());
		double naY = Double.parseDouble(na.getY());
		double naX = Double.parseDouble(na.getX());
		
		return Direkcioni.sracunajDirekcioni(saY, saX, naY, naX);
	}
	
	private double duzina(Koordinata Od, Koordinata Do) {
		double odY = Double.parseDouble(Od.getY());
		double odX = Double.parseDouble(Od.getX());
		double doY = Double.parseDouble(Do.getY());
		double doX = Double.parseDouble(Do.getX());
		
		return Direkcioni.sracunajDuzinu(odY, odX, doY, doX);
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
	
	private Koordinata pronadjiKoordinatu(String oznaka) {
		Koordinata k = null;
		for(int i = 0; i < koordinate.size(); i++) {
			if(koordinate.get(i).getOznaka().equals(oznaka)) {
				k = koordinate.get(i);
				break;
			}
		}
		
		return k;
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
