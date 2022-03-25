import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PredicatesTests {

	// ----------------- Mayor que true -----------------
	@Test
	void graterThanTtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		Double val1 = 10.0;
		Double val2 = 5.0;
		assertEquals("T", pdr.graterThan(val1, val2));
	}
	
	// ----------------- Mayor que false -----------------
	@Test
	void graterThanFtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		Double val1 = 2.0;
		Double val2 = 4.0;
		assertEquals("NIL", pdr.graterThan(val1, val2));
	}
	
	// ----------------- Menor que true -----------------
	@Test
	void smallerThanTtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		Double val1 = 6.0;
		Double val2 = 8.0;
		assertEquals("T", pdr.smallerThan(val1, val2));
	}
	
	// ----------------- Menor que false -----------------
	@Test
	void smallerThanFtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		Double val1 = 9.0;
		Double val2 = 3.0;
		assertEquals("NIL", pdr.smallerThan(val1, val2));
	}
	
	// ----------------- Igual a true -----------------
	@Test
	void equalsToTtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		Double val1 = 41.0;
		Double val2 = 41.0;
		assertEquals("T", pdr.equalsTo(val1, val2));
	}
	
	// ----------------- Igual a false -----------------
	@Test
	void equalsToFtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		Double val1 = 19.0;
		Double val2 = 21.0;
		assertEquals("NIL", pdr.equalsTo(val1, val2));
	}
	
	// ----------------- Atom true -----------------
	@Test
	void atomTtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		String[] tokens = {"a", "b"};
		assertEquals("T", pdr.atom(tokens));
	}
	
	// ----------------- Atom false -----------------
	@Test
	void atomFtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		String[] tokens = {"a", "b", "c"};
		assertEquals("NIL", pdr.atom(tokens));
	}
	
	// ----------------- List true -----------------
	@Test
	void listTtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		String[] tokens = {"a", "b", "c", "d", "e"};
		assertEquals("T", pdr.list(tokens));
	}
	
	// ----------------- List false -----------------
	@Test
	void listFtest() throws InterpreterException {
		Predicates pdr = new Predicates();
		String[] tokens = {"a"};
		assertEquals("NIL", pdr.list(tokens));
	}

}
