package geometrischeFiguren;

import java.nio.ByteBuffer;

public class Segment {

	Point anfangsPunkt;
	Point endPunkt;

	String name;

	public Segment(String name, Point anfangsPunkt, Point endPunkt) {
		this.anfangsPunkt = anfangsPunkt;
		this.endPunkt = endPunkt;
		this.name = name;

	}

	public void setAnfangsPunkt(Point anfangsPunkt) {
		this.anfangsPunkt = anfangsPunkt;
	}

	public void setEndpunkt(Point endPunkt) {
		this.endPunkt = endPunkt;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getAnfangsPunkt() {
		return this.anfangsPunkt;
	}

	public Point getEndPunkt() {
		return this.endPunkt;
	}

	public String getName() {
		return this.name;
	}

	/*
	 * public ByteBuffer stitchCode() {
	 * 
	 * // ohne zwischen stiche. Maschiene würde bei einer zu größen Stichlänge (auto
	 * // split) zwischenstiche einfügen // sinvoller automatisch zwischenstiche
	 * berechnen.
	 * 
	 * ByteBuffer ausgabe = ByteBuffer.allocate(6);
	 * 
	 * ausgabe.put(EndcodeTajimaStitch.encodeDST((int) anfangsPunkt.x, (int)
	 * anfangsPunkt.y, true)); ausgabe.put(EndcodeTajimaStitch.encodeDST( 0, 0,
	 * false)); ausgabe.put(EndcodeTajimaStitch.encodeDST((int) endPunkt.x - (int)
	 * anfangsPunkt.x, (int) endPunkt.y - (int) anfangsPunkt.y));
	 * 
	 * return (ausgabe); }
	 */
	/**
	 * 
	 * @param aufloesung in welchem Verhältnis alles ausgegeben wird.
	 * @return
	 */
	public ByteBuffer stitchCode(Point punktDavor, double aufloesung) {

		// Berechnen von zischenstichen. 5-7 mmm 2,5 - 3.0 mm
		// brother max 5mm
		// best 1,5mm
		// double aufloesung = 1;
		int abstaende = (int) (15 * aufloesung); // umgerechnet in nadelbewegungen mit 0,1mm

		int anzahlStiche = (int) (laenge() / abstaende); // aufgerundet, da sonst ein stich unter minimalabstand fällt
		int stichZähler = 0;

		ByteBuffer ausgabe = ByteBuffer.allocate(10000); // TODO noch schauen, das der Buffer nicht zu
		// groß ist, sonst gibt es zu viele stellen.

		if (punktDavor.equals(this.anfangsPunkt)) {

			stichZähler += 0;
		} else {
			long bildWechselX = (long) anfangsPunkt.x - (long) punktDavor.x;
			long bildWechselY = (long) anfangsPunkt.y - (long) punktDavor.y;

			// Bildwechsel zur nächsten Form mithilfe eines oder mehrer Sprungstiche
			while (bildWechselX > 121 || bildWechselX < -121 || bildWechselY > 121 || bildWechselY < -121) {

				long xAusgabeSpeicher = 0;
				long yAusgabeSpeicher = 0;
				if (bildWechselX > 121) {
					xAusgabeSpeicher = 121;
					bildWechselX -= 121;
				} else if (bildWechselX < -121) {
					xAusgabeSpeicher = -121;
					bildWechselX += 121;
				} else if (bildWechselY > 121) {
					yAusgabeSpeicher = 121;
					bildWechselY -= 121;
				} else if (bildWechselY < -121) {
					yAusgabeSpeicher = -121;
					bildWechselY += 121;
				}
				ausgabe.put(EndcodeTajimaStitch.encodeDST(xAusgabeSpeicher, yAusgabeSpeicher, true));

				stichZähler += 1;

			}

			ausgabe.put(EndcodeTajimaStitch.encodeDST(bildWechselX, bildWechselY, true));

			ausgabe.put(EndcodeTajimaStitch.encodeDST(0, 0));
			stichZähler += 2;

		}

		long neuerAbstandX = (long) ((endPunkt.x - anfangsPunkt.x) / anzahlStiche);

		Point aktuellerPunkt;
		Point vorletzterPunkt;
		Point punktVorher;
		/*if (neuerAbstandX == 0) {
			aktuellerPunkt = geradenGleichungY(neuerAbstandY + anfangsPunkt.y);
			vorletzterPunkt = geradenGleichungY(neuerAbstandY * anzahlStiche + anfangsPunkt.y);
		} else {
*/
			aktuellerPunkt = geradenGleichung(neuerAbstandX + anfangsPunkt.x);
			punktVorher = anfangsPunkt;
			vorletzterPunkt = geradenGleichung(neuerAbstandX * anzahlStiche + anfangsPunkt.x);
		//}
		
		
		// zwischenschritte einzeichnen
		for (int i = 1; i < anzahlStiche; i++) {

			aktuellerPunkt = geradenGleichung(neuerAbstandX*i + anfangsPunkt.x);
			ausgabe.put(EndcodeTajimaStitch.encodeDST((long) aktuellerPunkt.x - (long) punktVorher.x,
					(long) aktuellerPunkt.y - (long) punktVorher.y));
			punktVorher = aktuellerPunkt;
			
			
			
			stichZähler += 1;
		}

		// falls es keine zwischenschritte gab, direkt den Endpunkt einzeichnen
		if (anzahlStiche == 1) {
			ausgabe.put(EndcodeTajimaStitch.encodeDST((long) endPunkt.x - (long) anfangsPunkt.x,
					(long) endPunkt.y - (long) anfangsPunkt.y));
			stichZähler += 1;

			// falls endpunkt auf letzten zwischenpunkt fällt, fehlt genau ein stich. bzw.
			// es wird ein Stich mit vektor (0,0) ausgegeben
		} else if (endPunkt.x == vorletzterPunkt.x && endPunkt.y == vorletzterPunkt.y) {
			ausgabe.put(EndcodeTajimaStitch.encodeDST((long) aktuellerPunkt.x - (long) anfangsPunkt.x,
					(long) aktuellerPunkt.y - (long) anfangsPunkt.y));
			stichZähler += 1;

			// da wir vorher mit dem cast auf int abrunden, wird der letzte Stiche zum
			// Endpunkt immer größer wie die mindeststichlänge sein.
		} else {
			ausgabe.put(EndcodeTajimaStitch.encodeDST((long) endPunkt.x - (long) vorletzterPunkt.x,
					(long) endPunkt.y - (long) vorletzterPunkt.y));
			stichZähler += 1;
		}

		ByteBuffer ausgabe2 = ByteBuffer.allocate((stichZähler * 3) + 3);
		ausgabe.flip(); // verhindert, dass die restlichen stellen des unbeschriebenen buffer als null
						// werte ausgegeben werden.
		ausgabe2.put(ausgabe);

		return (ausgabe);
	}

	/**
	 * 
	 * @param aufloesung in welchem Verhältnis alles ausgegeben wird.
	 * @return
	 */
	public ByteBuffer stitchCode2(Point punktDavor, double aufloesung) {

		// Berechnen von zischenstichen. 5-7 mmm 2,5 - 3.0 mm
		// brother max 5mm
		// best 1,5mm
		// double aufloesung = 1;
		int abstaende = (int) (15 * aufloesung); // umgerechnet in Nadelbewegungen mit 0,1mm

		int anzahlStiche = (int) (laenge() / abstaende); // abgerundet, da sonst ein Stich unter Minimalabstand fällt
		int stichZaehler = 0;

		ByteBuffer ausgabe = ByteBuffer.allocate(10000); // TODO noch schauen, das der Buffer nicht zu
		// groß ist, sonst gibt es zu viele stellen.

		if (punktDavor.equals(this.anfangsPunkt)) {

			stichZaehler += 0;
		} else {
			long bildWechselX = (long) anfangsPunkt.x - (long) punktDavor.x;
			long bildWechselY = (long) anfangsPunkt.y - (long) punktDavor.y;

			// Bildwechsel zur nächsten Form mithilfe eines oder mehrer Sprungstiche
			while (bildWechselX > 121 || bildWechselX < -121 || bildWechselY > 121 || bildWechselY < -121) {

				long xAusgabeSpeicher = 0;
				long yAusgabeSpeicher = 0;
				if (bildWechselX > 121) {
					xAusgabeSpeicher = 121;
					bildWechselX -= 121;
				} else if (bildWechselX < -121) {
					xAusgabeSpeicher = -121;
					bildWechselX += 121;
				} else if (bildWechselY > 121) {
					yAusgabeSpeicher = 121;
					bildWechselY -= 121;
				} else if (bildWechselY < -121) {
					yAusgabeSpeicher = -121;
					bildWechselY += 121;
				}
				ausgabe.put(EndcodeTajimaStitch.encodeDST(xAusgabeSpeicher, yAusgabeSpeicher, true));

				stichZaehler += 1;

			}

			ausgabe.put(EndcodeTajimaStitch.encodeDST(bildWechselX, bildWechselY, true));

			ausgabe.put(EndcodeTajimaStitch.encodeDST(0, 0));
			stichZaehler += 2;

		}
		
		//Richtungsvektor 
		long endpunktX = (long) endPunkt.x;
		long endpunktY = (long) endPunkt.y;
		long anfangspunktX = (long) anfangsPunkt.x;
		long anfangspunktY = (long) anfangsPunkt.y;
		
		long vekX = (endpunktX-anfangspunktX);
		long vekY = endpunktY-anfangspunktY;
		
		long aktuellerPunktX = anfangspunktX ;
		long aktuellerPunktY = anfangspunktY;
		
		//TODO Zwischnenschritte als Punkte berechnen und dann dazwischen die Vektoren 
		// Mininimert möglicherwieise etwas die rundungsfehler des Ortsvektors
		// zwischenschritte einzeichnen
		for (int i = 1; i < anzahlStiche; i++) {

			ausgabe.put(EndcodeTajimaStitch.encodeDST((1/anzahlStiche) *vekX,
					(1/anzahlStiche) *vekY));
			aktuellerPunktX = anfangspunktX + (i/anzahlStiche) *vekX;
			aktuellerPunktY = anfangspunktY + (i/anzahlStiche) *vekY;
			stichZaehler += 1;
		}
		
		if (anzahlStiche == 0) {
			ausgabe.put(EndcodeTajimaStitch.encodeDST(vekX,
					vekY,true));
			
			stichZaehler += 1;

			
			// falls es keine zwischenschritte gab, direkt den Endpunkt einzeichnen	
		}  else {
			ausgabe.put(EndcodeTajimaStitch.encodeDST(endpunktX-aktuellerPunktX ,
					endpunktY-aktuellerPunktY));
			stichZaehler += 1;
		}

		ByteBuffer ausgabe2 = ByteBuffer.allocate((stichZaehler * 3) + 3);
		ausgabe.flip(); // verhindert, dass die restlichen stellen des unbeschriebenen buffer als null
						// werte ausgegeben werden.
		ausgabe2.put(ausgabe);

		return (ausgabe);
	}

	
	/**
	 * 
	 * @param neuX
	 * @return
	 */
	private Point geradenGleichung(double neuX) {
		double steigung = (this.endPunkt.y - this.anfangsPunkt.y) / (this.endPunkt.x - this.anfangsPunkt.x);
		double neuY = steigung * (neuX - this.anfangsPunkt.x) + this.anfangsPunkt.y;
		Point point = new Point(this.name + neuX, neuX, neuY);
		return point;
	}

	
	
	/**
	 * 
	 * @return
	 */
	private double laenge() {
		return Math.sqrt(square(this.endPunkt.x - this.anfangsPunkt.x) + square(this.endPunkt.y - this.anfangsPunkt.y));
	}

	private double square(double zahl) {
		return zahl * zahl;
	}

}
