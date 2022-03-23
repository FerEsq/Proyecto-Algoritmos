
/**
 * Class Predicates
 * 
 * Clase que maneja los predicados de las condicionales del interprete
 * 
 * @version 1.0, 20/03/2022
 * 
 * @author 
 * Andres E. Montoya W. - 21552
 * Diego E. Lemus L. - 21469
 * Fernanda Esquivel - 21542
 */

public class Predicates {
	/**
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public String graterThan (double val1, double val2) {
		if (val1>val2) {
			return "T";
		}else {
			return "NIL";
		}
	}
	public String smallerThan (double val1, double val2) {
		if (val1<val2) {
			return "T";
		}else {
			return "NIL";
		}
	}
	public String equalsTo (double val1, double val2) {
		if (val1==val2) {
			return "T";
		}else {
			return "NIL";
		}
	}
	public String atom (String[] tokens) {
		if (tokens.length==2) {
			return "T";
		}else {
			return "NIL";
		}
	}
	public String list (String[] tokens) {
		if (tokens.length>2) {
			return "T";
		}else {
			return "NIL";
		}
	}
}
