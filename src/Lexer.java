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
			Pattern pattern = Pattern.compile("^[(][ ]*[)]$",Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(expression);
			if(matcher.find()) {
				result = false;
				throw new InterpreterException("Empty expression");
			}
		}
		return result;
	}
}
