import java.nio.ByteBuffer;
import java.util.ArrayList;

import geometrischeFiguren.EndcodeTajimaStitch;
import geometrischeFiguren.EndcodeTajimaStitch2;
import geometrischeFiguren.Point;

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
		
		

		String[] testArray6 = {"F","12.45","9.71"}; 
		String[] testArray5 = {"E","4.15","1.68"};
		String[] testArray4 = {"D","1.53","0.3"}; 
		String[] testArray3 = {"C","4","2"};
		String[] testArray2 = {"B","0","2"}; 
		String[] testArray1 = {"A","0","0"};
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
		String[] testArray7 = {"a","Strecke(A;B)"};
		String[] testArray8 = {"b","Strecke(B;C)"};
		String[] testArray9 = {"c","Strecke(D;E)"};
		testArrayList3.add(testArray7);
		testArrayList3.add(testArray8);
		testArrayList3.add(testArray9);
		test.getSegments(testArrayList3);
		
		//[[a,Strecke(A;B)]]
		//[B,1.92,4.76],[A,-4.2,2.28]
		//testArrayList = ({"P19,-1.68,7.08"},{"P18,-1.24,5.68"},{P17,-1.4,3.76},[P16,0.2,1.24],[P15,4.56,-0.2],[P14,7.28,-1.84],[P13,11.84,-0.88],[P12,13.76,0.6],[P11,14.12,3.48],[P10,13.72,6.16],[P9,12.52,7.88],[P8,9.88,8.24],[P7,7.36,8.44],[P6,0.76,8.32],[P5,-1.2,-0.2],[P4,-1.68,-0.32],[P3,-2.32,0.44],[P2,-2.52,4.28],[P1,4.52,8.48],[P0,9.6,6.48],[Z,11.2,4.96],[Y,11.52,1.48],[X,9.56,-0.16],[W,7.64,0.52],[V,4,1.84],[U,2.36,1.92],[T,3.6,0.64],[S,4.44,0.52],[R,6.48,2.04],[Q,6.8,3.04],[P,6.84,5.96],[O,6.84,6.68],[N,7.76,6.12],[M,7.92,5.64],[L,7.6,4.6],[K,7.36,1.56],[H,8.64,1.88],[G,9.72,2.6],[F,10.6,3.76],[E,12.72,3.44],[D,13.36,3.48],[C,4.44,4.76],[B,1.92,4.76],[A,-4.2,2.28])

		
		System.out.println(test.segementListe);
		
		test.startProgrammAusgabe();
		
		String test2;
		char charakter = 0x00;
		char space = 0x20;
		char end1 = 0x00;
		char end2 = 0x0D;
		test2 = "LA:turtlestitch" + space + space + space + end1 + end2;  
		test2 += "ST:3" + space + space + space + space + space + space + end2;//TODO noch automatisiern
		test2 += "CO:1" + space + space + end2;	
		test2 += "+X:90" + space + space + space + end2;//TODO noch automatisiern
		test2 += "-X:0" + space + space + space + space + end2;//TODO noch automatisiern
		test2 += "+Y:90"  + space + space + space + end2;//TODO noch automatisiern
		test2 += "-Y:0" + space + space + space + space + end2; //TODO noch automatisiern
		test2 += "AX:0" + space + space + space + space + space + end2;
		test2 += "AY:0" + space + space + space + space + space + end2;
		test2 += "MX:0" + space + space + space + space + space + end2;
		test2 += "MY:0" + space + space + space + space + space + end2;
		test2 += "PD:******" + end2;
		char headerEnd = 0x1a;
		test2 += "" + headerEnd + end1 + end1 + end1;
		
		for (int i = 0; i < 384; i++) {
			test2 += space;
		}
		
		test2 += "" + end1 + end1 + (char)0x03 + (char)0x09+ (char)0x04 + (char)0x07;
				
		test2 += "" + end1 + end1 + charakter;
	
		
		//System.out.println(test2);
		//DateiAusgabe.standardFileSave(test2);
		
		
		byte[] jumpTest1 = EndcodeTajimaStitch.encodeDST(0, 0, false);
		byte[] jumpTest2 = EndcodeTajimaStitch.encodeDST(20, 0, false);
		byte[] jumpTest3 = EndcodeTajimaStitch.encodeDST(20, 0, true);
		byte[] jumpTest4 = EndcodeTajimaStitch.encodeDST(0, 0, false);
		byte[] jumpTest5 = EndcodeTajimaStitch.encodeDST(20, 0, false);
		byte[] jumptTest = ByteBuffer.allocate(15).put(jumpTest1).put(jumpTest2).put(jumpTest3).put(jumpTest4).put(jumpTest5).array();
		ByteBuffer test5 = ByteBuffer.wrap(jumptTest);
		
		//DateiAusgabe.dstDatei(test5);
		
		
		

	}

}
