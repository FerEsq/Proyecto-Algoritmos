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
	Pattern pvariable = Pattern.compile("^setq[ ]+[a-z][a-z0-9_]*[ ]+[0-9*+\\-/ ]+",Pattern.CASE_INSENSITIVE);
	Pattern pStringprint = Pattern.compile("^print[ ]+[\"][a-z0-9]+[\"]",Pattern.CASE_INSENSITIVE);
	Pattern pVarprint = Pattern.compile("^print[ ]+[a-z][a-z0-9_]*",Pattern.CASE_INSENSITIVE);
	Pattern pIntprint = Pattern.compile("^print[ ]+[0-9*+\\-/ ]+",Pattern.CASE_INSENSITIVE);
	Pattern pQuote = Pattern.compile("^'|^quote",Pattern.CASE_INSENSITIVE);
	Pattern pGreater = Pattern.compile("^[>][ ]+[[0-9]|[a-z]]+[ ]+[[0-9]|[a-z]]+",Pattern.CASE_INSENSITIVE);
	Pattern pSmaller = Pattern.compile("^[<][ ]+[[0-9]|[a-z]]+[ ]+[[0-9]|[a-z]]+",Pattern.CASE_INSENSITIVE);
	Pattern pEqual = Pattern.compile("^equal[ ]+[[0-9]|[a-z]]+[ ]+[[0-9]|[a-z]]+",Pattern.CASE_INSENSITIVE);
	Pattern pAtom = Pattern.compile("^atom",Pattern.CASE_INSENSITIVE);
	Pattern pList = Pattern.compile("^list",Pattern.CASE_INSENSITIVE);
	Pattern pCond = Pattern.compile("^cond",Pattern.CASE_INSENSITIVE);
	/*****************************************************************************************/
	Variables variables = new Variables();
	Arithmetics arithmetics = new Arithmetics();
	Predicates predicates = new Predicates();
	
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
				result+="-> ";
				Double value=variables.getVar(tokens[1]);
				result+= value+""+"\n";
			}
			else if(pStringprint.matcher(splitedExpression[i]).find()) {//matches a string to print
				result+="-> ";
				String string = tokens[1].replace("\"", "");//removes quotation marks
				if(tokens.length>1) {
					for (int j=2;j<tokens.length;j++) {
						string +=" "+tokens[j];
					}
				}
				result+= string+"\n";
			}
			else if(pQuote.matcher(splitedExpression[i]).find()) {//matches a string to print
				result+="-> ";
				String string = tokens[1].replace("'","");//removes quotation marks
				if(tokens.length>1) {
					for (int j=2;j<tokens.length;j++) {
						string +=" "+tokens[j];
					}
				}
				result+= string+"\n";
			}
			else if(pIntprint.matcher(splitedExpression[i]).find()) {//matches an int to print
				result+="-> ";
				if(tokens[1].contains("*")||tokens[1].contains("/")||tokens[1].contains("-")||tokens[1].contains("+")) {
					String ope="";
					for(int j =2;j<tokens.length;j++) { //repairs the splitted operation
						if(j ==tokens.length-1) {
							ope+=tokens[j];
						}else {
							ope+=tokens[j]+" ";
						}
					}
					result+= arithmetics.prefixcalc(ope)+"\n";
				}else {
					result+= tokens[1]+"\n";
				}
			}
			else if(pGreater.matcher(splitedExpression[i]).find()) {//matches an > to evaluate
				result+="-> ";
				double v1=0;
				double v2=0;
				v1=varVal(tokens[1]);
				v2=varVal(tokens[2]);
				result+= predicates.graterThan(v1, v2)+"\n";
			}
			else if(pSmaller.matcher(splitedExpression[i]).find()) {//matches an < to evaluate
				result+="-> ";
				double v1=0;
				double v2=0;
				v1=varVal(tokens[1]);
				v2=varVal(tokens[2]);
				result+= predicates.smallerThan(v1, v2)+"\n";
			}
			else if(pEqual.matcher(splitedExpression[i]).find()) {//matches an equal to evaluate
				result+="-> ";
				double v1=0;
				double v2=0;
				v1=varVal(tokens[1]);
				v2=varVal(tokens[2]);
				result+= predicates.equalsTo(v1, v2)+"\n";
			}
			else if(pAtom.matcher(splitedExpression[i]).find()) {//matches an atom to evaluate
				result+="-> ";
				result+= predicates.atom(tokens)+"\n";
			}
			else if(pList.matcher(splitedExpression[i]).find()) {//matches a list to evaluate
				result+="-> ";
				result+= predicates.list(tokens)+"\n";
			}
			else if(pCond.matcher(splitedExpression[i]).find()){//conditions
				/*if(splitedExpression.length>3) {
					throw new InterpreterException("Function 'cond' can only take one parameter for a true or false result");*/
				if(splitedExpression.length<3){
					throw new InterpreterException("Syntax error");
				}else {	
					if(predEval(splitedExpression[i+1]).equals("T")) {
						i+=2;
						result+= evaluate(splitedExpression[i]);				
					/*}else if (splitedExpression.length==4&&evaluate(splitedExpression[1]).equals("NIL")){
						result += evaluate(splitedExpression[3]);*/
					}else {
						i+=2;
					}
				}
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
	private boolean isNumeric(String string) {
		try {
			Double.parseDouble(string);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}
	private double varVal(String string) throws InterpreterException{
		double val=0;
		if(isNumeric(string)) {
			val = Double.parseDouble(string);
		}
		if (!isNumeric(string)) {
			val = variables.getVar(string);
		}
		return val;
	}
	private String predEval(String pred) throws InterpreterException{
		String[] tokens =pred.split(" ");
		if(pGreater.matcher(pred).find()) {//matches an > to evaluate
			double v1=0;
			double v2=0;
			v1=varVal(tokens[1]);
			v2=varVal(tokens[2]);
			return predicates.graterThan(v1, v2);
		}
		else if(pSmaller.matcher(pred).find()) {//matches an < to evaluate
			double v1=0;
			double v2=0;
			v1=varVal(tokens[1]);
			v2=varVal(tokens[2]);
			return predicates.smallerThan(v1, v2);
		}
		else if(pEqual.matcher(pred).find()) {//matches an equal to evaluate
			double v1=0;
			double v2=0;
			v1=varVal(tokens[1]);
			v2=varVal(tokens[2]);
			return predicates.equalsTo(v1, v2);
		}
		else if(pAtom.matcher(pred).find()) {//matches an atom to evaluate
			return predicates.atom(tokens);
		}
		else if(pList.matcher(pred).find()) {//matches a list to evaluate
			return predicates.list(tokens);
		}else{
			return "NIL";
		}
	}
	
}
