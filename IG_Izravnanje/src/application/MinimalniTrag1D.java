package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MinimalniTrag1D {

	private ObservableList<Visina> visine;
	private ObservableList<VisinskaRazlika> visinske_razlike;
	private Matrix matrica_A;
	

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

	}
	
	private void formirajMatricuA() {
		int n = visine.size();
		int u = visinske_razlike.size();
		double niz[][] = new double[u][n];
		
		for(int i = 0; i < u; i++) {
			for(int j = 0; j < n; j++) {
				String OD = visinske_razlike.get(i).getOd();
				String DO = visinske_razlike.get(i).getDo();
				if(visine.get(j).getOznaka().equals(OD)) {
					niz[i][j] = -1;
				}else if(visine.get(j).getOznaka().equals(DO)) {
					niz[i][j] = 1;
				}else {
					niz[i][j] = 0;
				}
			}
		}
		
		matrica_A = new Matrix(niz);
		System.out.println(matrica_A);
	}

}
