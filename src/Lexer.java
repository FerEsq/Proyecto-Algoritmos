import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Lexer 
 * 
 * Clase lexer del interprete
 * 
 * @version 1.8, 22/03/2022
 * 
 * @author 
 * Andres E. Montoya W. - 21552
 * Diego E. Lemus L. - 21469
 * Fernanda Esquivel - 21542
 */

public class Lexer {
	/*********************PATTERNS************************************************************/
	//variable creation
	Pattern pvariable = Pattern.compile("^setq[ ]+[a-z][a-z0-9_]*[ ]+[0-9*+\\-/ ]+",Pattern.CASE_INSENSITIVE);
	//string printing
	Pattern pStringprint = Pattern.compile("^print[ ]+[\"][a-z0-9]+[\"]",Pattern.CASE_INSENSITIVE);
	//var printing
	Pattern pVarprint = Pattern.compile("^print[ ]+[a-z][a-z0-9_]*",Pattern.CASE_INSENSITIVE);
	//int printing
	Pattern pIntprint = Pattern.compile("^print[ ]+[0-9*+\\-/ ]+",Pattern.CASE_INSENSITIVE);
	//quote
	Pattern pQuote = Pattern.compile("^'|^quote",Pattern.CASE_INSENSITIVE);
	//condition greater
	Pattern pGreater = Pattern.compile("^[>][ ]+[[0-9]|[a-z]]+[ ]+[[0-9]|[a-z]]+",Pattern.CASE_INSENSITIVE);
	//condition smaller
	Pattern pSmaller = Pattern.compile("^[<][ ]+[[0-9]|[a-z]]+[ ]+[[0-9]|[a-z]]+",Pattern.CASE_INSENSITIVE);
	//condition equal
	Pattern pEqual = Pattern.compile("^equal[ ]+[[0-9]|[a-z]]+[ ]+[[0-9]|[a-z]]+",Pattern.CASE_INSENSITIVE);
	//atom
	Pattern pAtom = Pattern.compile("^atom",Pattern.CASE_INSENSITIVE);
	//list
	Pattern pList = Pattern.compile("^list",Pattern.CASE_INSENSITIVE);
	//condition
	Pattern pCond = Pattern.compile("^cond",Pattern.CASE_INSENSITIVE);
	//define function
	Pattern pDefun = Pattern.compile("^defun",Pattern.CASE_INSENSITIVE);
	//call function
	Pattern pCallfun = Pattern.compile("^[a-z][a-z0-9_]*",Pattern.CASE_INSENSITIVE);
	//arithmetics operations
	Pattern pArithmetics = Pattern.compile("^[*+\\-/][0-9*+\\-/ ]*",Pattern.CASE_INSENSITIVE);
	/*****************************************************************************************/
	
	/*******************PARAMETERS*******************/
	Variables variables = new Variables();
	Arithmetics arithmetics = new Arithmetics();
	Predicates predicates = new Predicates();
	Functions functions = new Functions();
	/************************************************/
	
	/**
	 * General evaluation to find syntax errors
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
	 * Evaluation of expressions
	 * @param expression expression to evaluate
	 * @return the result of the expression
	 * @throws InterpreterException
	 */
	public String evaluate(String expression) throws InterpreterException{
		String result="";
		try {
			boolean isfunction=true;
			String[] splitedExpression = expression.split("[\\(||\\)]");//split the expression between parenthesis
			splitedExpression = clean_array(splitedExpression);//clean the null slots of the array
			/**
			 * Defun expression found
			 */
			if(pDefun.matcher(splitedExpression[0]).find()) {
				String[] instructions = new String[splitedExpression.length-2];
				String parameters = splitedExpression[1];
				for(int i=2;i<splitedExpression.length;i++) {
					instructions[i-2]=splitedExpression[i];
				}
				String[] funct_name = splitedExpression[0].split(" ");
				functions.newFunction(funct_name[1], parameters, instructions);//creates new function with the parameters and instructions sent
			}
			else {
				for(int i=0;i<splitedExpression.length;i++) {// cycle of expressions
					String[] tokens =splitedExpression[i].split(" ");//separates the clean expression in tokens
					/****************************
					 * Matches a variable
					 */
					if(pvariable.matcher(splitedExpression[i]).find()) {//matches a variable
						isfunction=false;
						if(tokens[2].contains("*")||tokens[2].contains("/")||tokens[2].contains("-")||tokens[2].contains("+")) {
							String ope="";
							for(int j =2;j<tokens.length;j++) { //repairs the splitted operation
								if(j ==tokens.length-1) {
									ope+=tokens[j];
								}else {
									ope+=tokens[j]+" ";
								}
							}
							for(int j=2;j<tokens.length;j++) {
								if(variables.isVar(tokens[j])) {
									ope=ope.replace(tokens[j], variables.getVar(tokens[j]).intValue()+"");
								}
							}
							variables.add(tokens[1],(arithmetics.prefixcalc(ope)));//makes the operation and saves it into a variable
						}else {
							variables.add(tokens[1],Double.parseDouble((tokens[2])));//saves the variable
						}
					}
					/****************************
					 * Matches print of a variable
					 */
					else if(pVarprint.matcher(splitedExpression[i]).find()) {//matches a variable to print
						isfunction=false;
						result+="-> ";
						Double value=variables.getVar(tokens[1]);
						result+= value+""+"\n";
					}
					/****************************
					 * Matches print of a string
					 */
					else if(pStringprint.matcher(splitedExpression[i]).find()) {//matches a string to print
						isfunction=false;
						result+="-> ";
						String string = tokens[1].replace("\"", "");//removes quotation marks
						if(tokens.length>1) {
							for (int j=2;j<tokens.length;j++) {
								string +=" "+tokens[j];
							}
						}
						result+= string+"\n";
					}
					/****************************
					 * Matches a quote
					 */
					else if(pQuote.matcher(splitedExpression[i]).find()) {//matches a string to print
						isfunction=false;
						result+="-> ";
						String string = tokens[1].replace("'","");//removes quotation marks
						if(tokens.length>1) {
							for (int j=2;j<tokens.length;j++) {
								string +=" "+tokens[j];
							}
						}
						result+= string+"\n";
					}
					/****************************
					 * Matches print of an int or an arithmetic operation
					 */
					else if(pIntprint.matcher(splitedExpression[i]).find()) {//matches an int to print
						isfunction=false;
						result+="-> ";
						if(tokens[1].contains("*")||tokens[1].contains("/")||tokens[1].contains("-")||tokens[1].contains("+")) {
							String ope="";
							for(int j =1;j<tokens.length;j++) { //repairs the splitted operation
								if(j ==tokens.length-1) {
									ope+=tokens[j];
								}else {
									ope+=tokens[j]+" ";
								}
							}
							for(int j=1;j<tokens.length;j++) {
								if(variables.isVar(tokens[j])) {
									ope=ope.replace(tokens[j],variables.getVar(tokens[j]).intValue()+"");
								}
							}
							result+= arithmetics.prefixcalc(ope)+"\n";
						}else {
							result+= tokens[1]+"\n";
						}
					}
					/****************************
					 * Matches the predicate greater than (only for print purposes)
					 */
					else if(pGreater.matcher(splitedExpression[i]).find()) {//matches an > to evaluate
						isfunction=false;
						result+="-> ";
						double v1=0;
						double v2=0;
						v1=varVal(tokens[1]);
						v2=varVal(tokens[2]);
						result+= predicates.graterThan(v1, v2)+"\n";
					}
					/****************************
					 * Matches the predicate smaller than (only for print purposes)
					 */
					else if(pSmaller.matcher(splitedExpression[i]).find()) {//matches an < to evaluate
						isfunction=false;
						result+="-> ";
						double v1=0;
						double v2=0;
						v1=varVal(tokens[1]);
						v2=varVal(tokens[2]);
						result+= predicates.smallerThan(v1, v2)+"\n";
					}
					/****************************
					 * Matches the predicate equal (only for print purposes)
					 */
					else if(pEqual.matcher(splitedExpression[i]).find()) {//matches an equal to evaluate
						isfunction=false;
						result+="-> ";
						double v1=0;
						double v2=0;
						v1=varVal(tokens[1]);
						v2=varVal(tokens[2]);
						result+= predicates.equalsTo(v1, v2)+"\n";
					}
					/****************************
					 * Matches the predicate atom (only for print purposes)
					 */
					else if(pAtom.matcher(splitedExpression[i]).find()) {//matches an atom to evaluate
						isfunction=false;
						result+="-> ";
						result+= predicates.atom(tokens)+"\n";
					}
					/****************************
					 * Matches the predicate list (only for print purposes)
					 */
					else if(pList.matcher(splitedExpression[i]).find()) {//matches a list to evaluate
						isfunction=false;
						result+="-> ";
						result+= predicates.list(tokens)+"\n";
					}
					/****************************
					 * Matches a condition
					 */
					else if(pCond.matcher(splitedExpression[i]).find()){//conditions
						isfunction=false;
						Boolean istrue= false;
						Boolean falsestate= false;
						Pattern pFalse = Pattern.compile("^t",Pattern.CASE_INSENSITIVE);//case of false
						/*if(splitedExpression.length>3) {
							throw new InterpreterException("Function 'cond' can only take one parameter for a true or false result");*/
						if(splitedExpression.length<3){
							throw new InterpreterException("Syntax error");
						}else {	
							if(splitedExpression.length>i+3) {//finds if there is a false state
								if(pFalse.matcher(splitedExpression[i+3]).find()) {
									falsestate = true;
								}
							}
							if(predEval(splitedExpression[i+1]).equals("T")) {//if the predicate is true
								i+=2;
								result+= evaluate(splitedExpression[i]);	//evaluates the true state
								istrue=true;
								if(falsestate) {
									i+=2;
								}
							}else if (!istrue&&predEval(splitedExpression[i+1]).equals("NIL")){//if predicate is false
								if(falsestate) {
									result += evaluate(splitedExpression[i+4]); // evaluates the false state
									i+=4;
								}else {
									i+=2;
								}				
							}else {
								i+=2;
							}
						}
					}
					/****************************
					 * Matches an arithmetic operation
					 */
					else if(pArithmetics.matcher(splitedExpression[i]).find()) {
						isfunction=false;
						result+="-> ";
						if(tokens[0].contains("*")||tokens[0].contains("/")||tokens[0].contains("-")||tokens[0].contains("+")) {
							String ope="";
							for(int j =0;j<tokens.length;j++) { //repairs the splitted operation
								if(j ==tokens.length-1) {
									ope+=tokens[j];
								}else {
									ope+=tokens[j]+" ";
								}
							}
							for(int j=0;j<tokens.length;j++) {
								if(variables.isVar(tokens[j])) {
									ope=ope.replace(tokens[j],variables.getVar(tokens[j]).intValue()+"");
								}
							}
							result+= arithmetics.prefixcalc(ope)+"\n";
						}else {
							result+= tokens[0]+"\n";
						}
					}
					/****************************
					 * Matches the call of a function
					 */
					else if(pCallfun.matcher(splitedExpression[i]).find()&&isfunction) {
						if (tokens.length==2) {
							String paramsString = tokens[1];
							String[] param = paramsString.split(",");
							for(int j=0; j<param.length;j++) {//replace the variables with their values
								if(variables.isVar(param[i])) {
									paramsString = paramsString.replace(param[i], variables.getVar(param[i])+"");
								}
							}
							String lexedInstruc =functions.functionCall(tokens[0],paramsString);//searches for the function and return the operations in it
							result +=evaluate(lexedInstruc);//evaluate the operations of the function
							i+=1;
						}else {
							throw new InterpreterException("Syntax error");
						}
					}
					else {//doesn't match a lisp expression
						throw new InterpreterException("Syntax error");
					}
				}
			}
		}catch (Exception e) {
			throw new InterpreterException("Syntax error");
		}
		return result;
	}
	
	/**
	 * Function that cleans arrays and return the cleaned array
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
	
	/**
	 * Function that evaluates a predicate and return T if the statement is true and NIL if is false
	 * @param pred predicate to evaluate
	 * @return the evaluation of the predicate
	 * @throws InterpreterException
	 */
	private String predEval(String pred) throws InterpreterException{
		/****************************
		 * All predicates for condition purposes
		 */
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
