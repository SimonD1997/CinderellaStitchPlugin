import de.cinderella.api.cs.CindyScript;
import de.cinderella.api.cs.CindyScriptPlugin;




public class StitchPlugin extends CindyScriptPlugin {

    public String getName() {
        return "StitchPlugin";
    }

    public String getAuthor() {
        return "Simon Doubleday";
    }

    @CindyScript("getGeometryData")
    public void getGeometryData(String forms) {
    	
    	
        
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
