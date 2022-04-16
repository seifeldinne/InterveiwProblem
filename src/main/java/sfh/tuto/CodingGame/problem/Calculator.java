package sfh.tuto.CodingGame.problem;

import java.math.BigDecimal;

class Calculator {

	/**
	 * Sums an array of​​​​​​‌​​‌‌​​‌​​‌​​‌‌‌​​‌​​​‌​‌ numbers.
	 *
	 * @return the exact sum of the given numbers
	 */
	static String sum(String... numbers) {
		double total = 0;

		for (String number : numbers) {
			total = total + Double.valueOf(number);
		}
		System.out.println(total);
		return String.valueOf(total);
	}

	static String sumBigDecimal(String... numbers) {
		BigDecimal total = new BigDecimal(0);

		for (String number : numbers) {
			total = total.add(new BigDecimal(number));
		}

		return String.valueOf(total);
	}

	public static void main(String[] args) {
		System.out.println(Double.parseDouble("99.35"));
		System.out.println(Double.parseDouble("1.10"));
		System.out.println(Calculator.sum("99.35", "1.10"));
		System.out.println(Calculator.sumBigDecimal("99.35", "1.10"));

	}
}