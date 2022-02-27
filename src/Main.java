/**
 * 
 */
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Andres
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println(evaluate(sc.nextLine()));
		}catch (InterpreterException e) {
			System.out.println(e);
		}
	}
	private static boolean evaluate(String expression) throws InterpreterException{
		boolean result=false;
		boolean parenthesis=false;
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
		if(incomplete_pairs==0&&parenthesis) {
			result=true;
		}else if (incomplete_pairs>0) {
			result = false;
			throw new InterpreterException("Closing Parenthesis missing");
		}else if (incomplete_pairs<0) {
			result = false;
			throw new InterpreterException("Opening Parenthesis missing");
		}
		
		if(expression.trim().charAt(0)=='('&&expression.trim().endsWith(")")) {
			result=true;
		}else {
			result = false;
			throw new InterpreterException("Parenthesis missing");
		}
		return result;
	}
}
