import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


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

    @CindyScript("getGeometryData")
    public void getGeometryData(String forms) {
    	
    	
        
    }
    
    @CindyScript("getScreenbound")
    public String getScreenbound(ArrayList<Double[]> forms) {
    	
    	String ausgabe = "";
    	
    	for (Double[] doubles : forms) {
    		Double summe = 0d;
    		
			for (Double doubles2 : doubles) {
				summe += doubles2;
			}
			
			ausgabe = ausgabe +   summe.toString() + " ; " ;
		};
        
		
		return ausgabe;
    }
    
    
    /**
     * Möglicherweise alles notwenidge Programm mithilfe des "import(loadProgramm())" in Cinderella laden lassen. 
     * Herhöht Komfort für Anwender.
     * @return
     */
    @CindyScript("loadProgramm")
    public String loadProgramm() {
    	
    	
        return ("");
    }


}
