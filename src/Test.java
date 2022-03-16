/* ------------------------------------------------------------------
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public License
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.s
# ------------------------------------------------------------------
*/
import java.nio.ByteBuffer;
import java.util.ArrayList;

import geometrischeFiguren.EndcodeTajimaStitch;
import geometrischeFiguren.Point;

/**
 * Testklasse zum Debuggen
 * @author Simon Doubleday
 *
 */
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CinderellaStitchPlugin test = new CinderellaStitchPlugin();
		
		//Double[] testArray1 = {2d,5d}; 
		//Double[] testArray2 = {2d,4d};
		
		//ArrayList<Double[]> testArrayList = new ArrayList<Double[]>();
		//testArrayList.add(testArray1);
		//testArrayList.add(testArray2);
		
		
		//String ausgabe = test.getScreenbound(testArrayList);
		//System.out.println(ausgabe);
		
		ArrayList<String[]> testArrayList2 = new ArrayList<String[]>();
		
		

		String[] testArray6 = {"F","2","1"}; 
		String[] testArray5 = {"E","13","15"};
		String[] testArray4 = {"D","21","7"}; 
		String[] testArray3 = {"M","15.8","-3.28"};
		String[] testArray2 = {"O","16.28","9.29"}; 
		String[] testArray1 = {"L","1.58","10.19"};
		testArrayList2.add(testArray1);
		testArrayList2.add(testArray2);
		testArrayList2.add(testArray3);
		testArrayList2.add(testArray4);
		testArrayList2.add(testArray5);
		testArrayList2.add(testArray6);
		test.getPoints(testArrayList2);
		
		System.out.println(test.punkteListe);
		for (Point s : test.punkteListe) {
			System.out.println(s.getName());
		}
		
		ArrayList<String[]> testArrayList3 = new ArrayList<String[]>();
		String[] testArray7 = {"h","Strecke(F;E)"};
		String[] testArray8 = {"d","Strecke(E;D)"};
		String[] testArray9 = {"e","Strecke(D;F)"};
		testArrayList3.add(testArray7);
		testArrayList3.add(testArray8);
		testArrayList3.add(testArray9);
		test.getSegments(testArrayList3);
		
		
		ArrayList<String[]> testArrayList4 = new ArrayList<String[]>();
		//String[] testArray10 = {"C","[0,0]","5"};
		//String[] testArray11 = {"C0","[3.52,3.31]","1.3"};
		//String[] testArray12 = {"C1","[5.35,0.96]","1.58"};
		
		//testArrayList4.add(testArray11);
		//testArrayList4.add(testArray12);
		
		test.getCircles(testArrayList4); 
		
		
		
		System.out.println(test.segementListe);
		
		test.startProgrammAusgabe();
		
		
		byte[] jumpTest1 = EndcodeTajimaStitch.encodeDST(0, 0, false);
		byte[] jumpTest2 = EndcodeTajimaStitch.encodeDST(20, 0, false);
		byte[] jumpTest3 = EndcodeTajimaStitch.encodeDST(20, 0, true);
		byte[] jumpTest4 = EndcodeTajimaStitch.encodeDST(0, 0, false);
		byte[] jumpTest5 = EndcodeTajimaStitch.encodeDST(20, 0, false);
		byte[] jumptTest = ByteBuffer.allocate(15).put(jumpTest1).put(jumpTest2).put(jumpTest3).put(jumpTest4).put(jumpTest5).array();
		ByteBuffer test5 = ByteBuffer.wrap(jumptTest);
		
		DateiAusgabe.dstDatei(test5);
		
		
		

	}

}
