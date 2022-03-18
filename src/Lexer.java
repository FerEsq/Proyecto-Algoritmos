/**
 * 
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Andres
 *
 */
public class Lexer {
	/*********************PATTERNS************************************************************/
	Pattern pvariable = Pattern.compile("setq[ ]+[a-z][a-z0-9_]*[ ]+[0-9*+\\-/ ]+",Pattern.CASE_INSENSITIVE);
	Pattern pStringprint = Pattern.compile("print[ ]+[\"][a-z0-9]+[\"]",Pattern.CASE_INSENSITIVE);
	Pattern pVarprint = Pattern.compile("print[ ]+[a-z][a-z0-9_]*",Pattern.CASE_INSENSITIVE);
	Pattern pIntprint = Pattern.compile("print[ ]+[0-9]+",Pattern.CASE_INSENSITIVE);
	/*****************************************************************************************/
	Variables variables = new Variables();
	Arithmetics arithmetics = new Arithmetics();
	
	/**
	 * 
	 * @param expression expression to evaluate
	 * @return true if the expression is correct
	 * @throws InterpreterException exception of the interpreter
	 */
	public boolean general_evaluation(String expression) throws InterpreterException{
		boolean result=false;
		boolean parenthesis=false;
		//evaluate if parenthesis exist 
		if(expression.length()!=0) {
			if(expression.trim().charAt(0)=='('&&expression.trim().endsWith(")")) {
				result=true;
			}else {
				result = false;
				throw new InterpreterException("Parenthesis missing");
			}
			//evaluate if the pair of parenthesis are complete
			int incomplete_pairs=0;
			for(int i=0; i<expression.length();i++) {
				if(expression.charAt(i)=='(') {
					parenthesis=true;
					incomplete_pairs++;
				}else if(expression.charAt(i)==')'){
					parenthesis=true;
					incomplete_pairs--;
				}
			}
			if(incomplete_pairs==0&&parenthesis) {//pairs are complete
				result=true;
			}else if (incomplete_pairs>0) { //there's more opening parenthesis than closing ones
				result = false;
				throw new InterpreterException("Closing Parenthesis missing");
			}else if (incomplete_pairs<0) {//there's more closing parenthesis than opening ones
				result = false;
				throw new InterpreterException("Opening Parenthesis missing");
			}
			Pattern pattern = Pattern.compile("^[(][(]*[ ]*[)]*[)]$",Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(expression);
			if(matcher.find()) {
				result = false;
				throw new InterpreterException("Empty expression");
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param expression expression to evaluate
	 * @return the result of the expression
	 * @throws InterpreterException
	 */
	public String evaluate(String expression) throws InterpreterException{
		String result="";
		String[] splitedExpression = expression.split("[\\(||//)]");//split the expression between parenthesis
		splitedExpression = clean_array(splitedExpression);//clean the null slots of the array
		for(int i=0;i<splitedExpression.length;i++) {
			String[] tokens =splitedExpression[i].split(" ");//separates the clean expression in tokens
			if(pvariable.matcher(splitedExpression[i]).find()) {//matches a variable
				if(tokens[2].contains("*")||tokens[2].contains("/")||tokens[2].contains("-")||tokens[2].contains("+")) {
					String ope="";
					for(int j =2;j<tokens.length;j++) { //repairs the splitted operation
						if(j ==tokens.length-1) {
							ope+=tokens[j];
						}else {
							ope+=tokens[j]+" ";
						}
					}
					variables.add(tokens[1],(arithmetics.prefixcalc(ope)));//makes the operation and saves it into a variable
				}else {
					variables.add(tokens[1],Double.parseDouble((tokens[2])));//saves the variable
				}
			}
			else if(pVarprint.matcher(splitedExpression[i]).find()) {//matches a variable to print
				Double value=variables.getVar(tokens[1]);
				if(value==null)//if the variable doesn't exist
					throw new InterpreterException("Variable "+ tokens[1]+" not found");
				else
					result = value+"";
			}
			else if(pStringprint.matcher(splitedExpression[i]).find()) {//matches a string to print
				String string = tokens[1].replace("\"", "");//removes quotation marks
				result = string;
			}
			else if(pIntprint.matcher(splitedExpression[i]).find()) {//matches an int to print
				result = tokens[1];
			}
			else {//doesn't match a lisp expression
				throw new InterpreterException("Syntax error");
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param array array to be cleaned
	 * @return cleaned array
	 */
	private String[] clean_array(String[] array) {
		int emptycount=0;
		for(int i=0;i<array.length;i++) {//counts the null entries on the array
			if(array[i].equals(""))
				emptycount++;
		}
		String[] newArray = new String[array.length-emptycount];//creates a new array without counting the null entries
		int newarraycount =0;
		for(int i=0;i<array.length;i++) {//copies the not null entries to the new array
			if(!array[i].equals("")) {
				newArray[newarraycount]=array[i];
				newarraycount++;
			}		
		}
		return newArray;//return the clean array
	}
}
