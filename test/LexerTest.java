import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LexerTest {

	// ----------------- Evaluacion general true -----------------
	@Test
	void testGeneral_evaluationT() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(+ 3 2)";
		assertEquals(true, lexer.general_evaluation(expression));
	}

	// ----------------- Evaluacion general false -----------------
	@Test
	void testGeneral_evaluationF() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(+ 3 2";
		
		Throwable exception = assertThrows(InterpreterException.class, () -> lexer.general_evaluation(expression));
	    assertEquals("Parenthesis missing", exception.getMessage());
	}
	
	// ----------------- Evaluacion general expresion vacia -----------------
	@Test
	void testGeneral_evaluationE1() throws InterpreterException{
		Lexer lexer = new Lexer();
		
		String expression = "( )";
			
		Throwable exception = assertThrows(InterpreterException.class, () -> lexer.general_evaluation(expression));
	    assertEquals("Empty expression", exception.getMessage());
	}
	
	// ----------------- Evaluacion general falta parentesis ( -----------------
	@Test
	void testGeneral_evaluationE2() throws InterpreterException{
		Lexer lexer = new Lexer();
	    Throwable exception = assertThrows(InterpreterException.class, () -> lexer.general_evaluation("(print + 5 2))"));
	    assertEquals("Opening Parenthesis missing", exception.getMessage());
	}
	
	// ----------------- Evaluacion general falta parentesis ) -----------------
	@Test
	void testGeneral_evaluationE3() throws InterpreterException{
		Lexer lexer = new Lexer();
	    Throwable exception = assertThrows(InterpreterException.class, () -> lexer.general_evaluation("(print (+ 5 2)"));
	    assertEquals("Closing Parenthesis missing", exception.getMessage());
	}
	
	// ----------------- Print numero -----------------
	@Test
	void evaluateTestP1() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(print 5)";
		assertEquals("-> 5\n", lexer.evaluate(expression));
	}
	
	// ----------------- Suna -----------------
	@Test
	void evaluateTestA1() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(+ 2 5)";
		assertEquals("-> 7.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Resta -----------------
	@Test
	void evaluateTestA2() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(- 9 8)";
		assertEquals("-> 1.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Multiplicacion -----------------
	@Test
	void evaluateTestA3() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(* 2 5)";
		assertEquals("-> 10.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Division -----------------
	@Test
	void evaluateTestA4() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(/ 4 2)";
		assertEquals("-> 2.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Declaracion de variables -----------------
	@Test
	void evaluateTestA5() throws InterpreterException{
		Lexer lexer = new Lexer();
		lexer.evaluate("(setq hola 9)");
		String expression = "(print hola)";
		assertEquals("-> 9.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Es igual false -----------------
	@Test
	void evaluateTestA6() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(equal 5 7)";
		assertEquals("-> NIL\n", lexer.evaluate(expression));
	}
	
	// ----------------- Menor que true -----------------
	@Test
	void evaluateTestA7() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(< 5 7)";
		assertEquals("-> T\n", lexer.evaluate(expression));
	}
	
	// ----------------- Mayor que false -----------------
	@Test
	void evaluateTestA8() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(> 5 7)";
		assertEquals("-> NIL\n", lexer.evaluate(expression));
	}
	
	// ----------------- Suma de variables -----------------
	@Test
	void evaluateTestA9() throws InterpreterException{
		Lexer lexer = new Lexer();
		lexer.evaluate("setq hola 5");
		lexer.evaluate("setq adios 5");
		String expression = "(+ hola adios)";
		assertEquals("-> 10.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Declaracion y llamada de funcion -----------------
	@Test
	void evaluateTestF1() throws InterpreterException{
		Lexer lexer = new Lexer();
		lexer.evaluate("(defun fun (x,y)(print + x y))");
		String expression = "(fun 1,2)";
		assertEquals("-> 3.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Funcion contador -----------------
	@Test
	void evaluateTestF2() throws InterpreterException{
		Lexer lexer = new Lexer();
		lexer.evaluate("(setq contador 0)");
		lexer.evaluate("(defun prueba (x)(setq contador + contador 1)(print contador)(cond (< contador x)(prueba x)))");
		String expression = "(prueba 5)";
		assertEquals("-> 1.0\n-> 2.0\n-> 3.0\n-> 4.0\n-> 5.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Error de sintaxis -----------------
	@Test
	void evaluateTestF3() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(cond (equal 5 5)(print diego)(print montoya))";
		Throwable exception = assertThrows(InterpreterException.class, () -> lexer.evaluate(expression));
		assertEquals("Syntax error", exception.getMessage());
	}
	
	// ----------------- Atom false -----------------
	@Test
	void evaluateTestF4() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(atom a b)";
		assertEquals("-> NIL\n", lexer.evaluate(expression));
	}
	
	// ----------------- List true -----------------
	@Test
	void evaluateTestF5() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(list a b)";
		assertEquals("-> T\n", lexer.evaluate(expression));
	}
	
	// ----------------- Quote -----------------
	@Test
	void evaluateTestF61() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(quote - 5 2)";
		assertEquals("-> - 5 2\n", lexer.evaluate(expression));
	}
	
	// ----------------- Quote 2 -----------------
	@Test
	void evaluateTestF62() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(' - 5 2)";
		assertEquals("-> - 5 2\n", lexer.evaluate(expression));
	}
	
	// ----------------- Print texto -----------------
	@Test
	void evaluateTestF7() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(print \"hola\")";
		assertEquals("-> hola\n", lexer.evaluate(expression));
	}
	
	// ----------------- Condicional false -----------------
	@Test
	void evaluateTestF8() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(cond (> 5 7)(print \"hola\")(t(print \"adios\")))";
		assertEquals("-> adios\n", lexer.evaluate(expression));
	}
	
	// ----------------- Error de sintaxis en condicional -----------------
	@Test
	void evaluateTestF9() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(cond (> 5 7))";
		Throwable exception = assertThrows(InterpreterException.class, () -> lexer.evaluate(expression));
	    assertEquals("Syntax error", exception.getMessage());
	}
	
	// ----------------- Condicional atom true -----------------
	@Test
	void evaluateTestF10() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(cond (atom a)(print \"hola\"))";
		assertEquals("-> hola\n", lexer.evaluate(expression));
	}
	
	// ----------------- Condicional list true -----------------
	@Test
	void evaluateTestF11() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(cond (list a b)(print \"hola\"))";
		assertEquals("-> hola\n", lexer.evaluate(expression));
	}
	
	// ----------------- Error de sintaxis función -----------------
	@Test
	void evaluateTestF12() throws InterpreterException{
		Lexer lexer = new Lexer();
		lexer.evaluate("(defun fun (x,y)(print + x y))");
		String expression = "(fun)";
		Throwable exception = assertThrows(InterpreterException.class, () -> lexer.evaluate(expression));
		assertEquals("Syntax error", exception.getMessage());
	}
	
	// ----------------- Llamada de funciones con variables -----------------
	@Test
	void evaluateTestF13() throws InterpreterException{
		Lexer lexer = new Lexer();
		lexer.evaluate("setq hola 5");
		lexer.evaluate("(defun fun (x,y)(print + x y))");
		String expression = "(fun hola,2)";
		assertEquals("-> 7.0\n", lexer.evaluate(expression));
	}
	
	// ----------------- Error de sintaxis condicional -----------------
	@Test
	void evaluate1() throws InterpreterException{
		Lexer lexer = new Lexer();
		String expression = "(< 2)";
		
		Throwable exception = assertThrows(InterpreterException.class, () -> lexer.evaluate(expression));
		assertEquals("Syntax error", exception.getMessage());
	}
	
	// ----------------- Error de sintaxis condicional -----------------
	@Test
	void evaluate2() throws InterpreterException{
		Lexer lexer = new Lexer();
	    Throwable exception = assertThrows(InterpreterException.class, () -> lexer.evaluate("(< 5)"));
	    assertEquals("Syntax error", exception.getMessage());
	}
	
}
