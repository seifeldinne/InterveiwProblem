package sfh.tuto.CodingGame.problem;

public class FindLargest {
	
	
	public int find(int[] numbers) {
		int largest = numbers[0];
		for (int i = 1; i <= numbers.length - 1; i++) {
			if (largest < numbers[i]) {
				largest = numbers[i];
			}
		}
		return largest;
	}
}