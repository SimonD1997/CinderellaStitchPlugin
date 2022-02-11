import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CinderellaStitchPlugin test = new CinderellaStitchPlugin();
		
		Double[] testArray1 = {2d,5d}; 
		Double[] testArray2 = {2d,4d};
		
		ArrayList<Double[]> testArrayList = new ArrayList<Double[]>();
		testArrayList.add(testArray1);
		testArrayList.add(testArray2);
		
		
		String ausgabe = test.getScreenbound(testArrayList);
		System.out.println(ausgabe);

	}

}
