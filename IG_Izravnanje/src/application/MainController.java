package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

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

	private List<TextField> textFields = new ArrayList<>();

	int redVR;
	int redV;

	public void initialize() {
		textFields.add(txt_od);
		textFields.add(txt_do);
		textFields.add(txt_visinskaRazlika);
		addTextFieldChangeListener(txt_od);
		addTextFieldChangeListener(txt_do);
		addTextFieldChangeListener(txt_visinskaRazlika);

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
		File proba = new File("/Users/kantarion/Documents/GitHub/IG_Izravnanje/IG_Izravnanje/src/application/matA.txt");
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

		radio_minimalanTrag.setSelected(true);
		txt_s0.setText("0.8");
		tabela_v.setItems(data_v);
		tabela_v.refresh();
	}

	public void popuniTabeluVr(ActionEvent event) {

		// Provjeravamo jesu li svi TextField-ovi popunjeni prije dodavanja u tabelu
		boolean allFieldsFilled = textFields.stream().allMatch(textField -> !textField.getText().isEmpty());

		List<TextField> neispunjeniTextFields = new ArrayList<>();

		// Provjerite svaki TextField pojedinačno i obojite ga crveno ako nije popunjen
		for (TextField textField : textFields) {
			if (textField.getText().isEmpty()) {
				textField.getStyleClass().add("red-outline");
				neispunjeniTextFields.add(textField);
			} else {
				textField.getStyleClass().remove("red-outline");
			}
		}

		if (!neispunjeniTextFields.isEmpty()) {
			System.out.println("Popunite sljedeća polja: ");
			for (TextField neispunjeniTextField : neispunjeniTextFields) {
				System.out.println(neispunjeniTextField.getId());
			}
		} else {
			// Ako su popunjeni svi TextField-ovi, dodajemo podatke u tabelu
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
			textFields.forEach(TextField::clear);
			textFields.forEach(textField -> textField.getStyleClass().remove("red-outline"));
			System.out.println("Dodano u tabelu!");

		}

	}

	public void popuniTabeluV(ActionEvent event) {
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
	}

	public void izravnaj(ActionEvent e) {
		MinimalniTrag1D mt = new MinimalniTrag1D(data_v, data_vr, Double.parseDouble(txt_s0.getText()),
				Double.parseDouble(txt_nivoZnacajnosti.getText()));
		KlasicanNacin1D kn = new KlasicanNacin1D(data_v, data_vr, Double.parseDouble(txt_s0.getText()),
				Double.parseDouble(txt_nivoZnacajnosti.getText()));
		if (radio_klasicno.isSelected()) {
			kn.napraviIzvjestaj();
		}
		if (radio_minimalanTrag.isSelected()) {
			mt.napraviIzvjestaj();
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

}