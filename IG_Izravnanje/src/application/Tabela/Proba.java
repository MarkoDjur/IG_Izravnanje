package wagu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.awt.Desktop;
import java.nio.file.Paths;

public class Proba {
//	private static final int ArrayList = 0;

	public static void main(String[] args) throws IOException {
		Integer[] od = { 1, 2, 3, 4, 5 };
		Integer[] doo = { 2, 3, 4, 5, 6 };
		double[] v = { 0.001, 0.001, 0.001, 0.001, 0.001 };
		double[] Qvii = { 0.001, 0.001, 0.001, 0.001, 0.002 };
		double[] loc = { 0.001, 0.001, 0.001, 0.001, 0.003 };
		double[] Qlii = { 0.001, 0.001, 0.001, 0.001, 0.004 };
		double[] rii = { 0.001, 0.001, 0.001, 0.001, 0.005 };
		double[] uv = { 0.001, 0.001, 0.001, 0.001, 0.006 };

		File proba = new File("/Users/kantarion/Desktop/proba.txt");

		ArrayList<String> nemore = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(proba);
			BufferedReader br = new BufferedReader(fr);
			Object[] tableLines = br.lines().toArray();
			for (int i = 0; i < tableLines.length; i++) {
				String line = tableLines[i].toString().trim();
				String[] dataRow = line.split(",");
				// u string prebaci bez tipa podataka
//				System.out.println(Arrays.toString(dataRow));
				nemore.add(Arrays.toString(dataRow));

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<List<String>> da = new ArrayList<>(); // Lista za spremanje redova elemenata

		List<String> ne = new ArrayList<>(); // Privremena lista za svaki red elemenata

		int vel = od.length;

		for (int i = 0; i < vel; i++) {
			String Od = od[i].toString();
			String Do = doo[i].toString();
			String V = String.valueOf(v);
			String Loc = String.valueOf(loc);
			String qvii = String.valueOf(Qvii);
			String qlii = String.valueOf(Qlii);
			String Rii = String.valueOf(rii);
			String UV = String.valueOf(uv);
			ne.add(Od);
			ne.add(Do);
			ne.add(V);
			ne.add(Loc);
			ne.add(qvii);
			ne.add(qlii);
			ne.add(Rii);
			ne.add(UV);

			if (i % 1 == 0) { // Ako je svaki treći element, dodaj red u listu i stvori novi red
				da.add(ne);
				ne = new ArrayList<>();
			}
		}
		System.out.println(da);

		// Dodavanje posljednjeg reda ako je potrebno
		if (!ne.isEmpty()) {
			da.add(ne);
		}

//		// Ispisivanje liste redova
//		for (List<String> row : da) {
//			for (String e : row) {
//				System.out.print(e + " ");
//			}
//			System.out.println(); // Novi red nakon svakog retka
//		}
		System.out.println(da.size());

		List<List<String>> lista = new ArrayList<>(); // Lista za spremanje redova elemenata

		List<String> red = new ArrayList<>(); // Privremena lista za svaki red elemenata

		// Dodavanje elemenata u listu i prelazak na novi red nakon svake petlje
		for (int i = 1; i <= 8; i++) {
			String element = "Element " + i;
			red.add(element);

			if (i % 2 == 0) { // Ako je svaki treći element, dodaj red u listu i stvori novi red
				lista.add(red);
				red = new ArrayList<>();
			}
		}

		System.out.println(lista);
		// Dodavanje posljednjeg reda ako je potrebno
		if (!red.isEmpty()) {
			lista.add(red);
		}

		// Ispisivanje liste redova
		for (List<String> row : lista) {
			for (String element : row) {
				System.out.print(element + " ");
			}
			System.out.println(); // Novi red nakon svakog retka
		}

		System.out.println(lista.size());

		List<String> header = Arrays.asList("OD", "DO", "V", "Qvii", "loc", "Qlii", "rii", "u-v");
//		List<List<String>> lista = Arrays.asList(more);
		Board board = new Board(500);
		Table table = new Table(board, 50, header, da);
		List<Integer> colWidthsList = Arrays.asList(14, 14, 14, 14, 14, 14, 14, 14);
		table.setColWidthsList(colWidthsList);
		Block tableBlock = table.tableToBlocks();
		board.setInitialBlock(tableBlock);
		board.build();
		String preview1 = board.getPreview();

		System.out.println(preview1);
//
//		List<String> headersList = Arrays.asList("NAME", "GENDER", "MARRIED");
//		Board board = new Board(100);
//		List<List<String>> rowsListEdited = Arrays.asList(Arrays.asList("Alice", "Male", "Yes", "29", "580.40"),
//				Arrays.asList("Alyse", "Female", "No", "26", "7000.89"),
//				Arrays.asList("Eddy", "Male", "No", "23", "1200.27"));
//
//		String col = rowsListEdited.get(1).get(0);
//		List<List<String>> lista = new ArrayList<>();
//		List<String> listb = new ArrayList<>();
//		for (int i = 0; i < rowsListEdited.get(0).size(); i++) {
//			for (int j = 0; j < rowsListEdited.size(); j++) {
//				System.out.println(rowsListEdited.get(j).get(i));
//				listb.add(rowsListEdited.get(j).get(i));
//			}
//			lista.add(listb);
//		}
//		System.out.println(lista);
//
//		Table table = new Table(board, 100, headersList, rowsListEdited);
//		table.invalidate().setGridMode(Table.GRID_NON).setRowsList(rowsListEdited);
//		String preview3 = board.invalidate().setInitialBlock(table.tableToBlocks()).build().getPreview();

		File izvjestaj = new File("/Users/kantarion/Desktop/proba.txt");
		FileWriter fw = new FileWriter(izvjestaj);
		fw.write(preview1);
		fw.close();

// OTVARANJE DATOTEKE

// first check if Desktop is supported by Platform or not
		if (!Desktop.isDesktopSupported()) {
			System.out.println("Desktop is not supported");
			return;
		}

		Desktop desktop = Desktop.getDesktop();
		if (izvjestaj.exists())
			try {
				desktop.open(izvjestaj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// let's try to open PDF file
		if (izvjestaj.exists())
			try {
				desktop.open(izvjestaj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

// NEKI POKUSAJ

//	public static void AddColumn(String[] novaKolona, String direktorijum, String delimiter, int column) {
//		try {
//			List<String> data = Files.readAllLines(Paths.get(direktorijum));
//			PrintWriter pw = new PrintWriter(direktorijum);
//			FileWriter fw = new FileWriter(direktorijum, true);
//			pw = new PrintWriter(fw);
//
//			for (int i = 0; i < data.size(); i++) {
//				String[] line = data.get(i).split(delimiter);
//				List<String> record = new ArrayList<String>(Arrays.asList(line));
//				record.add(column, novaKolona[i]);
//				pw.println(String.join(delimiter, record));
//			}
//
//			pw.close();
//			System.out.println("Dodana kolona");
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
	}

}
