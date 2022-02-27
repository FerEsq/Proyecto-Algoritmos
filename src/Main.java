/**
 * 
 */
import java.util.Scanner;
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
		Lexer lexer = new Lexer();
		boolean run = true;
		System.out.println("WELCOME TO THE LISP INTERPRETER \nTO LEAVE, WRITE \"leave\"");
		while(run) {
			String expressionString=sc.nextLine();
			if(expressionString.trim().toLowerCase().equals("leave")) {//leave
				run=false;
				System.out.println("Goodbye!");
			}else {
				boolean evaluation=false;
				try {
					evaluation = lexer.general_evaluation(expressionString);
				}catch (InterpreterException e) {
					System.out.println(e);					}
					if(evaluation) {
					//expression is correct
					System.out.println("Correct");
				}
			}
		}
	}
	
}
