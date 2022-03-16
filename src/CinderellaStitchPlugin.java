/* ------------------------------------------------------------------
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public License
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.s
# ------------------------------------------------------------------
*/

import java.util.ArrayList;

import java.nio.ByteBuffer;

import geometrischeFiguren.Circle;
import geometrischeFiguren.Point;
import geometrischeFiguren.Segment;
import geometrischeFiguren.EndcodeTajimaStitch;
import de.cinderella.api.cs.CindyScript;
import de.cinderella.api.cs.CindyScriptPlugin;

/**
 * Hauptklasse wird von Cinderella aufgrufen und verkn�pft Cinderella mit Plugin
 * @author Simon Doubleday
 *
 */
public class CinderellaStitchPlugin extends CindyScriptPlugin {

	/**
	 * @return Name des Plugins
	 */
	@CindyScript("getName")
	public String getName() {
		return "CinderellaStitchPlugin";
	}

	/**
	 * @return Name des Autors
	 */
	public String getAuthor() {
		return "Simon Doubleday";
	}

	// Variablen:

	private double aufloesung = 1;

	// Groesse der dargestellten Flaeche in Cinderella
	private Double[] ecke1;
	private Double[] ecke2;
	private Double[] ecke3;
	private Double[] ecke4;

	// Liste der Geometrischen Figuren
	ArrayList<Point> punkteListe = new ArrayList<>();
	ArrayList<Segment> segementListe = new ArrayList<>();
	ArrayList<Circle> circleListe = new ArrayList<>();

	/**
	 * Hauptprogramm: Ruft fuer jede geometrische Konstruktion den Stichcode ab und gibt ihn durch die Methode dst.Datei in Klasse Dateiausgabe aus
	 */
	@CindyScript("startProgrammAusgabe")
	public void startProgrammAusgabe() {

		ByteBuffer ausgabe = ByteBuffer.allocate(1000);
		ByteBuffer test = ByteBuffer.allocate(100);
		Point davor = punkteListe.get(0);
		
		//Erster Stich
		//Jump Stich am Anfang vermeiden und ersten Einstich am Ausgangspunkt setzen
		if(segementListe.isEmpty()) {
			davor = circleListe.get(0).getAnfangsPunkt();
		}else {
			davor = segementListe.get(0).getAnfangsPunkt();
		}
		ausgabe.put(EndcodeTajimaStitch.encodeDST((long)davor.getX(), (long)davor.getY()));
		// ByteBuffer test3 = ByteBuffer.allocate(100);
		/*
		 * for (Point s : punkteListe) { ausgabe += s.getName(); ausgabe += "\n"; }
		 */
		
		//ruft den Stichcode von allem Strecken ab
		for (Segment s : segementListe) {

			// byte[] test2 = s.stitchCode().array();
			// test = s.stitchCode();

			ausgabe.put(s.stitchCode2(davor, aufloesung).flip());
			davor = s.getEndPunkt();
			// test.flip();
			// ausgabe.put(test);
			// test.put(test3);
		}

		//ruft den Stichcode von allen Kreisen ab
		for (Circle c : circleListe) {

			// byte[] test2 = s.stitchCode().array();
			// test = s.stitchCode();

			ausgabe.put(c.stitchCode(davor, aufloesung).flip());
			davor = c.getEndPunkt();
			// test.flip();
			// ausgabe.put(test);
			// test.put(test3);
		}

		//Ausgabe des Stichcodes. flip() und Uebertragen in anderen ByteBuffer; um Nullen am Ende der Datei zu verhindern.
		ByteBuffer ausgabeOhneNullen = ByteBuffer.allocate(ausgabe.position());
		ausgabe.flip();
		ausgabeOhneNullen.put(ausgabe);

		DateiAusgabe.dstDatei(ausgabeOhneNullen);
		// ausgabe.flip();
		// DateiAusgabe.dstDatei(ausgabe);

	}

	/**
	 * Speichert die vier Ecken des sichtbaren Bereichs in Cinderella 
	 * @param forms ArrayList mit den vier Ecken des sichtbaren Bereichs in Cinderella
	 */
	@CindyScript("getScreenbound")
	public void getScreenbound(ArrayList<Double[]> forms) {
		ecke1 = forms.get(0);
		ecke2 = forms.get(1);
		ecke3 = forms.get(2);
		ecke4 = forms.get(3);
	}

	/**
	 * Fuegt alle Punkte aus Cinderella als 'Point' einer Liste hinzu. 
	 * @param forms Punkte in Cinderella als ArrayList  
	 */
	@CindyScript("getPoints")
	public void getPoints(ArrayList<String[]> forms) {
		// Listen werden einmal angelegt, wenn PLugin geladen wird.
		//Deswegen jedes mal Liste löschen wenn Methode aufgerrufen wird. 
		// sonst füllt sich Liste immer weiter...
		punkteListe.clear();  
		
		for (String[] arrayList : forms) {

			String name = arrayList[0];
			String x = arrayList[1];
			String y = arrayList[2];

			// Ergibt damit die vorgabe von 1 StichEinheit pro 0,1 im Koordinatensystem
			// wenn aufloesung = 1;
			double xDST = Double.valueOf(x) * 10 * aufloesung;
			double yDST = Double.valueOf(y) * 10 * aufloesung;

			Point point = new Point(name, xDST, yDST);

			punkteListe.add(point);

		}

	}

	/**
	 * Fuegt alle Strecken aus Cinderella als 'Segement' einer Liste hinzu. 
	 * @param forms Strecken in Cinderella als ArrayList  
	 */
	@CindyScript("getSegments")
	public void getSegments(ArrayList<String[]> forms) {
		// Listen werden einmal angelegt, wenn PLugin geladen wird.
		//Deswegen jedes mal Liste loeschen, wenn Methode aufgerrufen wird. 
		// sonst faellt sich Liste immer weiter...
		
		segementListe.clear();
		for (String[] arrayList : forms) {

			String name = arrayList[0];
			String anfangsPunktString;
			String endPunktString;
			String definition = arrayList[1];
			String[] split;
			split = definition.split("[();]");

			anfangsPunktString = split[1];
			endPunktString = split[2];

			Point anfangsPunkt = null;
			int iterator = 0;
			// Lineare Suche nach den Anfangs und Endpunkten der Strecke (ander einfache
			// Suchen nicht moeglich weil Liste nicht sortiert ist)

			/*
			 * Moeglicherweise Listesortieren lassen und dann mit Binaerer suche drueber 
			 * Testen ob des Klappt. Collections.sort(liste); Collections.binarySearch(list, key);
			 */

			while (iterator < punkteListe.size() && anfangsPunkt == null) {

				if (punkteListe.get(iterator).getName().compareTo(anfangsPunktString) == 0) {
					anfangsPunkt = punkteListe.get(iterator);

				}

				iterator += 1;
			}

			Point endPunkt = null;
			iterator = 0;
			// Lineare Suche nach den Anfangs und Endpunkte der Strecke (ander einfache
			// Suchen nicht möglich weil Liste nicht sortiert ist)
			while (iterator < punkteListe.size() && endPunkt == null) {

				if (punkteListe.get(iterator).getName().compareTo(endPunktString) == 0) {
					endPunkt = punkteListe.get(iterator);

				}
				iterator += 1;
			}

			Segment segment = new Segment(name, anfangsPunkt, endPunkt);

			segementListe.add(segment);

		}

	}

	/**
	 * Fuegt alle Kreise aus Cinderella als 'Circle' einer Liste hinzu. 
	 * @param forms Kreise in Cinderella als ArrayList  
	 */
	@CindyScript("getCircles")
	public void getCircles(ArrayList<String[]> forms) {
		// Listen werden einmal angelegt, wenn PLugin geladen wird.
		//Deswegen jedes mal Liste löschen wenn Methode aufgerrufen wird. 
		// sonst fuellt sich Liste immer weiter...
		circleListe.clear();
		for (String[] arrayList : forms) {

			String name = arrayList[0];
			String mittelpunktString = arrayList[1];
			String radius = arrayList[2];
			String[] split;
			split = mittelpunktString.split("[,\\x5B\\x5D]");

			double[] mittelpunkt = { Double.valueOf(split[1]) * 10 * aufloesung,
					Double.valueOf(split[2]) * 10 * aufloesung };
			Circle circle = new Circle(name, mittelpunkt, Double.valueOf(radius) * 10 * aufloesung);

			circleListe.add(circle);

		}
	}

	/**
	 * Moeglicherweise alles notwendige Programm mithilfe des
	 * "import(loadProgramm())" in Cinderella laden lassen. erhoeht Komfort fuer
	 * Anwender.
	 * TODO NOCH UNGETESTET
	 * @return
	 */
	@CindyScript("loadProgramm")
	public String loadProgramm() {

		return ("createtool([\"Move\",\"Point\",\"Segment\",\"Circle\"],2,2,flipped->true);\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "programmAufruf():=(\n"
				+ "use(\"CinderellaStitchPlugin\");\n"
				+ "println(Screenbounds());\n"
				+ "println(getScreenbound(Screenbounds())); //getter im Plugin\n"
				+ "\n"
				+ "//Punkte abrufen\n"
				+ "pts2;\n"
				+ "forall(allpoints(),\n"
				+ "pts1 = [[#,#.x,#.y]];\n"
				+ "println(pts1);\n"
				+ "pts2 = concat(pts2,pts1);\n"
				+ ");\n"
				+ "println(pts2);\n"
				+ "getPoints(pts2); //getter im Plugin\n"
				+ "\n"
				+ "//Strecken abrufen\n"
				+ "seg2;\n"
				+ "forall(allsegments(),\n"
				+ "seg1 = [[#,inspect(#,\"definition\")]];\n"
				+ "seg2 = concat(seg2,seg1);\n"
				+ ");\n"
				+ "println(seg2);\n"
				+ "getSegments(seg2); //getter im Plugin\n"
				+ "\n"
				+ "//Strecken abrufen\n"
				+ "circ2;\n"
				+ "forall(allcircles(),\n"
				+ "circ1 = [[#,#.center,#.radius]];\n"
				+ "circ2 = concat(circ2,circ1);\n"
				+ ");\n"
				+ "println(circ2);\n"
				+ "getCircles(circ2); //getter im Plugin\n"
				+ "\n"
				+ "startProgrammAusgabe();\n"
				+ "clear(); //alle Variablen löschen\n"
				+ ");\n"
				+ "\n"
				+ "programmAufruf();");
	}

}
