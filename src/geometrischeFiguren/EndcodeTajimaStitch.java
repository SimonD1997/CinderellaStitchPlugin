/* ------------------------------------------------------------------
# originaly from stitchcode.py
# see: https://github.com/backface/stitchcode/blob/f56cf76ee49dfae4e3cd503e7d914cc50d67f893/stitchcode.py
# ------------------------------------------------------------------
# # This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public License
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.s
*/

package geometrischeFiguren;

/**
 * Berechnet den Stichcode anhand von x und y Bewegungen. Dürfen maximal
 * zwischen -121 und +121 liegen
 * 
 * @author Simon Doubleday
 *
 */
public class EndcodeTajimaStitch {

	/**
	 * Berechnet einen 'normalen' Stich
	 * 
	 * @param dx Bewegung in x-Richtung zwischen -121 und + 121
	 * @param dy Bewegung in y-Richtung zwischen -121 und + 121
	 * @return Stitch Code in 3 Bytes gespeichert in einem ByteArray
	 */
	public static byte[] encodeDST(long dx, long dy) {
		return encodeDST(dx, dy, false);
	}

	/**
	 * Berechnet einen Stich. Es kann zwischen Jump Stich und 'normalem' Stich
	 * gewählt werden.
	 * 
	 * @param dx   Bewegung in x-Richtung zwischen -121 und + 121
	 * @param dy   Bewegung in y-Richtung zwischen -121 und + 121
	 * @param jump true falls ein Jump Stitch ausgegeben werden soll
	 * @return Stitch Code in 3 Bytes gespeichert in einem ByteArray
	 */
	public static byte[] encodeDST(long dx, long dy, boolean jump) {

		byte[] byteArray = { 0, 0, 0 };
		byte b1 = 0;
		byte b2 = 0;
		byte b3 = 0;

		if (dx > 40) {
			b3 += 0x04;
			dx -= 81;
		}
		if (dx < -40) {
			b3 += 0x08;
			dx += 81;
		}
		if (dy > 40) {
			b3 += 0x20;
			dy -= 81;
		}
		if (dy < -40) {
			b3 += 0x10;
			dy += 81;
		}
		if (dx > 13) {
			b2 += 0x04;
			dx -= 27;
		}
		if (dx < -13) {
			b2 += 0x08;
			dx += 27;
		}
		if (dy > 13) {
			b2 += 0x20;
			dy -= 27;
		}
		if (dy < -13) {
			b2 += 0x10;
			dy += 27;
		}
		if (dx > 4) {
			b1 += 0x04;
			dx -= 9;
		}
		if (dx < -4) {
			b1 += 0x08;
			dx += 9;
		}
		if (dy > 4) {
			b1 += 0x20;
			dy -= 9;
		}
		if (dy < -4) {
			b1 += 0x10;
			dy += 9;
		}
		if (dx > 1) {
			b2 += 0x01;
			dx -= 3;
		}
		if (dx < -1) {
			b2 += 0x02;
			dx += 3;
		}
		if (dy > 1) {
			b2 += 0x80;
			dy -= 3;
		}
		if (dy < -1) {
			b2 += 0x40;
			dy += 3;
		}
		if (dx > 0) {
			b1 += 0x01;
			dx -= 1;
		}
		if (dx < 0) {
			b1 += 0x02;
			dx += 1;
		}
		if (dy > 0) {
			b1 += 0x80;
			dy -= 1;
		}
		if (dy < 0) {
			b1 += 0x40;
			dy += 1;
		}

		byteArray[0] = b1;
		byteArray[1] = b2;
		byteArray[2] = b3;

		// Verändert den Bit der den Jump Stitch ansagt
		if (jump == true) {
			byteArray[2] += (byte) 0b10000011;
			// b3 += 0x83;
			// s += b3;

		} else {
			byteArray[2] += (byte) 0b00000011;
			// b3 += 0x03;
			// s += b3;
		}

		return byteArray;

	}

}
