import java.util.HashMap;
import java.util.regex.Pattern;

public class Pruebas {

	public static void main(String[] args) {
		Pattern test = Pattern.compile("^print[ ]+[0-9*+\\-/ ]+",Pattern.CASE_INSENSITIVE);
		System.out.println(test.matcher("print / 2 2").find());

	}

}
