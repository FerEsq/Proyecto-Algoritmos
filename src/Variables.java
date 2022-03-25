import java.util.HashMap;

/**
 * Class Variables
 * 
 * Clase que maneja las variables del interprete
 * 
 * @version 1.3, 21/03/2022
 * 
 * @author 
 * Andres E. Montoya W. - 21552
 * Diego E. Lemus L. - 21469
 * Fernanda Esquivel - 21542
 */

public class Variables {
	
	/*******************PARAMETERS*******************/
	public static HashMap<String, Double> vars = new HashMap<String, Double>();
	
	/**
	 * Creates a new variable
	 * @param name name of the variable
	 * @param value value of the variable
	 */
	public void add(String name,Double value){
		vars.put(name, value);
	}
	
	/**
	 * Diego's secret function
	 */
	public void dgo() {
		vars.put("diego", 69.0);
	}
	
	/**
	 * Function that returns a specific variable
	 * @param name of the variable
	 * @return the value of the variable
	 */
	public Double getVar(String name)throws InterpreterException{	
		boolean exists = false;
        //verify if the variable exists
        for (String i : vars.keySet()) {
            if(i.equals(name)) {//if exists
            	exists=true;
            }
        }
        if(exists) {//if not exists, creates a new one
        	return vars.get(name);
        }else {
        	throw new InterpreterException("Variable "+ name + " not found");
        }
		
	}
	
	/**
	 * Function that returns true if the variable exists
	 * @param variable to find
	 * @return true if the variable exists
	 */
	public boolean isVar(String string) {
		boolean exists = false;
		for (String i : vars.keySet()) {
			if(i.equals(string)) {
				exists=true;
			}
		}
		return exists;
	}
}
