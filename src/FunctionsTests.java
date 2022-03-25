import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FunctionsTests {

	// ----------------- Funcion print -----------------
	@Test
	void printFunctionTest() throws InterpreterException {
		Functions fnt = new Functions();
		
		String name = "fun";
		String parameter = "x";
		String[] instructions = {"print \"hola\""};
		fnt.newFunction(name, parameter, instructions);
		assertEquals("(print \"hola\")", fnt.functionCall(name, parameter));
	}
	
	// ----------------- Funcion suma -----------------
	@Test
	void sumaFunctionTest() throws InterpreterException {
		Functions fnt = new Functions();
		
		String name = "fun";
		String parameters = "x,y,z";
		String[] instructions = {"print + x y z"};
		fnt.newFunction(name, parameters, instructions);
		assertEquals("(print + x y z)", fnt.functionCall(name, parameters));
	}
	
	// ----------------- Funcion division -----------------
	@Test
	void divisionFunctionTest() throws InterpreterException {
		Functions fnt = new Functions();
		
		String name = "fun";
		String parameters = "x,y";
		String[] instructions = {"print / x y"};
		fnt.newFunction(name, parameters, instructions);
		assertEquals("(print / x y)", fnt.functionCall(name, parameters));
	}
	
	// ----------------- Funcion contador -----------------
	@Test
	void contadorFunctionTest() throws InterpreterException {
		Functions fnt = new Functions();
		
		String name = "fun";
		String parameter = "x";
		String[] instructions = {"setq contador + contador 1", "print contador", "cond (< contador x)(fun x)"};
		fnt.newFunction(name, parameter, instructions);
		assertEquals("(setq contador + contador 1)(print contador)(cond (< contador x)(fun x))", fnt.functionCall(name, parameter));
	}
	
	// ----------------- Funcion factorial -----------------
	@Test
	void factorialFunctionTest() throws InterpreterException {
		Functions fnt = new Functions();
			
		String name = "fun";
		String parameter = "x";
		String[] instructions = {"setq res * res cont", "setq cont + cont 1", "cond (> cont x)(print res)(t(factorial x)))"};
		fnt.newFunction(name, parameter, instructions);
		assertEquals("(setq res * res cont)(setq cont + cont 1)(cond (> cont x)(print res)(t(factorial x))))", fnt.functionCall(name, parameter));
	}
	
	@Test
	void divisionFunctionTest1() throws InterpreterException {
		Functions fnt = new Functions();
		
		String name = "fun";
		String parameters = "x";
		
		Throwable exception = assertThrows(InterpreterException.class, () -> fnt.functionCall(name, parameters));
	    assertEquals("Function fun not found", exception.getMessage());
	}
	
	@Test
	void divisionFunctionTest2() throws InterpreterException {
		Functions fnt = new Functions();
		
		String name = "fun";
		String parameters1 = "x,y";
		String parameters2 = "x";
		String[] instructions = {"print / x y"};
		fnt.newFunction(name, parameters1, instructions);
		
		Throwable exception = assertThrows(InterpreterException.class, () -> fnt.functionCall(name, parameters2));
	    assertEquals("Missing parameters", exception.getMessage());
	}
	
	@Test
	void divisionFunctionTest3() throws InterpreterException {
		Functions fnt = new Functions();
		
		String name = "fun";
		String name2 = "fer";
		String parameters = "x,y";
		String[] instructions = {"print / x y"};
		fnt.newFunction(name, parameters, instructions);
		
		Throwable exception = assertThrows(InterpreterException.class, () -> fnt.functionCall(name2, parameters));
	    assertEquals("Function fer not found", exception.getMessage());
	}
	
}





