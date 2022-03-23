import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Class Pruebas
 * 
 * Clase para realizar pruebas del interprete
 * 
 * @version 1.0, 21/03/2022
 * 
 * @author 
 * Andres E. Montoya W. - 21552
 * Diego E. Lemus L. - 21469
 * Fernanda Esquivel - 21542
 */

public class Pruebas {

	public static void main(String[] args) {
		Pattern test = Pattern.compile("^print[ ]+[0-9*+\\-/ ]+",Pattern.CASE_INSENSITIVE);
		System.out.println(test.matcher("print / 2 2").find());

	}

}
