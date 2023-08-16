package application;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import izravnanje1D.KlasicanNacin1D;
import izravnanje1D.MinimalniTrag1D;
import izravnanje1D.Visina;
import izravnanje1D.VisinskaRazlika;
import izravnanje2D.Duzina;
import izravnanje2D.KlasicanNacin2D;
import izravnanje2D.Koordinata;
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
	private List<TextField> listaTxtPravac = new ArrayList<>();
	private List<TextField> listaTxtUgao = new ArrayList<>();
	private List<TextField> listaTxtDuzina = new ArrayList<>();

	int redVR;
	int redV;
	int redP;
	int redU;
	int redD;

	// 2D mreza
	ObservableList<Pravac> data_pravci = FXCollections.observableArrayList();
	ObservableList<Ugao> data_uglovi = FXCollections.observableArrayList();
	ObservableList<Duzina> data_duzine = FXCollections.observableArrayList();
	ObservableList<Koordinata> data_koordinate = FXCollections.observableArrayList();
	private Pravac pravac;
	private Ugao ugao;
	private Duzina duzina;
	private Koordinata koordinata;
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
	private RadioButton radio_poznata_p;
	@FXML
	private RadioButton radio_nepoznata_p;

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

	@FXML
	private TextField txt_oznaka_k;
	@FXML
	private TextField txt_y_k;
	@FXML
	private TextField txt_x_k;
	@FXML
	private RadioButton datum2d;
	@FXML
	public TableColumn KOORDINATA_OZNAKA;
	@FXML
	public TableColumn KOORDINATA_Y;
	@FXML
	public TableColumn KOORDINATA_X;
	@FXML
	private TableView<Koordinata> tabela_k;

	@FXML
	private TextField txt_s0_2d;
	@FXML
	private TextField txt_nivoZnacajnosti_2d;

	public String style = " -fx-border-color: red;\n" + //
			"    -fx-border-width: 1px;\n" + //
			"    -fx-border-radius:\n" +
			"     5px;";

	public void initialize() {
		OD.setCellValueFactory(new PropertyValueFactory<>("Od"));
		DO.setCellValueFactory(new PropertyValueFactory<>("Do"));
		VISINSKA_RAZLIKA.setCellValueFactory(new PropertyValueFactory<>("visinskaRaz"));
		DUZINA_NIVELMANSKE_STRANE.setCellValueFactory(new PropertyValueFactory<>("duzinaStrane"));
		BROJ_STANICA.setCellValueFactory(new PropertyValueFactory<>("brojStanica"));
		OZNAKA.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
		VISINA.setCellValueFactory(new PropertyValueFactory<>("visina"));
		UGAO_OZNAKA.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
		UGAO_STEPEN.setCellValueFactory(new PropertyValueFactory<>("stepen"));
		UGAO_MINUT.setCellValueFactory(new PropertyValueFactory<>("minut"));
		UGAO_SEKUND.setCellValueFactory(new PropertyValueFactory<>("sekund"));
		UGAO_TACNOST.setCellValueFactory(new PropertyValueFactory<>("tacnost"));
		DUZINA_OD.setCellValueFactory(new PropertyValueFactory<>("Od"));
		DUZINA_DO.setCellValueFactory(new PropertyValueFactory<>("Do"));
		DUZINA_VRIJEDNOST.setCellValueFactory(new PropertyValueFactory<>("vrijednost"));
		DUZINA_TACNOST.setCellValueFactory(new PropertyValueFactory<>("tacnost"));
		KOORDINATA_OZNAKA.setCellValueFactory(new PropertyValueFactory<>("oznaka"));
		KOORDINATA_Y.setCellValueFactory(new PropertyValueFactory<>("y"));
		KOORDINATA_X.setCellValueFactory(new PropertyValueFactory<>("x"));
		PRAVAC_OD.setCellValueFactory(new PropertyValueFactory<>("Od"));
		PRAVAC_DO.setCellValueFactory(new PropertyValueFactory<>("Do"));
		PRAVAC_STEPEN.setCellValueFactory(new PropertyValueFactory<>("stepen"));
		PRAVAC_MINUT.setCellValueFactory(new PropertyValueFactory<>("minut"));
		PRAVAC_SEKUND.setCellValueFactory(new PropertyValueFactory<>("sekund"));
		PRAVAC_TACNOST.setCellValueFactory(new PropertyValueFactory<>("tacnost"));

		// 1D mreza
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
		addTextFieldChangeListener(txt_od, "slova", 0);
		addTextFieldChangeListener(txt_do, "slova", 0);
		addTextFieldChangeListener(txt_brojStanica, "cijeliBr", Integer.MAX_VALUE);
		addTextFieldChangeListener(txt_visinskaRazlika, "decimalniBr", 0);
		addTextFieldChangeListener(txt_duzinaStrane, "decimalniBr", 0);
		// Dodavanje listener-a za fields V
		addTextFieldChangeListener(txt_oznaka, "slova", 0);
		addTextFieldChangeListener(txt_visina, "decimalniBr", 0);
		// Dodavanje listener-a za fields Izravnaj
		addTextFieldChangeListener(txt_s0, "decimalniBr", 0);
		addTextFieldChangeListener(txt_nivoZnacajnosti, "decimalniBr", 0);

		// 2D mreza
		// Dodavanje fields u listu Pravac
		listaTxtPravac.add(txt_od_p);
		listaTxtPravac.add(txt_do_p);
		listaTxtPravac.add(txt_tacnost_p);
		listaTxtPravac.add(txt_stepen_p);
		listaTxtPravac.add(txt_minut_p);
		listaTxtPravac.add(txt_sekund_p);
		// dodavanje listener-a za fields Pravac
		addTextFieldChangeListener(txt_od_p, "slova", 0);
		addTextFieldChangeListener(txt_do_p, "slova", 0);
		addTextFieldChangeListener(txt_tacnost_p, "decimalniBr", Integer.MAX_VALUE);
		addTextFieldChangeListener(txt_stepen_p, "cijeliBr", 360);
		addTextFieldChangeListener(txt_minut_p, "cijeliBr", 60);
		addTextFieldChangeListener(txt_sekund_p, "decimalniBr", 60);
		addTextFieldChangeListener(txt_od, "slova", 0);

		// Dodavanje fields u listu Ugao
		listaTxtUgao.add(txt_lijevo_u);
		listaTxtUgao.add(txt_sredina_u);
		listaTxtUgao.add(txt_desno_u);
		listaTxtUgao.add(txt_tacnost_u);
		listaTxtUgao.add(txt_stepen_u);
		listaTxtUgao.add(txt_minut_u);
		listaTxtUgao.add(txt_sekund_u);
		// Dodavanje listener-a za fields Ugao
		addTextFieldChangeListener(txt_lijevo_u, "slova", 0);
		addTextFieldChangeListener(txt_sredina_u, "slova", 0);
		addTextFieldChangeListener(txt_desno_u, "slova", 0);
		addTextFieldChangeListener(txt_tacnost_u, "decimalniBr", Integer.MAX_VALUE);
		addTextFieldChangeListener(txt_stepen_u, "cijeliBr", 360);
		addTextFieldChangeListener(txt_minut_u, "cijeliBr", 60);
		addTextFieldChangeListener(txt_sekund_u, "decimalniBr", 60);
		Ugao u = new Ugao("aaa-bbb-ccc", "111", "22", "33", "123.456");
		data_uglovi.add(u);
		tabela_u.setItems(data_uglovi);
		tabela_u.refresh();

		// Dodavanje fields u listu Duzina
		listaTxtDuzina.add(txt_od_d);
		listaTxtDuzina.add(txt_do_d);
		listaTxtDuzina.add(txt_vrijednost_d);
		listaTxtDuzina.add(txt_mm_d);
		listaTxtDuzina.add(txt_ppm_d);
		// Dodavanje listenera-a za fields Duzina
		addTextFieldChangeListener(txt_od_d, "slova", 0);
		addTextFieldChangeListener(txt_do_d, "slova", 0);
		addTextFieldChangeListener(txt_vrijednost_d, "decimalniBr", Integer.MAX_VALUE);
		addTextFieldChangeListener(txt_mm_d, "decimalniBr", Integer.MAX_VALUE);
		addTextFieldChangeListener(txt_ppm_d, "decimalniBr", Integer.MAX_VALUE);

		// Dodavanje tooltip-a
		toolTip();
		// Dodavanje dvoklika na tablicu
		klikTabelaVR();
		klikTabelaV();
		klikTabelaPravac();
		klikTabelaUgao();

	}

	private void addTextFieldChangeListener(TextField textField, String type, int maxValue) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			// Provjera popunjenosti TextField-ova i primjena crvenog ruba
			if (newValue.isEmpty()) {
				textField.getStyleClass().add("red-outline");
			} else {
				textField.getStyleClass().remove("red-outline");

				// Provjeravanje tipa
				switch (type) {
					case "decimalniBr":
						// Provjeravanje je li unesen brojčani niz i tacka
						if (!newValue.matches("\\d*\\.?\\d*")) {
							textField.getStyleClass().add("red-outline");
						} else {
							// Provjera da li je broj manji od zadatog maksimuma
							double numericValue = Double.parseDouble(newValue);
							if (numericValue < 0 || numericValue >= maxValue) {
								textField.getStyleClass().add("red-outline");
							}
						}
						break;
					case "slova":
						// Provjeravanje je li unesen brojčani niz, tacka i slova abecede
						if (!newValue.matches("[a-zA-Z0-9\\.]*")) {
							textField.getStyleClass().add("red-outline");
						}
						break;
					case "cijeliBr":
						// Provjeravanje je li unesen samo brojčani niz
						if (!newValue.matches("\\d*")) {
							textField.getStyleClass().add("red-outline");
						} else {
							// Provjera ograničenja (ne veće od maxValue i ne negativno)
							int numericValue = Integer.parseInt(newValue);
							if (numericValue < 0 || numericValue >= maxValue) {
								textField.getStyleClass().add("red-outline");
							}
						}
						break;
				}
			}
		});
	}

	private TextField createTextField(String initialValue) {
		TextField textField = new TextField();
		textField.setText(initialValue);
		return textField;
	}

	private TextField createAlphanumericTextField(String initialValue) {
		TextField textField = createTextField(initialValue);
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z0-9\\.\\-]*")) {
				textField.setText(newValue.replaceAll("[^a-zA-Z0-9\\.\\-]", ""));
			}
		});
		return textField;
	}

	private TextField createIntegerTextField(String initialValue) {
		TextField textField = createTextField(initialValue);
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				textField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		return textField;
	}

	private TextField createDecimalTextField(String initialValue) {
		TextField textField = createTextField(initialValue);
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*\\.?\\d*")) {
				textField.setText(newValue.replaceAll("[^\\d\\.]", ""));
			}
		});
		return textField;
	}

	private void initializeRadioButtons(RadioButton radioButtonP, RadioButton radioButtonN, Pravac odabraniPravac) {
		if (odabraniPravac.getSelektovanoPoznata()) {
			radioButtonP.setSelected(true);
			radioButtonN.setSelected(false);
		} else if (odabraniPravac.getSelektovanoNepoznata()) {
			radioButtonP.setSelected(false);
			radioButtonN.setSelected(true);
		}
	}

	private void initializeRadioButtons(RadioButton radioButtonDa, RadioButton radioButtonNe, Visina odabranaVisina) {
		if (odabranaVisina.definiseDatum()) {
			radioButtonDa.setSelected(true);
			radioButtonNe.setSelected(false);
		} else {
			radioButtonDa.setSelected(false);
			radioButtonNe.setSelected(true);
		}

	}

	private boolean validateTextField(TextField textField, String regexPattern) {
		if (!textField.getText().matches(regexPattern)) {
			textField.setStyle(style);
			return false;
		} else {
			textField.setStyle(null);
			return true;
		}

	}

	private boolean validateIntegerTextField(TextField textField, int minValue, int maxValue) {
		try {
			int value = Integer.parseInt(textField.getText());
			if (value < minValue || value >= maxValue) {
				textField.setStyle(style);
				return false;
			} else {
				textField.setStyle(null);
				return true;
			}
		} catch (NumberFormatException e) {
			textField.setStyle(style);
			return false;
		}
	}

	private boolean validateDecimalTextField(TextField textField, double minValue, double maxValue) {
		try {
			double value = Double.parseDouble(textField.getText());
			if (value < minValue || value >= maxValue) {
				textField.setStyle(style);
				return false;
			} else {
				textField.setStyle(null);
				return true;
			}
		} catch (NumberFormatException e) {
			textField.setStyle(style);
			return false;
		}
	}

	// Postavljanje tooltip-a za dugme
	public void toolTip() {
		Tooltip tipUcitaj = new Tooltip(
				"Učitana .txt datoteka\n" + "za tabelu Visine razlike\n" + "mora biti formata:\n" + "OD,DO,VR,D,BS\n"
						+ "A za tabelu Visine\n" + "format je: OZ,V,DATUM(da,ne)\n" + "Ako vam neki podatak\n"
						+ "ne treba, zamijenite ga sa -");
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

	public void klikTabelaV() {
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
					if (newVal.definiseDatum()) {
						row.getStyleClass().add("table-row-true");
					} else {
						row.getStyleClass().remove("table-row-true");
					}
				}
			});
			return row;
		});

	}

	public void klikTabelaPravac() {
		tabela_p.setRowFactory(tv -> {
			TableRow<Pravac> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !row.isEmpty()) {
					Pravac rowData = row.getItem();
					otvoriDijalogZaUredivanjePravca(rowData);
				}
				redP = row.getIndex() + 1;
			});
			row.itemProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) {
					if (newVal.selektovano_poznata) {
						row.getStyleClass().add("table-row-true");
					} else {
						row.getStyleClass().remove("table-row-true");
					}
				}
			});
			return row;
		});
	}

	public void klikTabelaUgao() {
		tabela_u.setRowFactory(tv -> {
			TableRow<Ugao> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !row.isEmpty()) {
					Ugao rowData = row.getItem();
					otvoriDijalogZaUredivanjeUgao(rowData);
				}
				redU = row.getIndex() + 1;
			});
			return row;
		});
	}

	public void klikTabelaDuzina() {
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

	public void ucitaj(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files",
				"*.txt"));

		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			System.out.println("Izabrali ste datoteku: " +
					selectedFile.getAbsolutePath());
		} else {
			System.out.println("Niste izabrali datoteku.");
		}

		try (
				BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");

				// Proveravamo da li je red u formatu "Od,Do,Visina,Duzina,Brojstanica"
				if (parts.length == 5) {

					try {
						// Iteriramo kroz sve djelove reda (atribute)
						for (int i = 0; i < parts.length; i++) {
							// Proveravamo da li atribut sadrži znak "-"
							if (parts[i].equals("-")) {
								// Ako sadrži, zamenjujemo ga sa praznim stringom
								parts[i] = "";
							}
						}
						String OD = parts[0];
						String DO = parts[1];
						String VR = parts[2];
						String D = parts[3];
						String bs = parts[4];
						VisinskaRazlika VS = new VisinskaRazlika(OD, DO, VR, D, bs);
						data_vr.add(VS);

					} catch (Exception e) {
						// TODO: handle exception
					}
					tabela_vr.setItems(data_vr);
					tabela_vr.refresh();

					// Proveravamo da li je red u formatu "Oznaka, Visina, Datum"
				} else if (parts.length == 3) {

					try {
						// Iteriramo kroz sve djelove reda (atribute)
						for (int i = 0; i < parts.length; i++) {
							// Proveravamo da li atribut sadrži znak "-"
							if (parts[i].equals("-")) {
								// Ako sadrži, zamenjujemo ga sa praznim stringom
								parts[i] = "";
							}
						}
						String Oznaka = parts[0];
						String Visine = parts[1];
						String datum = parts[2];
						if (datum.equals("da")) {
							Visina V = new Visina(Oznaka, Visine, true);
							data_v.add(V);
						} else {
							Visina V = new Visina(Oznaka, Visine, false);
							data_v.add(V);
						}

					} catch (Exception e) {
						// TODO: handle exception
					}

					// radio_minimalanTrag.setSelected(true);

					tabela_v.setItems(data_v);
					tabela_v.refresh();

				}
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

	public void popuniTabeluVr(ActionEvent event) {
		boolean allFieldsFilledVR = listaTxtVr.stream().allMatch(textField -> !textField.getText().isEmpty());

		if (allFieldsFilledVR) {
			// Ako su svi TextField-ovi popunjeni, nastavite sa dodavanjem u tabelu

			// Provjera validnosti za određenog polja
			boolean validNumbers = true;
			for (int i = 0; i < listaTxtVr.size(); i++) {
				TextField textField = listaTxtVr.get(i);
				String text = textField.getText();

				if (i == 0 || i == 1) { // Polja 1 i 2 su polja u kojima dozvoljavamo slova
					if (!text.matches("[a-zA-Z0-9\\.]*")) {
						textField.getStyleClass().add("red-outline");
						validNumbers = false;
					} else {
						textField.getStyleClass().remove("red-outline");
					}
				} else { // Ostala polja su polja u kojima treba unijeti brojeve ili tačku
					if (!text.matches("\\d*\\.?\\d*")) {
						textField.getStyleClass().add("red-outline");
						validNumbers = false;
					} else {
						textField.getStyleClass().remove("red-outline");
					}
				}
			}

			if (validNumbers) {
				// Ako su uneseni validni brojevi i slova, dodajemo podatke u tabelu
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
				// Ako nisu uneseni validni brojevi i slova, obavjestavamo korisnika
				System.out.println("Popunite sva polja sa validnim brojevima i slovima prije dodavanja u tabelu.");
			}
		} else {
			// Ako nisu svi TextField-ovi popunjeni, obojite odgovarajuće TextField-ove u
			// crveno
			System.out.println("Popunite sva polja prije dodavanja u tabelu.");
			listaTxtVr.forEach(textField -> {
				if (textField.getText().isEmpty()) {
					textField.getStyleClass().add("red-outline");
				}
			});
		}
	}

	public void popuniTabeluV(ActionEvent event) {

		// Proveravamo da li su svi TextField-ovi popunjeni pre dodavanja u tabelu
		boolean allFieldsFilledV = listaTxtV.stream().allMatch(textField -> !textField.getText().isEmpty());
		boolean allFieldsValidV = listaTxtV.stream()
				.allMatch(textField -> textField.getText().matches("\\d*\\.?\\d*") || textField == txt_oznaka);

		if (allFieldsFilledV && allFieldsValidV) {
			// Ako su svi TextField-ovi popunjeni i uneseni su validni brojevi, dodajemo
			// podatke u tabelu

			if (datum1d.isSelected()) {
				visina = new Visina(txt_oznaka.getText(), txt_visina.getText(), true);
			}
			if (!datum1d.isSelected()) {
				visina = new Visina(txt_oznaka.getText(), txt_visina.getText(), false);
			}
			data_v.add(visina);
			tabela_v.setItems(data_v);
			tabela_v.refresh();

			// Čistimo TextField-ove nakon dodavanja u tabelu
			listaTxtV.forEach(TextField::clear);
			datum1d.setSelected(false);
			listaTxtV.forEach(textField -> textField.getStyleClass().remove("red-outline"));
			System.out.println("Dodato u tabelu!");
		} else {
			// Ako nisu sva TextField polja popunjena ili nisu uneseni validni brojevi,
			// označavamo odgovarajući TextField crvenom bojom
			System.out.println("Popunite sva polja brojevima pre dodavanja u tabelu.");
			for (TextField textField : listaTxtV) {
				if (textField.getText().isEmpty()
						|| (!textField.getText().matches("\\d*\\.?\\d*") && textField != txt_oznaka)) {
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
				showAlert("GRESKA", "Morate izabrati jednu od metoda!", AlertType.ERROR);
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

	public void otvoriDijalogZaUredivanjeV(Visina odabranaV) {
		Dialog<Boolean> dialog = new Dialog<>();
		dialog.setTitle("Uredi visinsku razliku: " + redV);

		// Kreiramo polja za unos atributa
		TextField OzField = createAlphanumericTextField(odabranaV.getOznaka());
		TextField VField = createDecimalTextField(odabranaV.getVisina());
		RadioButton radioButtonDa = new RadioButton("Da");
		RadioButton radioButtonNe = new RadioButton("Ne");
		initializeRadioButtons(radioButtonDa, radioButtonNe, odabranaV);

		// Kreiramo višeslojni raspored za elemente
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

		// Dodajemo raspored u dijalog
		VBox content = new VBox(gridPane);
		dialog.getDialogPane().setContent(content);

		// Dodajemo gumb "Potvrdi" u dijalog
		ButtonType potvrdiButton = new ButtonType("Potvrdi", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(potvrdiButton, ButtonType.CANCEL);

		// Obrada potvrde dugmeta
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == potvrdiButton) {
				boolean areFieldsValid = true;

				// Validacija polja
				areFieldsValid &= validateTextField(OzField, "[a-zA-Z0-9\\.]*");
				areFieldsValid &= validateDecimalTextField(VField, 0, Double.MAX_VALUE);

				if (!areFieldsValid) {
					showAlert("Greška", "Unesene vrijednosti prelaze ograničenja.",
							AlertType.ERROR);
					return false; // Ne zatvaraj dijalog ako postoje greške
				} else {
					odabranaV.setOznaka(OzField.getText());
					odabranaV.setVisina(VField.getText());
					odabranaV.setDefinise_datum(radioButtonDa.isSelected());
					return true;
				}
			}
			return false;
		});

		// Prikaži dijalog i obradi rezultat
		dialog.showAndWait().ifPresent(result -> {
			if (result) {
				tabela_v.refresh(); // Osvježi prikaz tabele
			}
		});
	}

	public void popuniTabeluP(ActionEvent event) {
		if (radio_poznata_p.isSelected() || radio_nepoznata_p.isSelected()) {

			boolean allFieldsFilledPravac = listaTxtPravac.stream()
					.allMatch(textField -> !textField.getText().isEmpty());

			if (allFieldsFilledPravac) {
				// Ako su svi TextField-ovi popunjeni, nastavite sa dodavanjem u tabelu

				// Provjera validnosti za određenog polja
				boolean validData = true;
				for (int i = 0; i < listaTxtPravac.size(); i++) {
					TextField textField = listaTxtPravac.get(i);
					String text = textField.getText();

					if (i == 0 || i == 1) { // Polja 0 i 1 dozvoljavaju slova
						if (!text.matches("[a-zA-Z0-9\\.]*")) {
							textField.getStyleClass().add("red-outline");
							validData = false;
						} else {
							textField.getStyleClass().remove("red-outline");
						}
					} else if (i == 2 || i == 5) { // Polja 2 i 5 dozvoljavaju decimalne brojeve
						if (!text.matches("\\d*\\.?\\d*")) {
							textField.getStyleClass().add("red-outline");
							validData = false;
						} else {
							textField.getStyleClass().remove("red-outline");
							double numericValue = Double.parseDouble(text);
							if (i == 5 && (numericValue >= 60 || numericValue < 0)) {
								textField.getStyleClass().add("red-outline");
								validData = false;
							}
						}

					} else if (i == 3) { // Polje 3 dozvoljava cijele brojeve manje od 360
						if (!text.matches("\\d*")) {
							textField.getStyleClass().add("red-outline");
							validData = false;
						} else {
							textField.getStyleClass().remove("red-outline");
							int numericValue = Integer.parseInt(text);
							if (numericValue >= 360) {
								textField.getStyleClass().add("red-outline");
								validData = false;
							}
						}
					} else if (i == 4) { // Polje 4 dozvoljava cijele brojeve manje od 60
						if (!text.matches("\\d*")) {
							textField.getStyleClass().add("red-outline");
							validData = false;
						} else {
							textField.getStyleClass().remove("red-outline");
							int numericValue = Integer.parseInt(text);
							if (numericValue >= 60) {
								textField.getStyleClass().add("red-outline");
								validData = false;
							}
						}
					}
				}

				if (validData) {
					// Ako su uneseni validni brojevi i slova, dodajemo podatke u tabelu
					int stepen = Integer.parseInt(txt_stepen_p.getText());
					int minut = Integer.parseInt(txt_minut_p.getText());
					double sekund = Double.parseDouble(txt_sekund_p.getText());

					if (stepen < 360 && minut < 60 && sekund < 60) {
						pravac = new Pravac(txt_od_p.getText(), txt_do_p.getText(),
								txt_stepen_p.getText(),
								txt_minut_p.getText(), txt_sekund_p.getText(),
								txt_tacnost_p.getText(), radio_poznata_p.isSelected(),
								radio_nepoznata_p.isSelected());
						data_pravci.add(pravac);
						tabela_p.setItems(data_pravci);
						tabela_p.refresh();

						// Očistimo TextField-ove nakon dodavanja u tabelu
						listaTxtPravac.forEach(TextField::clear);
						listaTxtPravac.forEach(textField -> textField.getStyleClass().remove("red-outline"));
						System.out.println("Dodano u tabelu!");
					} else {
						System.out.println("Vrijednosti stepena, minuta ili sekundi nisu u dozvoljenom opsegu.");
					}

					// Očistimo TextField-ove nakon dodavanja u tabelu
					listaTxtPravac.forEach(TextField::clear);
					radio_poznata_p.setSelected(false);
					radio_nepoznata_p.setSelected(false);
					listaTxtPravac.forEach(textField -> textField.getStyleClass().remove("red-outline"));
					System.out.println("Dodano u tabelu!");
				} else {
					// Ako nisu uneseni validni brojevi i slova, obavestavamo korisnika
					System.out.println("Popunite sva polja sa validnim brojevima i slovima prije dodavanja u tabelu.");
				}
			} else {
				// Ako nisu svi TextField-ovi popunjeni, obojite odgovarajuće TextField-ove u
				// crveno
				System.out.println("Popunite sva polja prije dodavanja u tabelu.");
				listaTxtPravac.forEach(textField -> {
					if (textField.getText().isEmpty()) {
						textField.getStyleClass().add("red-outline");
					}
				});
			}
		} else {
			showAlert("GRESKA", "Morate izabrati da li je tacka poznata ili ne!", AlertType.ERROR);
		}
	}

	public void otvoriDijalogZaUredivanjePravca(Pravac odabraniPravac) {
		Dialog<Boolean> dialog = new Dialog<>();
		dialog.setTitle("Uredi pravac: " + redP);

		TextField OdField = createAlphanumericTextField(odabraniPravac.getOd());
		TextField DoField = createAlphanumericTextField(odabraniPravac.getDo());
		TextField StepenField = createIntegerTextField(odabraniPravac.getStepen());
		TextField MinutField = createIntegerTextField(odabraniPravac.getMinut());
		TextField SekundField = createDecimalTextField(odabraniPravac.getSekund());
		TextField TacnostField = createDecimalTextField(odabraniPravac.getTacnost());

		RadioButton radioButtonP = new RadioButton("Poznata");
		RadioButton radioButtonN = new RadioButton("Nepoznata");
		initializeRadioButtons(radioButtonP, radioButtonN, odabraniPravac);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.addRow(0, new Label("Od:"), OdField);
		gridPane.addRow(1, new Label("Do:"), DoField);
		gridPane.addRow(2, new Label("Tacnost:"), TacnostField);
		Label ugaoLabel = new Label("Ugao:");
		HBox ugaoHBox = new HBox(StepenField, new Label("\u00B0 "), MinutField, new Label("' "), SekundField,
				new Label("\""));
		gridPane.addRow(3, ugaoLabel, ugaoHBox);
		gridPane.addRow(4, new Label("Da li je poznata tacka:"));
		gridPane.addRow(5, radioButtonP, radioButtonN);
		ToggleGroup radioGroup = new ToggleGroup();
		radioButtonP.setToggleGroup(radioGroup);
		radioButtonN.setToggleGroup(radioGroup);

		VBox content = new VBox(gridPane);
		dialog.getDialogPane().setContent(content);

		ButtonType potvrdiButton = new ButtonType("Potvrdi", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(potvrdiButton, ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == potvrdiButton) {
				boolean areFieldsValid = true;

				areFieldsValid &= validateTextField(OdField, "[a-zA-Z0-9\\.]*");
				areFieldsValid &= validateTextField(DoField, "[a-zA-Z0-9\\.]*");
				areFieldsValid &= validateTextField(TacnostField, "\\d*\\.?\\d*");
				areFieldsValid &= validateIntegerTextField(StepenField, 0, 360);
				areFieldsValid &= validateIntegerTextField(MinutField, 0, 60);
				areFieldsValid &= validateDecimalTextField(SekundField, 0, 60);

				if (!areFieldsValid) {
					showAlert("Greška", "Unesene vrijednosti prelaze ograničenja ili nisu validno popunjena!",
							AlertType.ERROR);
					return false;
				} else {
					// Setovanje atributa u odabraniPravac
					odabraniPravac.setOd(OdField.getText());
					odabraniPravac.setDo(DoField.getText());
					odabraniPravac.setTacnost(TacnostField.getText());
					odabraniPravac.setStepen(StepenField.getText());
					odabraniPravac.setMinut(MinutField.getText());
					odabraniPravac.setSekund(SekundField.getText());

					if (radioButtonP.isSelected()) {
						odabraniPravac.setPoznata(true);
						odabraniPravac.setNePoznata(false);
					} else {
						odabraniPravac.setPoznata(false);
						odabraniPravac.setNePoznata(true);
					}
					return true;
				}
			}
			return true;
		});

		Optional<Boolean> result = dialog.showAndWait();
		while (result.isPresent() && !result.get()) {
			result = dialog.showAndWait();
		}

		tabela_p.refresh();
	}

	public void popuniTabeluU(ActionEvent event) {

		boolean allFieldsFilledUgao = listaTxtUgao.stream()
				.allMatch(textField -> !textField.getText().isEmpty());

		if (allFieldsFilledUgao) {
			// Ako su svi TextField-ovi popunjeni, nastavite sa dodavanjem u tabelu

			// Provjera validnosti za određenog polja
			boolean validData = true;
			for (int i = 0; i < listaTxtUgao.size(); i++) {
				TextField textField = listaTxtUgao.get(i);
				String text = textField.getText();

				if (i == 0 || i == 1 || i == 2) { // Polja 1, 2 i 3 dozvoljavaju slova, brojeve i tacku
					if (!text.matches("[a-zA-Z0-9\\.]*")) {
						textField.getStyleClass().add("red-outline");
						validData = false;
					} else {
						textField.getStyleClass().remove("red-outline");
					}
				} else if (i == 6 || i == 3) { // Polje 6 i 3 dozvoljavaju decimalne brojeve i tacku
					if (!text.matches("\\d*\\.?\\d*")) {
						textField.getStyleClass().add("red-outline");
						validData = false;
					} else {
						textField.getStyleClass().remove("red-outline");
						double numericValue = Double.parseDouble(text);
						if (i == 6 && (numericValue >= 60 || numericValue < 0)) {
							textField.getStyleClass().add("red-outline");
							validData = false;
						}

					}
				} else if (i == 4 || i == 5) { // Polja 4 i 5 dozvoljavaju cijele brojeve
					if (!text.matches("\\d*")) {
						textField.getStyleClass().add("red-outline");
						validData = false;
					} else {
						textField.getStyleClass().remove("red-outline");
						int numericValue = Integer.parseInt(text);
						if (i == 4 && (numericValue >= 360 || numericValue < 0)) {
							textField.getStyleClass().add("red-outline");
							validData = false;
						}
						if (i == 5 && (numericValue >= 60 || numericValue < 0)) {
							textField.getStyleClass().add("red-outline");
							validData = false;
						}
					}
				}
			}

			if (validData) {
				// Ako su uneseni validni brojevi i slova, dodajemo podatke u tabelu
				String oznaka = txt_lijevo_u.getText() + "-" + txt_sredina_u.getText() + "-" + txt_desno_u.getText();
				int stepen = Integer.parseInt(txt_stepen_u.getText());
				int minut = Integer.parseInt(txt_minut_u.getText());
				double sekund = Double.parseDouble(txt_sekund_u.getText());

				if (stepen < 360 && minut < 60 && sekund < 60) {
					ugao = new Ugao(oznaka, txt_stepen_u.getText(), txt_minut_u.getText(), txt_sekund_u.getText(),
							txt_tacnost_u.getText());
					data_uglovi.add(ugao);
					tabela_u.setItems(data_uglovi);
					tabela_u.refresh();

					// Očistimo TextField-ove nakon dodavanja u tabelu
					listaTxtUgao.forEach(TextField::clear);
					listaTxtUgao.forEach(textField -> textField.getStyleClass().remove("red-outline"));
					System.out.println("Dodano u tabelu!");
				} else {
					System.out.println("Vrijednosti stepena, minuta ili sekundi nisu u dozvoljenom opsegu.");
				}
			} else {
				// Ako nisu uneseni validni brojevi i slova, obavestavamo korisnika
				System.out.println("Popunite sva polja sa validnim brojevima i slovima prije dodavanja u tabelu.");
			}
		} else {
			// Ako nisu svi TextField-ovi popunjeni, obojite odgovarajuće TextField-ove u
			// crveno
			System.out.println("Popunite sva polja prije dodavanja u tabelu.");
			listaTxtUgao.forEach(textField -> {
				if (textField.getText().isEmpty()) {
					textField.getStyleClass().add("red-outline");
				}
			});
		}
	}

	public void otvoriDijalogZaUredivanjeUgao(Ugao odabraniUgao) {
		Dialog<Boolean> dialog = new Dialog<>();
		dialog.setTitle("Uredi ugao: " + redU);

		// Kreirajte polja za unos atributa
		TextField LField = createAlphanumericTextField(odabraniUgao.getLijevo());
		TextField SField = createAlphanumericTextField(odabraniUgao.getSredina());
		TextField DField = createAlphanumericTextField(odabraniUgao.getDesno());
		TextField tacField = createDecimalTextField(odabraniUgao.getTacnost());
		TextField stepenField = createIntegerTextField(odabraniUgao.getStepen());
		TextField minutField = createIntegerTextField(odabraniUgao.getMinut());
		TextField sekundField = createDecimalTextField(odabraniUgao.getSekund());

		// Kreirajte višeslojni raspored za elemente
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		Label oznakaLabel = new Label("Oznaka:");
		HBox oznakaHBox = new HBox(LField, new Label(" - "), SField, new Label(" - "), DField);
		gridPane.addRow(0, oznakaLabel, oznakaHBox);
		gridPane.addRow(1, new Label("Tacnost:"), tacField);
		Label ugaoLabel = new Label("Ugao:");
		HBox ugaoHBox = new HBox(stepenField, new Label("\u00B0 "), minutField, new Label("' "), sekundField,
				new Label("\""));
		gridPane.addRow(2, ugaoLabel, ugaoHBox);

		// Dodajte raspored u dijalog
		VBox content = new VBox(gridPane);
		dialog.getDialogPane().setContent(content);

		// Dodajte gumb "Potvrdi" u dijalog
		ButtonType potvrdiButton = new ButtonType("Potvrdi",
				ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(potvrdiButton,
				ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == potvrdiButton) {
				boolean areFieldsValid = true;

				// Validacija polja
				areFieldsValid &= validateTextField(LField, "[a-zA-Z0-9\\.]*");
				areFieldsValid &= validateTextField(SField, "[a-zA-Z0-9\\.]*");
				areFieldsValid &= validateTextField(DField, "[a-zA-Z0-9\\.]*");
				areFieldsValid &= validateIntegerTextField(stepenField, 0, 360);
				areFieldsValid &= validateIntegerTextField(minutField, 0, 60);
				areFieldsValid &= validateDecimalTextField(sekundField, 0, 60);

				if (!areFieldsValid) {
					showAlert("Greška", "Unesene vrijednosti prelaze ograničenja.",
							AlertType.ERROR);
					return false; // Ne zatvaraj dijalog ako postoje greške
				} else {
					String oznaka = LField.getText() + "-" + SField.getText() + "-" +
							DField.getText();

					odabraniUgao.setOznaka(oznaka);
					odabraniUgao.setTacnost(tacField.getText());
					odabraniUgao.setStepen(stepenField.getText());
					odabraniUgao.setMinut(minutField.getText());
					odabraniUgao.setSekund(sekundField.getText());

					return true;
				}
			}
			return true;
		});

		// Prikaži dijalog i obradi rezultat
		Optional<Boolean> result = dialog.showAndWait();
		while (result.isPresent() && !result.get()) {
			// Ponovo prikaži dijalog dok se ne unesu ispravne vrijednosti
			result = dialog.showAndWait();
		}

		tabela_u.refresh(); // Osvježi prikaz tablice
	}

	public void pupuniTabeluD(ActionEvent event) {

		boolean allFieldsFilledDuzina = listaTxtDuzina.stream()
				.allMatch(textField -> !textField.getText().isEmpty());

		if (allFieldsFilledDuzina) {
			// Ako su svi TextField-ovi popunjeni, nastavite sa dodavanjem u tabelu

			// Provjera validnosti za određenog polja
			boolean validData = true;
			for (int i = 0; i < listaTxtDuzina.size(); i++) {
				TextField textField = listaTxtDuzina.get(i);
				String text = textField.getText();

				if (i == 0 || i == 1) { // Polja 1, 2 i 3 dozvoljavaju slova, brojeve i tacku
					if (!text.matches("[a-zA-Z0-9\\.]*")) {
						textField.getStyleClass().add("red-outline");
						validData = false;
					} else {
						textField.getStyleClass().remove("red-outline");
					}
				} else if (i == 2 || i == 3 || i == 4) { // Polje 6 i 3 dozvoljavaju decimalne brojeve i tacku
					if (!text.matches("\\d*\\.?\\d*")) {
						textField.getStyleClass().add("red-outline");
						validData = false;
					} else {
						textField.getStyleClass().remove("red-outline");
					}

				}
			}

			if (validData) {
				double mm = Double.parseDouble(txt_mm_d.getText());
				double ppm = Double.parseDouble(txt_ppm_d.getText());
				double duz = Double.parseDouble(txt_vrijednost_d.getText());
				double tac = mm + (ppm * (duz / 1000));
				String tacnost = "" + tac;
				duzina = new Duzina(txt_od_d.getText(), txt_do_d.getText(), txt_vrijednost_d.getText(), tacnost);
				data_duzine.add(duzina);
				tabela_d.setItems(data_duzine);
				tabela_d.refresh();

				// Očistimo TextField-ove nakon dodavanja u tabelu
				listaTxtDuzina.forEach(TextField::clear);
				listaTxtDuzina.forEach(textField -> textField.getStyleClass().remove("red-outline"));
				System.out.println("Dodano u tabelu!");
			} else {
				// Ako nisu uneseni validni brojevi i slova, obavestavamo korisnika
				System.out.println("Popunite sva polja sa validnim brojevima i slovima prije dodavanja u tabelu.");
			}
		} else {
			// Ako nisu svi TextField-ovi popunjeni, obojite odgovarajuće TextField-ove u
			// crveno
			System.out.println("Popunite sva polja prije dodavanja u tabelu.");
			listaTxtUgao.forEach(textField -> {
				if (textField.getText().isEmpty()) {
					textField.getStyleClass().add("red-outline");
				}
			});
		}

	}

	public void popuniTabeluK(ActionEvent event) {
		koordinata = new Koordinata(txt_oznaka_k.getText(), txt_y_k.getText(), txt_x_k.getText(), datum2d.isSelected());
		data_koordinate.add(koordinata);
		tabela_k.setItems(data_koordinate);
		tabela_k.refresh();
	}

	// Metoda za prikazivanje Alert dijaloga
	private void showAlert(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void izravnaj2D(ActionEvent event) {
		KlasicanNacin2D kn = new KlasicanNacin2D(data_pravci, data_uglovi, data_duzine, data_koordinate,
				Double.parseDouble(txt_s0_2d.getText()), Double.parseDouble(txt_nivoZnacajnosti_2d.getText()));
		kn.napraviIzvjestaj();
	}

}