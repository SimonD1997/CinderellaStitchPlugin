
import java.util.ArrayList;
import java.math.RoundingMode;
import java.nio.ByteBuffer;

import geometrischeFiguren.EndcodeTajimaStitch;
import geometrischeFiguren.Point;
import geometrischeFiguren.Segment;
import de.cinderella.api.cs.CindyScript;
import de.cinderella.api.cs.CindyScriptPlugin;

public class CinderellaStitchPlugin extends CindyScriptPlugin {

	@CindyScript("getName")
	public String getName() {
		return "CinderellaStitchPlugin";
	}

	public String getAuthor() {
		return "Simon Doubleday";
	}

	// Variablen:
	
	private double aufloesung = 1;


	// Größe der dargestellten Fläche in Cinderella
	private Double[] ecke1;
	private Double[] ecke2;
	private Double[] ecke3;
	private Double[] ecke4;

	// Liste der Geometrischen Figuren
	ArrayList<Point> punkteListe = new ArrayList<>();
	ArrayList<Segment> segementListe = new ArrayList<>();

	@CindyScript("startProgrammAusgabe")
	public void startProgrammAusgabe() {

		ByteBuffer ausgabe = ByteBuffer.allocate(100);
		ByteBuffer test = ByteBuffer.allocate(100);
		Point davor = new Point(getName(), 0, 0);
		//ByteBuffer test3 = ByteBuffer.allocate(100);
		/*
		for (Point s : punkteListe) {
			ausgabe += s.getName();
			ausgabe += "\n";
		}
		 */
		for (Segment s : segementListe) {
			
			//byte[] test2 = s.stitchCode().array();
			//test = s.stitchCode();
			
			ausgabe.put(s.stitchCode(davor).flip()) ;
			davor = s.getEndPunkt();
			//test.flip();
			//ausgabe.put(test);
			//test.put(test3);
		}
		
		ByteBuffer test3 = ByteBuffer.allocate(ausgabe.position());
		ausgabe.flip();
		test3.put(ausgabe);
		
		DateiAusgabe.dstDatei(test3);
		//ausgabe.flip();
		//DateiAusgabe.dstDatei(ausgabe);

		

	}

	/**
	 * 
	 * @param forms
	 */
	@CindyScript("getScreenbound")
	public void getScreenbound(ArrayList<Double[]> forms) {
		ecke1 = forms.get(0);
		ecke2 = forms.get(1);
		ecke3 = forms.get(2);
		ecke4 = forms.get(3);
	}

	/**
	 * 
	 * @param forms
	 */
	@CindyScript("getPoints")
	public void getPoints(ArrayList<String[]> forms) {
		for (String[] arrayList : forms) {

			String name = arrayList[0];
			String x = arrayList[1];
			String y = arrayList[2];
			
	
			//Ergibt damit die vorgabe von 1 Stich pro 0,1 im Koordinatensystem
			double xDST = Double.valueOf(x)*10*aufloesung;
			double yDST = Double.valueOf(y)*10*aufloesung;
			

			Point point = new Point(name, xDST, yDST);

			punkteListe.add(point);

		}

	}

	/**
	 * 
	 * @param forms
	 */
	@CindyScript("getSegments")
	public void getSegments(ArrayList<String[]> forms) {

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
			// Suchen nicht möglich weil Liste nicht sortiert ist)
			
			/*
			 * Möglicherweise Listesortieren lassen und dann mit Binärer suche drüber
			 * Testen ob des Klappt. 
			 * Collections.sort(liste);
			 * Collections.binarySearch(list, key);
			 */
			
			while (iterator < punkteListe.size() && anfangsPunkt == null) {

				if (punkteListe.get(iterator).getName().compareTo(anfangsPunktString) == 0) {
					anfangsPunkt = punkteListe.get(iterator);

				}

				iterator += 1;
			}

			Point endPunkt = null;
			iterator = 0;
			// Lineare Suche nach den Anfangs und Endpunkten der Strecke (ander einfache
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
	 * Möglicherweise alles notwenidge Programm mithilfe des
	 * "import(loadProgramm())" in Cinderella laden lassen. Herhöht Komfort für
	 * Anwender.
	 * 
	 * @return
	 */
	@CindyScript("loadProgramm")
	public String loadProgramm() {

		return ("");
	}

}
