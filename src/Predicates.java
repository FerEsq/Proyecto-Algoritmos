/**
 * 
 */

/**
 * @author Andres
 *
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
