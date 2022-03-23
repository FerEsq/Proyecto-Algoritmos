import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Class Functions
 * 
 * Clase que maneja las funciones del interprete
 * 
 * @version 1.0, 21/03/2022
 * 
 * @author 
 * Andres E. Montoya W. - 21552
 * Diego E. Lemus L. - 21469
 * Fernanda Esquivel - 21542
 */

public class Functions {
	HashMap<String, String> functions = new HashMap<String, String>();
	HashMap<String, LinkedHashMap<String, String>> functionParameters = new HashMap<String, LinkedHashMap<String, String>>();
	/**
	 * Function that creates a new function
	 * @param name name of the function
	 * @param parameters parameters of the function (x,y,z)
	 * @param instructions the body of the function
	 */
	public void newFunction(String name, String parameters, String[] instructions) {
		LinkedHashMap<String, String> parametersMap = new LinkedHashMap<String, String>();
		String trim_param = parameters.trim();
		String[] param_array = trim_param.split(",");
		for(int i =0;i<param_array.length;i++) {//saves the parameters names
			parametersMap.put(param_array[i], "");
		}
		String instructionString ="";
		for (int i=0;i<instructions.length;i++) {//saves the name of the function with the instructions
			instructionString += "("+instructions[i]+")";
		}
		functionParameters.put(name, parametersMap);
		functions.put(name, instructionString);
	}
	/**
	 * Function that returns the instructions of the function called
	 * @param name name of the function to call
	 * @param parameters value of the parameters
	 * @return the lexed instructions that the evaluation can understand
	 * @throws InterpreterException 
	 */
	public String functionCall(String name, String parameters) throws InterpreterException{
		String lexedInstructions="";
		String[] param_array = parameters.split("[ ]*[,]+[ ]*");
		if(functions.containsKey(name)) {
			String instructionString = functions.get(name);
			LinkedHashMap<String, String> parametersMap = functionParameters.get(name);
			if (param_array.length == parametersMap.size()) {//check if the parameters are the same
				int cont=0;
				for (String i : parametersMap.keySet()) {//matches the value of the parameters with the name
					parametersMap.put(i, param_array[cont]);
					cont++;
				}
				for (String i : parametersMap.keySet()) {//translates the expression replacing the parameters with their value
					instructionString= instructionString.replace(i, parametersMap.get(i));
				}
				lexedInstructions = instructionString;
			}else {
				throw new InterpreterException("Missing parameters");
			}
		}else {
			throw new InterpreterException("Function "+name+" not found");
		}
		return lexedInstructions;
	}
}
