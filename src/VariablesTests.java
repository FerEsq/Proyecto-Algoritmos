import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VariablesTests {

	// ----------------- get variable -----------------
	@Test
	void getVarTest() throws InterpreterException {
		Variables var = new Variables();
		String name = "x";
		var.add(name, 43678.0);
		assertEquals(43678.0, var.getVar(name));
	}
	
	// ----------------- get variable -----------------
	@Test
	void isVarTest() throws InterpreterException {
		Variables var = new Variables();
		String name = "y";
		var.add(name, 123.0);
		assertEquals(true, var.isVar(name));
	}

}
