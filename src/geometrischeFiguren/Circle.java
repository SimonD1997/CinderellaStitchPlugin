package geometrischeFiguren;

import java.nio.ByteBuffer;

public class Circle {

	String name;
	private double radius;
	private Point mittelpunkt = new Point("", 0, 0);

	Point anfangsPunkt;
	Point endPunkt;

	public Circle(String name, double[] mittelpunkt, double radius) {
		this.radius = radius;
		this.mittelpunkt.setName(name+ "Mittelpunkt");
		this.mittelpunkt.setX(mittelpunkt[0]);
		this.mittelpunkt.setY(mittelpunkt[1]);
		this.name = name;
		anfangsPunkt = kreisGleichung(0);

	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setMittelpunkt(Point mittelpunkt) {
		this.mittelpunkt = mittelpunkt;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRadius() {
		return this.radius;
	}

	public Point getMittelpunkt() {
		return this.mittelpunkt;
	}
	
	public Point getEndPunkt() {
		return this.endPunkt;
	}

	public String getName() {
		return this.name;
	}

	public ByteBuffer stitchCode(Point punktDavor, double aufloesung) {

		// Berechnen von zischenstichen. 5-7 mm 2,5 - 3.0 mm
		// brother max 5mm
		// best 1,5mm
		// double aufloesung = 1;
		int abstaende = (int)(10*aufloesung); // umgerechnet in nadelbewegungen mit 0,1mm

		int anzahlStiche = (int) (umfang() / abstaende); // aufgerundet, da sonst ein stich unter minimalabstand fällt
		int stichZaehler = 0;

		ByteBuffer ausgabe = ByteBuffer.allocate(anzahlStiche * 3 + 9); // TODO noch schauen, das der Buffer nicht zu
		// groß ist, sonst gibt es zu viele stellen.

		long bildWechselX = (long) anfangsPunkt.x - (long) punktDavor.x;
		long bildWechselY = (long) anfangsPunkt.y - (long) punktDavor.y;

		// Bildwechsel zur nächsten Form mithilfe eines oder mehrere Sprungstiche
		// führt zu Fehlen, da mehrere Sprungstiche als TRIM Command interpretiert werden könnten
		while (bildWechselX > 121 || bildWechselX < -121 || bildWechselY > 121 || bildWechselY < -121) {
			
			if (bildWechselX > 121) {
				ausgabe.put(EndcodeTajimaStitch.encodeDST( 121, 0, true));
				bildWechselX =- 121;
			}else if (bildWechselX < -121) {
				ausgabe.put(EndcodeTajimaStitch.encodeDST( -121, 0, true));
				bildWechselX =+ 121;
			} else if (bildWechselY > 121) {
				ausgabe.put(EndcodeTajimaStitch.encodeDST(0, 121, true));
				bildWechselY =- 121;
			} else if (bildWechselY < -121) {
				ausgabe.put(EndcodeTajimaStitch.encodeDST( 0, -121, true));
				bildWechselY =+ 121;
			} 
			
			stichZaehler += 1;
			
		}

		ausgabe.put(EndcodeTajimaStitch.encodeDST(bildWechselX, bildWechselY, true));

		ausgabe.put(EndcodeTajimaStitch.encodeDST(0, 0));
		stichZaehler += 2;
		
		

		Point aktuellerPunkt = anfangsPunkt;
		Point naechsterPunkt = anfangsPunkt;

		// zwischenschritte einzeichnen
		for (int i = 1; i < anzahlStiche; i++) {

			naechsterPunkt = kreisGleichung(((2*Math.PI) / anzahlStiche) * i);

			ausgabe.put(EndcodeTajimaStitch.encodeDST((long) naechsterPunkt.x - (long) aktuellerPunkt.x,
					(long) naechsterPunkt.y - (long) aktuellerPunkt.y));
			stichZaehler += 1;

			aktuellerPunkt = naechsterPunkt;
		}

		endPunkt = naechsterPunkt;

		// falls es keine zwischenschritte gab, direkt den Endpunkt einzeichnen
		if (anzahlStiche == 1) {

		} else {

			ausgabe.put(EndcodeTajimaStitch.encodeDST((int) anfangsPunkt.x - (int) aktuellerPunkt.x,
					(long) anfangsPunkt.y - (long) aktuellerPunkt.y));
			stichZaehler += 1;

		}

		ByteBuffer ausgabe2 = ByteBuffer.allocate((stichZaehler * 3)); // TODO noch schauen, das der Buffer nicht zu
		ausgabe.flip();
		ausgabe2.put(ausgabe); // groß ist, sonst gibt es zu viele stellen.

		return (ausgabe);
	}

	/**
	 * 
	 * @param neuX
	 * @return
	 */
	private Point kreisGleichung(double winkel) {
		double neuX = radius * Math.cos(winkel) + mittelpunkt.getX();
		double neuY = radius * Math.sin(winkel) + mittelpunkt.getY();
		Point point = new Point(this.name + winkel, neuX, neuY);
		//System.out.println(neuX + "|" +neuY);
		return point;
	}

	/**
	 * 
	 * @return
	 */
	private double umfang() {
		return 2 * radius * Math.PI;
	}

}
