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
	 * // ohne zwischen stiche. Maschiene w�rde bei einer zu gr��en Stichl�nge (auto
	 * // split) zwischenstiche einf�gen // sinvoller automatisch zwischenstiche
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
	 * @param aufloesung in welchem Verh�ltnis alles ausgegeben wird.
	 * @return
	 */
	public ByteBuffer stitchCode(Point punktDavor, double aufloesung) {
		
		// Berechnen von zischenstichen. 5-7 mmm 2,5 - 3.0 mm
		// brother max 5mm
		// best 1,5mm
		//double aufloesung = 1;
		int abstaende = (int)(15*aufloesung); // umgerechnet in nadelbewegungen mit 0,1mm

		int anzahlStiche = (int) (laenge() / abstaende); // aufgerundet, da sonst ein stich unter minimalabstand f�llt
		int stichZ�hler = 0;

		ByteBuffer ausgabe = ByteBuffer.allocate(anzahlStiche * 3 + 9); // TODO noch schauen, das der Buffer nicht zu
		// gro� ist, sonst gibt es zu viele stellen.

		
		if (punktDavor.equals(anfangsPunkt)) {
			
			stichZ�hler += 0;
		}else {
			long bildWechselX =(long) anfangsPunkt.x -(long)punktDavor.x;
			long bildWechselY =(long) anfangsPunkt.y - (long)punktDavor.y;
			
			//Bildwechsel zur n�chsten Form mithilfe eines oder mehrer Sprungstiche
			while (bildWechselX > 121) {
				
				ausgabe.put(EndcodeTajimaStitch.encodeDST( 121, 0, true));
				bildWechselX =- 121;
				stichZ�hler += 1;
				
			}
			while (bildWechselX > 121) {
				
				ausgabe.put(EndcodeTajimaStitch.encodeDST( 0, 121, true));
				bildWechselX =-121; 
				stichZ�hler += 1;
				
			}
			
			ausgabe.put(EndcodeTajimaStitch.encodeDST( bildWechselX, bildWechselY, true));
			
			
			ausgabe.put(EndcodeTajimaStitch.encodeDST(0, 0));
			stichZ�hler += 2;

		}
		
		long neuerAbstandX = (long) ((endPunkt.x - anfangsPunkt.x) / anzahlStiche);

		Point aktuellerPunkt = geradenGleichung(neuerAbstandX);

		//zwischenschritte einzeichnen
		for (int i = 1; i < anzahlStiche; i++) {

			ausgabe.put(EndcodeTajimaStitch.encodeDST((long) aktuellerPunkt.x-(long)anfangsPunkt.x, (long) aktuellerPunkt.y-(long)anfangsPunkt.y));
			stichZ�hler += 1;
		}

		//falls es keine zwischenschritte gab, direkt den Endpunkt einzeichnen
		if (anzahlStiche == 1) {
			ausgabe.put(EndcodeTajimaStitch.encodeDST((long) endPunkt.x - (long) anfangsPunkt.x,
					(long) endPunkt.y - (long) anfangsPunkt.y));
		}else {
			
		ausgabe.put(EndcodeTajimaStitch.encodeDST((int) endPunkt.x - (int) aktuellerPunkt.x,
				(long) endPunkt.y - (long) aktuellerPunkt.y));
		}
		
		ByteBuffer ausgabe2 = ByteBuffer.allocate((stichZ�hler*3)+3); // TODO noch schauen, das der Buffer nicht zu
		ausgabe.flip();
		ausgabe2.put(ausgabe);																// gro� ist, sonst gibt es zu viele stellen.
		
		
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
		return zahl*zahl;
	}

}
