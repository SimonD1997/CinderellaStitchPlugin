/* ------------------------------------------------------------------
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public License
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.s
# ------------------------------------------------------------------
*/

package geometrischeFiguren;

import java.nio.ByteBuffer;

/**
 * Alle Strecken aus Cinderella sollen durch diese Klasse instaziiert werden.
 * Deffiniert ist eine Strecke durch Anfangs und Endpunnkt als 'Point'.
 * 
 * @author Simon Doubleday
 *
 */
public class Segment {

	Point anfangsPunkt;
	Point endPunkt;

	String name;

	/**
	 * Constructor
	 * 
	 * @param name         Name der Strecke
	 * @param anfangsPunkt Punkt als 'Point'
	 * @param endPunkt     Punkt als 'Point'
	 */
	public Segment(String name, Point anfangsPunkt, Point endPunkt) {
		this.anfangsPunkt = anfangsPunkt;
		this.endPunkt = endPunkt;
		this.name = name;

	}

	// Getter und Setter
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
	 * Gibt den Stichcode der Strecke aus. 
	 * 
	 * @param punktDavor der vorige endpunkt des letzten Objekts
	 * @param aufloesung in welchem Verhältnis alles ausgegeben wird.
	 * @return Stichcode der Strecke mit Zwischenstichen
	 */
	public ByteBuffer stitchCode(Point punktDavor, double aufloesung) {

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
			long bildWechselX = (long) anfangsPunkt.x - (long) punktDavor.x;
			long bildWechselY = (long) anfangsPunkt.y - (long) punktDavor.y;

			
			//TODO Zwischenschritte einzeichnen um den TRIM-Command zu umgehen
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

		// Richtungsvektor
		long endpunktX = (long) endPunkt.x;
		long endpunktY = (long) endPunkt.y;
		long anfangspunktX = (long) anfangsPunkt.x;
		long anfangspunktY = (long) anfangsPunkt.y;

		long vekX = (endpunktX - anfangspunktX);
		long vekY = (endpunktY - anfangspunktY);

		long aktuellerPunktX = anfangspunktX;
		long aktuellerPunktY = anfangspunktY;

		// Zwischnenschritte als Punkte berechnen und dann dazwischen die Vektoren
		// Mininimert möglicherweise etwas die rundungsfehler des Ortsvektors
		// zwischenschritte einzeichnen
		for (int i = 1; i < anzahlStiche; i++) {

			ausgabe.put(EndcodeTajimaStitch.encodeDST((1 / anzahlStiche) * vekX, (1 / anzahlStiche) * vekY));
			aktuellerPunktX = (long) (anfangspunktX + ((double) (i / anzahlStiche) * vekX));
			aktuellerPunktY = (long) (anfangspunktY + ((double) (i / anzahlStiche) * vekY));
			stichZaehler += 1;
		}

		if (anzahlStiche == 0) {
			ausgabe.put(EndcodeTajimaStitch.encodeDST(vekX, vekY, true));

			stichZaehler += 1;

			// falls es keine zwischenschritte gab, direkt den Endpunkt einzeichnen
		} else {
			ausgabe.put(EndcodeTajimaStitch.encodeDST(endpunktX - aktuellerPunktX, endpunktY - aktuellerPunktY));
			stichZaehler += 1;
		}

		ByteBuffer ausgabe2 = ByteBuffer.allocate((stichZaehler * 3) + 3);
		ausgabe.flip(); // verhindert, dass die restlichen stellen des unbeschriebenen buffer als null
						// werte ausgegeben werden.
		ausgabe2.put(ausgabe);

		return (ausgabe);
	}

	/**
	 * Berechnet die Länge der Strecke
	 * 
	 * @return laenge der Strecke
	 */
	private double laenge() {
		return Math
				.sqrt((square(this.endPunkt.x - this.anfangsPunkt.x) + square(this.endPunkt.y - this.anfangsPunkt.y)));
	}

	/**
	 * 
	 * @param zahl in double
	 * @return quadratzahl der eingabe zahl
	 */
	private double square(double zahl) {
		return zahl * zahl;
	}

}
