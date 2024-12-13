package mario;

import support.cse131.ArgsProcessor;

public class Mario {

	public static void main(String[] args) {

		//
		// Surprise! This part is done for you.
		// Don't change this and don't ask for any other inputs
		// or testing will fail
		//
		ArgsProcessor ap = new ArgsProcessor(args);
		int size = ap.nextInt("What size mountain do you want?");
		int pattern = ap.nextInt("What pattern (1, 2, 3, or 4)?");

		if (size < 1)
			throw new IllegalArgumentException("Size must be at least 1");
		if (pattern < 1 || pattern > 4)
			throw new IllegalArgumentException("Invalid pattern, must be 1, 2, 3, or 4.  Mario aborts");
		String[][] ladder = new String[size][size];


		if (pattern == 1) {
			int k = 1;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size -k; j++) {
					ladder[i][j] = "*";

				}
				k = k + 1;
			}
			for (int a = 0; a < size; a++) {
				for (int b = 0; b < size; b++) {
					if (ladder[a][b] == "*") {
						System.out.print(" ");
					}
					else {
						System.out.print("#");
					}

				}
				System.out.println();
			}
		}

		if (pattern == 2) {
			int g = size -1;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size -g; j++) {
					ladder[i][j] = "*";
				}
				g = g - 1;
			}
			for (int a = 0; a < size; a++) {
				for (int b = 0; b < size; b++) {
					if (ladder[a][b] == "*") {
						System.out.print("#");
					}
					else {
						System.out.print(" ");
					}

				}
				System.out.println();
			}


		}
		if (pattern == 3) {
			int m = size;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size - m; j++) {
					ladder[i][j] = "*";
				}
				m = m - 1;
			}
			for (int a = 0; a < size; a++) {
				for (int b = 0; b < size; b++) {
					if (ladder[a][b] == "*") {
						System.out.print(" ");
					}
					else {
						System.out.print("#");
					}

				}
				System.out.println();
			}
		}

		if (pattern == 4) {
			int h = 0;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size - h; j++) {
					ladder[i][j]="*";

				}
				h = h + 1;

			}
			for (int a = 0; a < size; a++) {
				for (int b = 0; b < size; b++) {
					if (ladder[a][b] == "*") {
						System.out.print("#");
					}
					else {
						System.out.print(" ");
					}

				}
				System.out.println();
			}


		}






	}
}













