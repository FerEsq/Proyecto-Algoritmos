import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class VariablesTests {
	
	//public InterpreterException thrown = InterpreterException.none();
	//Expected ex = InterpreterException.class();

	// ----------------- get variable -----------------
	@Test
	void getVarTest() throws InterpreterException {
		Variables var = new Variables();
		String name = "x";
		var.add(name, 43678.0);
		assertEquals(43678.0, var.getVar(name));
	}
	
	// ----------------- get variable exception -----------------
	@Test
	void getVarETest() throws InterpreterException {
		Variables var = new Variables();
		String name = "x";
		
		Throwable exception = assertThrows(InterpreterException.class, () -> var.getVar(name));
	    assertEquals("Variable x not found", exception.getMessage());
	}
	
	// ----------------- get variable true -----------------
	@Test
	void isVarTTest() throws InterpreterException {
		Variables var = new Variables();
		String name = "y";
		var.add(name, 123.0);
		assertEquals(true, var.isVar(name));
	}
	
	// ----------------- get variable false -----------------
	@Test
	void isVarFTest() throws InterpreterException {
		Variables var = new Variables();
		String name = "y";
		//var.add(name, 123.0);
		assertEquals(false, var.isVar(name));
	}
	
	// ----------------- get variable false -----------------
		@Test
		void getDiegoTest() throws InterpreterException {
			Variables var = new Variables();
			String name = "diego";
			var.dgo();
			assertEquals(69.0, var.getVar(name));
		}

}
