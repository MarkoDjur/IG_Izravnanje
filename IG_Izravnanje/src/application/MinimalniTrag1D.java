package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MinimalniTrag1D {
	
	private static int DEFEKT = 1;

	private ObservableList<Visina> visine;
	private ObservableList<VisinskaRazlika> visinske_razlike;
	private Matrix matrica_A;
	private Matrix matrica_P;
	private Matrix vektor_f;
	private Matrix matrica_N;
	private Matrix vektor_n;
	private Matrix matrica_BT;
	private Matrix matrica_Qx;
	private Matrix vektor_x;
	private Matrix vektor_v;
	private double s_ocjenjeno;

	public MinimalniTrag1D(ObservableList<Visina> visine, ObservableList<VisinskaRazlika> visinske_razlike) {
		this.visine = visine;
		this.visinske_razlike = visinske_razlike;
	}

	public void izracunajPriblizneVisine() {
		ObservableList<Visina> poznate_visine = FXCollections.observableArrayList();
		for (int i = 0; i < visine.size(); i++) {
			if (!visine.get(i).getVisina().equals("")) {
				poznate_visine.add(visine.get(i));
			}
		}

		for (int i = 0; i < visine.size(); i++) {
			if (visine.get(i).getVisina().equals("")) {
				for (int j = 0; j < visinske_razlike.size(); j++) {
					if (visine.get(i).getOznaka().equals(visinske_razlike.get(j).getDo())) { // Prvi slucaj
						try {
							for (int k = 0; k < poznate_visine.size(); k++) {
								if (visinske_razlike.get(j).getOd().equals(poznate_visine.get(k).getOznaka())) {
									visine.get(i).setVisina(
											Double.toString(Double.parseDouble(poznate_visine.get(k).getVisina())
													+ Double.parseDouble(visinske_razlike.get(j).getVisinskaRaz())));
									break;
								}
							}
						} catch (Exception e) {
							// Uradi nesto?
						}
					} else if (visine.get(i).getOznaka().equals(visinske_razlike.get(j).getOd())) { // Drugi slucaj
						try {
							for (int k = 0; k < poznate_visine.size(); k++) {
								if (visinske_razlike.get(j).getDo().equals(poznate_visine.get(k).getOznaka())) {
									visine.get(i).setVisina(
											Double.toString(Double.parseDouble(poznate_visine.get(k).getVisina())
													- Double.parseDouble(visinske_razlike.get(j).getVisinskaRaz())));
									break;
								}
							}
						} catch (Exception e) {
							// Uradi nesto?
						}
					}
				}
			}
		}

		formirajMatricuA();
		formirajMatricuP();
		formirajVektorf();
		izracunajMatricuN();
		izracunajVektorn();
		formirajMatricuBT();
		izracunajMatricuQx();
		izracunajVektorx();
		izracunajVektorv();
		izracunajStandardnoOdstupanje();

	}

	private void formirajMatricuA() {
		int n = visine.size();
		int u = visinske_razlike.size();
		double niz[][] = new double[u][n];

		for (int i = 0; i < u; i++) {
			for (int j = 0; j < n; j++) {
				String OD = visinske_razlike.get(i).getOd();
				String DO = visinske_razlike.get(i).getDo();
				if (visine.get(j).getOznaka().equals(OD)) {
					niz[i][j] = -1;
				} else if (visine.get(j).getOznaka().equals(DO)) {
					niz[i][j] = 1;
				} else {
					niz[i][j] = 0;
				}
			}
		}

		matrica_A = new Matrix(niz);
	}

	private void formirajMatricuP() {
		int u = visinske_razlike.size();
		double niz[][] = new double[u][u];

		// Ako je dat broj stanica onda je p=1/n
		if (visinske_razlike.get(0).getDuzinaStrane().equals("")) {
			for (int i = 0; i < u; i++) {
				for (int j = 0; j < u; j++) {
					if (i == j) {
						niz[i][j] = 1 / Double.parseDouble(visinske_razlike.get(i).getBrojStanica());
					}else {
						niz[i][j] = 0;
					}
				}
			}
		}

		// Ako je data duzina nivelmanske strane onda je p=1/d[km]
		if (visinske_razlike.get(0).getBrojStanica().equals("")) {
			for (int i = 0; i < u; i++) {
				for (int j = 0; j < u; j++) {
					if (i == j) {
						niz[i][j] = 1 / (Double.parseDouble(visinske_razlike.get(i).getDuzinaStrane())/1000);
					}else {
						niz[i][j] = 0;
					}
				}
			}
		}
		
		matrica_P = new Matrix(niz);
	}
	
	private void formirajVektorf() {
		int n = visinske_razlike.size();
		double niz[][] = new double[n][1];
		
		for(int i = 0; i < n; i++) {
			double visinska_razlika = Double.parseDouble(visinske_razlike.get(i).getVisinskaRaz());
			String OD = visinske_razlike.get(i).getOd();
			String DO = visinske_razlike.get(i).getDo();
			niz[i][0] = (nadjiVisinu(DO) - nadjiVisinu(OD) - visinska_razlika) * 1000;
		}
		
		vektor_f = new Matrix(niz);
		
	}
	
	private void izracunajMatricuN() {
		Matrix AT = matrica_A.transpose();
		matrica_N = AT.multiply(matrica_P.multiply(matrica_A));
	}
	
	private void izracunajVektorn() {
		Matrix AT = matrica_A.transpose();
		vektor_n = AT.multiply(matrica_P.multiply(vektor_f));
	}
	
	private void formirajMatricuBT() {
		int u = visine.size();
		double niz[][] = new double[1][u];
		
		for(int i = 0; i < u; i++) {
			niz[0][i] = 1;
		}
		
		matrica_BT = new Matrix(niz);
	}
	
	private void izracunajMatricuQx() {
		double niz_N[][] = matrica_N.getMatrix();
		int n = visine.size();
		double niz_Nprosireno[][] = new double[n+1][n+1];
		double niz_BT[][] = matrica_BT.getMatrix();
		double niz_B[][] = matrica_BT.transpose().getMatrix();
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				niz_Nprosireno[i][j] = niz_N[i][j];
			}
		}
		for(int i = 0; i < n; i++) {
			niz_Nprosireno[n][i] = niz_BT[0][i];
		}
		for(int i = 0; i < n; i++) {
			niz_Nprosireno[i][n] = niz_B[i][0];
		}
		niz_Nprosireno[n][n] = 0;
		
		Matrix N_prosireno = new Matrix(niz_Nprosireno);
		Matrix Qx_prosireno = N_prosireno.inverse();
		double niz_Qxprosireno[][] = Qx_prosireno.getMatrix();
		double niz_Qx[][] = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				niz_Qx[i][j] = niz_Qxprosireno[i][j];
			}
		}
		
		matrica_Qx = new Matrix(niz_Qx);
	}
	
	private void izracunajVektorx() {
		vektor_x = matrica_Qx.multConst(-1).multiply(vektor_n);
		System.out.println(vektor_x);
	}
	
	private void izracunajVektorv() {
		vektor_v = (matrica_A.multiply(vektor_x)).add(vektor_f);
		System.out.println(vektor_v);
	}
	
	private void izracunajStandardnoOdstupanje() {
		int n = visinske_razlike.size();
		int u = visine.size();
		Matrix vt = vektor_v.transpose();
		Matrix vtp = vt.multiply(matrica_P);
		Matrix vtpv = vtp.multiply(vektor_v);
		s_ocjenjeno = Math.sqrt(vtpv.getMatrix()[0][0] / (n - u + DEFEKT));
		System.out.println(s_ocjenjeno);
	}
	
	private double nadjiVisinu(String oznaka) {
		String v = "";
		for(int i = 0; i < visine.size(); i++) {
			if(visine.get(i).getOznaka().equals(oznaka)) {
				v = visine.get(i).getVisina();
				break;
			}
		}
		
		double visina = Double.parseDouble(v);
		
		return visina;
	}

}
