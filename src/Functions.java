import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * 
 */

/**
 * @author Andres
 *
 */
public class Functions {
	HashMap<String, String> functions = new HashMap<String, String>();
	HashMap<String, LinkedHashMap<String, String>> functionParameters = new HashMap<String, LinkedHashMap<String, String>>();
	public void newFunction(String name, String parameters, String[] instructions) {
		LinkedHashMap<String, String> parametersMap = new LinkedHashMap<String, String>();
		String trim_param = parameters.trim();
		String[] param_array = trim_param.split(",");
		for(int i =0;i<param_array.length;i++) {
			parametersMap.put(param_array[i], "");
		}
		String instructionString ="";
		for (int i=0;i<instructions.length;i++) {
			instructionString += "("+instructions[i]+")";
		}
		functionParameters.put(name, parametersMap);
		functions.put(name, instructionString);
	}
	public String functionCall(String name, String parameters) throws InterpreterException{
		String lexedInstructions="";
		String[] param_array = parameters.split("[ ]*[,]+[ ]*");
		if(functions.containsKey(name)) {
			String instructionString = functions.get(name);
			LinkedHashMap<String, String> parametersMap = functionParameters.get(name);
			if (param_array.length == parametersMap.size()) {
				int cont=0;
				for (String i : parametersMap.keySet()) {
					parametersMap.put(i, param_array[cont]);
					cont++;
				}
				for (String i : parametersMap.keySet()) {
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
