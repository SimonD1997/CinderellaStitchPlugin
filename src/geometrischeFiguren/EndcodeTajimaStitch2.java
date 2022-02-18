package geometrischeFiguren;

public class EndcodeTajimaStitch2 {
	
	
	public static String encodeDST(int dx, int dy) {
		return encodeDST(dx, dy, false);
	}

	public static String encodeDST(int dx, int dy, boolean jump) {

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

		
		
		
		//b3 += 0x83;
		
		//char b4 = 0x89;
		//b4 += b3;
		String s = "" + b1;
		s += b2;
		
		if (jump= true) {
			b3 += 10000111;
			s += b3;
		}else {
			s += b3 + 00000011;
		}
		
		Byte b4 = (byte)0x83;
		s += String.format("%8s", Integer.toBinaryString(b4 & 0xFF)).replace(' ', '0');
		
		
		return s;

	}
	
	
}
