import java.util.Scanner;

/**
 * Class Main
 * 
 * Clase driver del programa
 * 
 * @version 1.4, 20/03/2022
 * 
 * @author 
 * Andres E. Montoya W. - 21552
 * Diego E. Lemus L. - 21469
 * Fernanda Esquivel - 21542
 */

public class Main {

	/**
	 * Main
	 * @param args string[]
	 */
	public static void main(String[] args) {
		Variables var = new Variables();
		Scanner sc = new Scanner(System.in);
		Lexer lexer = new Lexer();
		var.dgo();
		boolean run = true;
		System.out.println("WELCOME TO THE LISP INTERPRETER \nTO LEAVE, WRITE \"leave\"");
		while(run) {//running process
			String expressionString=sc.nextLine();
			if(expressionString.trim().toLowerCase().equals("leave")) {//leave
				run=false;
				System.out.println("Goodbye!");
			}else {
				boolean evaluation=false;
				try {
					evaluation = lexer.general_evaluation(expressionString);//general evaluation of syntax
				}catch (InterpreterException e) {
					System.out.println(e);					
				}
				if(evaluation) {
					//expression is correct
					try {
						String outString =lexer.evaluate(expressionString);
						if(!outString.equals(""))
							System.out.println(outString);	//result of the expression
					}catch (InterpreterException e) {
						System.out.println(e);
					}
				}
			}
		}
	}
	
}
