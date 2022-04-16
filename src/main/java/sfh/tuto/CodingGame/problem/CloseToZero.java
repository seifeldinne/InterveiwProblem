package sfh.tuto.CodingGame.problem;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.Arrays;
import java.util.logging.Logger;

public class CloseToZero {
    private static Logger logger = Logger.getLogger(CloseToZero.class.getName());
	public static void main(String[] args) {
        int[] numbers = {2,3,-2};
        String s = closeToZero(numbers)+"";
        logger.info(s);
        
    }
    public static int closeToZero(int [] numbers) {
    	return Arrays.stream(numbers)
    			.filter(i -> i != 0)
                .reduce((a, b) -> abs(a) < abs(b) ? a : (abs(a) == abs(b) ? max(a, b) : b))
                .getAsInt();
    }
}