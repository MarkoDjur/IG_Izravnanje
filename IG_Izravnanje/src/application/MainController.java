package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import izravnanje1D.KlasicanNacin1D;
import izravnanje1D.MinimalniTrag1D;
import izravnanje1D.Visina;
import izravnanje1D.VisinskaRazlika;
import izravnanje2D.Duzina;
import izravnanje2D.Pravac;
import izravnanje2D.Ugao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

	// 1D mreza
	private ObservableList<VisinskaRazlika> data_vr = FXCollections.observableArrayList();
	private ObservableList<Visina> data_v = FXCollections.observableArrayList();
	private Visina visina;
	private VisinskaRazlika visinskaRazlika;

	@FXML
	private RadioButton radio_klasicno;
	@FXML
	private RadioButton radio_minimalanTrag;
	@FXML
	private RadioButton datum1d;
	@FXML
	public Button ucitajButton;

	@FXML
	private Label myLabel;
	@FXML
	private TextField txt_od;
	@FXML
	private TextField txt_do;
	@FXML
	private TextField txt_visinskaRazlika;
	@FXML
	private TextField txt_duzinaStrane;
	@FXML
	private TextField txt_brojStanica;
	@FXML
	private TextField txt_oznaka;
	@FXML
	private TextField txt_visina;
	@FXML
	private TextField txt_s0;
	@FXML
	private TextField txt_nivoZnacajnosti;

	@FXML
	private TableView<VisinskaRazlika> tabela_vr;
	@FXML
	public TableColumn OD;
	@FXML
	public TableColumn DO;
	@FXML
	public TableColumn VISINSKA_RAZLIKA;
	@FXML
	public TableColumn DUZINA_NIVELMANSKE_STRANE;
	@FXML
	public TableColumn BROJ_STANICA;

	@FXML
	private TableView<Visina> tabela_v;
	@FXML
	public TableColumn OZNAKA;
	@FXML
	public TableColumn VISINA;

	private List<TextField> listaTxtVr = new ArrayList<>();
	private List<TextField> listaTxtV = new ArrayList<>();
	private List<TextField> listaTxtIzravnaj = new ArrayList<>();

	int redVR;
	int redV;

	// 2D mreza
	ObservableList<Pravac> data_pravci = FXCollections.observableArrayList();
	ObservableList<Ugao> data_uglovi = FXCollections.observableArrayList();
	ObservableList<Duzina> data_duzine = FXCollections.observableArrayList();
	private Pravac pravac;
	private Ugao ugao;
	private Duzina duzina;
	@FXML
	private TextField txt_od_p;
	@FXML
	private TextField txt_do_p;
	@FXML
	private TextField txt_stepen_p;
	@FXML
	private TextField txt_minut_p;
	@FXML
	private TextField txt_sekund_p;
	@FXML
	private TextField txt_tacnost_p;
	@FXML
	public TableColumn PRAVAC_OD;
	@FXML
	public TableColumn PRAVAC_DO;
	@FXML
	public TableColumn PRAVAC_STEPEN;
	@FXML
	public TableColumn PRAVAC_MINUT;
	@FXML
	public TableColumn PRAVAC_SEKUND;
	@FXML
	public TableColumn PRAVAC_TACNOST;
	@FXML
	private TableView<Pravac> tabela_p;

	@FXML
	private TextField txt_lijevo_u;
	@FXML
	private TextField txt_sredina_u;
	@FXML
	private TextField txt_desno_u;
	@FXML
	private TextField txt_tacnost_u;
	@FXML
	private TextField txt_stepen_u;
	@FXML
	private TextField txt_minut_u;
	@FXML
	private TextField txt_sekund_u;
	@FXML
	public TableColumn UGAO_OZNAKA;
	@FXML
	public TableColumn UGAO_STEPEN;
	@FXML
	public TableColumn UGAO_MINUT;
	@FXML
	public TableColumn UGAO_SEKUND;
	@FXML
	public TableColumn UGAO_TACNOST;
	@FXML
	private TableView<Ugao> tabela_u;

	@FXML
	private TextField txt_od_d;
	@FXML
	private TextField txt_do_d;
	@FXML
	private TextField txt_vrijednost_d;
	@FXML
	private TextField txt_mm_d;
	@FXML
	private TextField txt_ppm_d;
	@FXML
	public TableColumn DUZINA_OD;
	@FXML
	public TableColumn DUZINA_DO;
	@FXML
	public TableColumn DUZINA_VRIJEDNOST;
	@FXML
	public TableColumn DUZINA_TACNOST;
	@FXML
	private TableView<Duzina> tabela_d;

	public void initialize() {
		// Dodavanje fields u listu VR
		listaTxtVr.add(txt_od);
		listaTxtVr.add(txt_do);
		listaTxtVr.add(txt_visinskaRazlika);
		listaTxtVr.add(txt_duzinaStrane);
		// Dodavanje fields u listu V
		listaTxtV.add(txt_oznaka);
		listaTxtV.add(txt_visina);
		// Dodavanje fields u listu Izravnaj
		listaTxtIzravnaj.add(txt_s0);
		listaTxtIzravnaj.add(txt_nivoZnacajnosti);

		// Dodavanje listener-a za fields VR
		addTextFieldChangeListener(txt_od);
		addTextFieldChangeListener(txt_do);
		addTextFieldChangeListener(txt_visinskaRazlika);
		addTextFieldChangeListener(txt_duzinaStrane);
		// Dodavanje listener-a za fields V
		addTextFieldChangeListener(txt_oznaka);
		addTextFieldChangeListener(txt_visina);
		// Dodavanje listener-a za fields Izravnaj
		addTextFieldChangeListener(txt_s0);
		addTextFieldChangeListener(txt_nivoZnacajnosti);

		// Dodavanje tooltip-a
		toolTip();
		// Dodavanje dvoklika na tablicu
		klikTabelaVR();
		klikTabelaVisina();

	}

	private void addTextFieldChangeListener(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			// Provjera popunjenosti TextField-ova i primjena crvenog obruba
			if (newValue.isEmpty()) {
				textField.getStyleClass().add("red-outline");
			} else {
				textField.getStyleClass().remove("red-outline");

				// Provjeravanje je li unesen samo brojčani niz i tacka
				if (!newValue.matches("\\d*\\.?\\d*")) {
					textField.getStyleClass().add("red-outline");
				} else {
					textField.getStyleClass().remove("red-outline");
				}
			}
		});
	}

	// Postavljanje tooltip-a za dugme
	public void toolTip() {
		Tooltip tipUcitaj = new Tooltip("Učitana txt datoteka\n" + "mora biti formata:\n" + "OD,DO,VR,D,BS");
		ucitajButton.setTooltip(tipUcitaj);

		Tooltip tipVR = new Tooltip("Format je tipa #.###");
		txt_visinskaRazlika.setTooltip(tipVR);

	}

	public void klikTabelaVR() {
		tabela_vr.setRowFactory(tv -> {
			TableRow<VisinskaRazlika> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !row.isEmpty()) {
					VisinskaRazlika rowData = row.getItem();
					otvoriDijalogZaUredivanjeVR(rowData);
				}
				redVR = row.getIndex() + 1;
			});
			return row;
		});
	}

	public void klikTabelaVisina() {
		tabela_v.setRowFactory(tv -> {
			TableRow<Visina> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !row.isEmpty()) {
					Visina rowData = row.getItem();
					otvoriDijalogZaUredivanjeV(rowData);
				}
				redV = row.getIndex() + 1;
			});

			row.itemProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) {
					if (newVal.definiseDatum()) { // Prilagodite s atributom koji provjeravate
						row.getStyleClass().add("table-row-true");
					} else {
						row.getStyleClass().remove("table-row-true");
					}
				}
			});
			return row;
		});
	}

	public void ucitaj(ActionEvent event) {
		OD.setCellValueFactory(new PropertyValueFactory<>("Od"));
		DO.setCellValueFactory(new PropertyValueFactory<>("Do"));
		VISINSKA_RAZLIKA.setCellValueFactory(new PropertyValueFactory<>("visinskaRaz"));
		DUZINA_NIVELMANSKE_STRANE.setCellValueFactory(new PropertyValueFactory<>("duzinaStrane"));
		BROJ_STANICA.setCellValueFactory(new PropertyValueFactory<>("brojStanica"));
		File proba = new File("IG_Izravnanje/src/application/matA.txt");
		try {
			FileReader fr = new FileReader(proba);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				String[] odada = line.split(",");
				String OD = odada[0];
				String DO = odada[1];
				String VR = odada[2];
				String D = odada[3];
				VisinskaRazlika VS = new VisinskaRazlika(OD, DO, VR, D, "");
				data_vr.add(VS);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		tabela_vr.setItems(data_vr);
		tabela_vr.refresh();

		OZNAKA.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
		VISINA.setCellValueFactory(new PropertyValueFactory<>("visina"));

		data_v.add(visina = new Visina("1", "100", true));
		data_v.add(visina = new Visina("2", "", false));
		data_v.add(visina = new Visina("3", "", false));
		data_v.add(visina = new Visina("4", "", false));
		data_v.add(visina = new Visina("5", "", false));

		// radio_minimalanTrag.setSelected(true);
		txt_s0.setText("0.8");
		tabela_v.setItems(data_v);
		tabela_v.refresh();
	}

	public void popuniTabeluVr(ActionEvent event) {

		// Provjeravamo jesu li svi TextField-ovi popunjeni prije dodavanja u tabelu
		boolean allFieldsFilledVR = listaTxtVr.stream().allMatch(textField -> !textField.getText().isEmpty());
		boolean allNumbersValidVR = listaTxtVr.stream()
				.allMatch(textField -> textField.getText().matches("\\d*\\.?\\d*"));

		if (allFieldsFilledVR && allNumbersValidVR) {
			// Ako su svi TextField-ovi popunjeni i uneseni su validni brojevi, dodajemo
			// podatke u tabelu
			OD.setCellValueFactory(new PropertyValueFactory<>("Od"));
			DO.setCellValueFactory(new PropertyValueFactory<>("Do"));
			VISINSKA_RAZLIKA.setCellValueFactory(new PropertyValueFactory<>("visinskaRaz"));
			DUZINA_NIVELMANSKE_STRANE.setCellValueFactory(new PropertyValueFactory<>("duzinaStrane"));
			BROJ_STANICA.setCellValueFactory(new PropertyValueFactory<>("brojStanica"));
			visinskaRazlika = new VisinskaRazlika(txt_od.getText(), txt_do.getText(),
					txt_visinskaRazlika.getText(),
					txt_duzinaStrane.getText(), txt_brojStanica.getText());
			data_vr.add(visinskaRazlika);
			tabela_vr.setItems(data_vr);
			tabela_vr.refresh();

			// Očistimo TextField-ove nakon dodavanja u tabelu
			listaTxtVr.forEach(TextField::clear);
			listaTxtVr.forEach(textField -> textField.getStyleClass().remove("red-outline"));
			System.out.println("Dodano u tabelu!");
		} else {
			// Ako nisu svi TextField-ovi popunjeni ili nisu uneseni validni brojevi,
			// obojimo odgovarajući TextField u crveno
			System.out.println("Popunite sva polja s brojevima prije dodavanja u tabelu.");
			for (TextField textField : listaTxtVr) {
				if (textField.getText().isEmpty() || !textField.getText().matches("\\d*\\.?\\d*")) {
					textField.getStyleClass().add("red-outline");
				}
			}
		}

	}

	public void popuniTabeluV(ActionEvent event) {

		// Provjeravamo jesu li svi TextField-ovi popunjeni prije dodavanja u tabelu
		boolean allFieldsFilledV = listaTxtV.stream().allMatch(textField -> !textField.getText().isEmpty());
		boolean allNumbersValidV = listaTxtV.stream()
				.allMatch(textField -> textField.getText().matches("\\d*\\.?\\d*"));

		if (allFieldsFilledV && allNumbersValidV) {
			// Ako su svi TextField-ovi popunjeni i uneseni su validni brojevi, dodajemo
			// podatke u tabelu
			OZNAKA.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
			VISINA.setCellValueFactory(new PropertyValueFactory<>("visina"));
			if (datum1d.isSelected()) {
				visina = new Visina(txt_oznaka.getText(), txt_visina.getText(), true);
			}
			if (!datum1d.isSelected()) {
				visina = new Visina(txt_oznaka.getText(), txt_visina.getText(), false);
			}
			data_v.add(visina);
			tabela_v.setItems(data_v);
			tabela_v.refresh();

			// Očistimo TextField-ove nakon dodavanja u tabelu
			listaTxtV.forEach(TextField::clear);
			listaTxtV.forEach(textField -> textField.getStyleClass().remove("red-outline"));
			System.out.println("Dodano u tabelu!");
		} else {
			// Ako nisu svi TextField-ovi popunjeni ili nisu uneseni validni brojevi,
			// obojimo odgovarajući TextField u crveno
			System.out.println("Popunite sva polja s brojevima prije dodavanja u tabelu.");
			for (TextField textField : listaTxtV) {
				if (textField.getText().isEmpty() || !textField.getText().matches("\\d*\\.?\\d*")) {
					textField.getStyleClass().add("red-outline");
				}
			}
		}

	}

	public void izravnaj(ActionEvent e) {

		// Provjeravamo jesu li svi TextField-ovi popunjeni prije dodavanja u tabelu
		boolean allFieldsFilledIz = listaTxtIzravnaj.stream().allMatch(textField -> !textField.getText().isEmpty());
		boolean allNumbersValidIz = listaTxtIzravnaj.stream()
				.allMatch(textField -> textField.getText().matches("\\d*\\.?\\d*"));

		if (allFieldsFilledIz && allNumbersValidIz) {
			MinimalniTrag1D mt = new MinimalniTrag1D(data_v, data_vr, Double.parseDouble(txt_s0.getText()),
					Double.parseDouble(txt_nivoZnacajnosti.getText()));
			KlasicanNacin1D kn = new KlasicanNacin1D(data_v, data_vr, Double.parseDouble(txt_s0.getText()),
					Double.parseDouble(txt_nivoZnacajnosti.getText()));
			if (radio_klasicno.isSelected()) {
				kn.napraviIzvjestaj();
			} else if (radio_minimalanTrag.isSelected()) {
				mt.napraviIzvjestaj();
			} else {
				showAlert("GRESKA", "Morate izabrati jednu od metoda!",AlertType.ERROR);
			}

			listaTxtV.forEach(textField -> textField.getStyleClass().remove("red-outline"));
		} else {
			// Ako nisu svi TextField-ovi popunjeni ili nisu uneseni validni brojevi,
			// obojimo odgovarajući TextField u crveno
			for (TextField textField : listaTxtIzravnaj) {
				if (textField.getText().isEmpty() || !textField.getText().matches("\\d*\\.?\\d*")) {
					textField.getStyleClass().add("red-outline");
				}
			}
		}

	}

	public void otvoriDijalogZaUredivanjeVR(VisinskaRazlika odabranaVR) {
		Dialog<Boolean> dialog = new Dialog<>();
		dialog.setTitle("Uredi visinsku razliku: " + redVR);

		// Kreirajte polja za unos atributa
		TextField odField = new TextField();
		odField.setText(odabranaVR.getOd());
		TextField doField = new TextField();
		doField.setText(odabranaVR.getDo());
		TextField vrField = new TextField();
		vrField.setText(odabranaVR.getVisinskaRaz());
		TextField DField = new TextField();
		DField.setText(odabranaVR.getDuzinaStrane());
		TextField BSField = new TextField();
		BSField.setText(odabranaVR.getBrojStanica());

		// Kreirajte višeslojni raspored za elemente
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.addRow(0, new Label("Od:"), odField);
		gridPane.addRow(1, new Label("Do:"), doField);
		gridPane.addRow(2, new Label("Visinska Razlika:"), vrField);
		gridPane.addRow(3, new Label("Duzina Strane:"), DField);
		gridPane.addRow(4, new Label("Broj Stanica:"), BSField);

		// Dodajte raspored u dijalog
		VBox content = new VBox(gridPane);
		dialog.getDialogPane().setContent(content);

		// Dodajte gumb "Potvrdi" u dijalog
		ButtonType potvrdiButton = new ButtonType("Potvrdi", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(potvrdiButton, ButtonType.CANCEL);

		// Obrada potvrde gumba
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == potvrdiButton) {
				odabranaVR.setOd(odField.getText());
				odabranaVR.setDo(doField.getText());
				odabranaVR.setVisinskaRaz(vrField.getText());
				odabranaVR.setDuzinaStrane(DField.getText());
				odabranaVR.setBrojStanica(BSField.getText());

				return true;
			}
			return false;
		});

		// Prikaži dijalog i obradi rezultat
		dialog.showAndWait().ifPresent(result -> {
			if (result) {
				tabela_vr.refresh(); // Osvježi prikaz tablice
			}
		});
	}

	/**
	 * @param odabranaV
	 */
	public void otvoriDijalogZaUredivanjeV(Visina odabranaV) {
		Dialog<Boolean> dialog = new Dialog<>();
		dialog.setTitle("Uredi visinsku razliku: " + redV);

		// Kreirajte polja za unos atributa
		TextField OzField = new TextField();
		OzField.setText(odabranaV.getOznaka());
		TextField VField = new TextField();
		VField.setText(odabranaV.getVisina());
		RadioButton radioButtonDa = new RadioButton("Da");
		RadioButton radioButtonNe = new RadioButton("Ne");

		if (odabranaV.definiseDatum()) {
			radioButtonDa.setSelected(true);
			radioButtonNe.setSelected(false);
		} else {
			radioButtonDa.setSelected(false);
			radioButtonNe.setSelected(true);
		}

		// Kreirajte višeslojni raspored za elemente
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.addRow(0, new Label("Oznaka:"), OzField);
		gridPane.addRow(1, new Label("Visina:"), VField);
		gridPane.addRow(2, new Label("Definise Datum:"));
		gridPane.addRow(3, radioButtonDa, radioButtonNe);

		ToggleGroup radioGroup = new ToggleGroup();
		radioButtonDa.setToggleGroup(radioGroup);
		radioButtonNe.setToggleGroup(radioGroup);

		// Dodajte raspored u dijalog
		VBox content = new VBox(gridPane);
		dialog.getDialogPane().setContent(content);

		// Dodajte gumb "Potvrdi" u dijalog
		ButtonType potvrdiButton = new ButtonType("Potvrdi", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(potvrdiButton, ButtonType.CANCEL);

		// Obrada potvrde gumba
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == potvrdiButton) {
				odabranaV.setOznaka(OzField.getText());
				odabranaV.setVisina(VField.getText());
				Boolean datum;
				if (radioButtonDa.isSelected()) {
					datum = true;
				} else {
					datum = false;
				}
				odabranaV.setDefinise_datum(datum);
				return true;
			}
			return false;
		});

		// Prikaži dijalog i obradi rezultat
		dialog.showAndWait().ifPresent(result -> {
			if (result) {
				tabela_v.refresh(); // Osvježi prikaz tablice
			}
		});
	}

	public void popuniTabeluP(ActionEvent event) {
		PRAVAC_OD.setCellValueFactory(new PropertyValueFactory<>("Od"));
		PRAVAC_DO.setCellValueFactory(new PropertyValueFactory<>("Do"));
		PRAVAC_STEPEN.setCellValueFactory(new PropertyValueFactory<>("stepen"));
		PRAVAC_MINUT.setCellValueFactory(new PropertyValueFactory<>("minut"));
		PRAVAC_SEKUND.setCellValueFactory(new PropertyValueFactory<>("sekund"));
		PRAVAC_TACNOST.setCellValueFactory(new PropertyValueFactory<>("tacnost"));
		pravac = new Pravac(txt_od_p.getText(), txt_do_p.getText(), txt_stepen_p.getText(),
				txt_minut_p.getText(), txt_sekund_p.getText(), txt_tacnost_p.getText());
		data_pravci.add(pravac);
		tabela_p.setItems(data_pravci);
		tabela_p.refresh();
	}

	public void popuniTabeluU(ActionEvent event) {
		UGAO_OZNAKA.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
		UGAO_STEPEN.setCellValueFactory(new PropertyValueFactory<>("stepen"));
		UGAO_MINUT.setCellValueFactory(new PropertyValueFactory<>("minut"));
		UGAO_SEKUND.setCellValueFactory(new PropertyValueFactory<>("sekund"));
		UGAO_TACNOST.setCellValueFactory(new PropertyValueFactory<>("tacnost"));
		String oznaka = txt_lijevo_u.getText() + "-" + txt_sredina_u.getText() + "-" + txt_desno_u.getText();
		ugao = new Ugao(oznaka, txt_stepen_u.getText(), txt_minut_u.getText(), txt_sekund_u.getText(),
				txt_tacnost_u.getText());
		data_uglovi.add(ugao);
		tabela_u.setItems(data_uglovi);
		tabela_u.refresh();
	}

	public void pupuniTabeluD(ActionEvent event) {
		DUZINA_OD.setCellValueFactory(new PropertyValueFactory<>("Od"));
		DUZINA_DO.setCellValueFactory(new PropertyValueFactory<>("Do"));
		DUZINA_VRIJEDNOST.setCellValueFactory(new PropertyValueFactory<>("vrijednost"));
		DUZINA_TACNOST.setCellValueFactory(new PropertyValueFactory<>("tacnost"));
		double mm = Double.parseDouble(txt_mm_d.getText());
		double ppm = Double.parseDouble(txt_ppm_d.getText());
		double duz = Double.parseDouble(txt_vrijednost_d.getText());
		double tac = mm + (ppm * (duz / 1000));
		String tacnost = "" + tac;
		duzina = new Duzina(txt_od_d.getText(), txt_do_d.getText(), txt_vrijednost_d.getText(), tacnost);
		data_duzine.add(duzina);
		tabela_d.setItems(data_duzine);
		tabela_d.refresh();
	}

	// Metoda za prikazivanje Alert dijaloga
	private void showAlert(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}