import java.util.*;

/**
 * 
 */

/**
 * @author Andres
 *
 */
public class Arithmetics {
	/**
	 * 
	 * @param c character to evaluate
	 * @return true if its an operator 
	 * https://algorithms.tutorialhorizon.com/evaluation-of-prefix-expressions-polish-notation-set-2/
	 * Modified by: Andrés Montoya
	 */
	private boolean isOp(char c) {
		if(c>=48&&c<=57) {
			return true;
		}else {
			return false;
		}
	}
	public double prefixcalc(String expression) throws InterpreterException{
		Stack<Double> stack = new Stack<Double>();
		for(int i =expression.length()-1;i>=0;i--) {
			if(expression.charAt(i)==' ') {
				continue;
			}
			if(isOp(expression.charAt(i))) {
				double num =0;
				double j =i;
				while(i<expression.length()&&isOp(expression.charAt(i))) {
					i--;
				}
				i++;
				for(int k=i;k<=j;k++) {
					num = num * 10 + (double)(expression.charAt(k)-'0');
				}
				stack.push(num);
			}else {
				double op1 = stack.peek();
				stack.pop();
				double op2 = stack.peek();
				stack.pop();
				switch (expression.charAt(i)) {
					case '+': 
						stack.push(op1+op2);
						break;
					case '-': 
						stack.push(op1-op2);
						break;
					case '*': 
						stack.push(op1*op2);
						break;
					case '/': 
						try {
							stack.push(op1/op2);
						}catch (Exception e) {
							throw new InterpreterException("Math error");
						}
						break;
				default:
					throw new InterpreterException("Arithmetic error");
				}
			}			
		}
		return stack.peek();
	}
	
}
