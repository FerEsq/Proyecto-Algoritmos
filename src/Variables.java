import java.util.HashMap;

/**
 * 
 */

/**
 * @author Andres
 *
 */
public class Variables {
	public static HashMap<String, Double> vars = new HashMap<String, Double>();
	/**
	 * Creates a new variable
	 * @param name name of the variable
	 * @param value value of the variable
	 */
	public void add(String name,Double value){
		boolean exists = false;
        //verify if the variable exists
        for (String i : vars.keySet()) {
            if(i.equals(name)) {//if exists
            	exists=true;
            	vars.remove(name);//variable gets replaced
            	vars.put(name, value);
            }
        }
        if(!exists) {//if not exists, creates a new one
        	vars.put(name, value);
        }

	}
	/**
	 * Diego's secret function
	 */
	public void dgo() {
		vars.put("diego", 69.0);
	}
	/**
	 * 
	 * @param name name of the variable
	 * @return the value of the variable
	 */
	public Double getVar(String name){		
		return vars.get(name);
	}
}
