import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArithmeticsTests {

	// ----------------- Suma -----------------
	@Test
	void sumaTest() throws InterpreterException {
		Arithmetics ats = new Arithmetics();
		String expression = "+ 3 2";
		assertEquals(5.0, ats.prefixcalc(expression));
	}
	
	// ----------------- Resta -----------------
	@Test
	void restaTest() throws InterpreterException {
		Arithmetics ats = new Arithmetics();
		String expression = "- 10 2";
		assertEquals(8.0, ats.prefixcalc(expression));
	}
	
	// ----------------- Multiplicacion -----------------
	@Test
	void multiplicacionTest() throws InterpreterException {
		Arithmetics ats = new Arithmetics();
		String expression = "* 6 2";
		assertEquals(12.0, ats.prefixcalc(expression));
	}
	
	// ----------------- Division -----------------
	@Test
	void divisionTest() throws InterpreterException {
		Arithmetics ats = new Arithmetics();
		String expression = "/ 6 2";
		assertEquals(3.0, ats.prefixcalc(expression));
	}

}
