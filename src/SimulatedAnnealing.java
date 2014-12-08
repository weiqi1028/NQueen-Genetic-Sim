import java.io.*;
import java.util.*;

public class SimulatedAnnealing {
	int[] queens;
	int dimension;
	double temperature = 1000;
	double cooling = 0.99;
	MyRandom myRand = new MyRandom();

	SimulatedAnnealing(int dimension) {
		this.dimension = dimension;
	}

	void initialize() {
		ArrayList<Integer> list = new ArrayList();
		list = myRand.random(dimension);
		queens = new int[dimension];
		for (int i = 0; i < dimension; i++) {
			queens[i] = list.get(i);
		}
	}

	double evaluate() { // evaluation function
		double wrong = 0;
		double evaluation;
		for (int i = 0; i < dimension - 1; i++) {
			for (int j = i + 1; j < dimension; j++) {
				if (queens[i] == queens[j]) {
					wrong++;
				} else {
					if (queens[i] - queens[j] < 0) {
						if (queens[j] == queens[i] + j - i)
							wrong++;
					} else {
						if (queens[i] == queens[j] + j - i)
							wrong++;
					}
				} // end of if
			} // end of loop on j
		} // end of loop on i
		evaluation = 1 / (wrong + 1);
		return evaluation;
	}

	void annealing() {
		Random rand = new Random();
		int[] temp = new int[dimension];
		temp = queens.clone(); // a copy of the current state
		double before = evaluate(); // evaluate the state before doing swap
		int position1 = 0; // swap position 1, it is a random conflict position
		int position2 = 0; // swap position 2, it is a totally random position
		for (int i = 0; i < dimension - 1; i++) {
			for (int j = i + 1; j < dimension; j++) {
				if (queens[i] == queens[j] + j - i
						|| queens[j] == queens[i] + j - i) {
					int choose = rand.nextInt(2);
					if (choose == 0)
						position1 = i;
					else
						position1 = j;
					break;
				}
			}
		}
		do {
			position2 = rand.nextInt(dimension);
		} while (position2 == position1);
		int num = temp[position1];
		temp[position1] = temp[position2];
		temp[position2] = num;
		double after = evaluate(); // evaluate the state after doing swap

		if (after - before > 0) { // accept the swap
			queens = temp.clone();
		} else { // still accept the swap only with a certain probability
			if (rand.nextDouble() < Math.exp((after - before) / temperature)) {
				queens = temp.clone();
			}
		}
	}

	public void process() {
		// input();
		initialize();
		long begin = System.currentTimeMillis(); // calculate the time elapsed
		double evaluation = 0;
		boolean found = false;
		while (true) { // control temperature
			while (!found) { // test if one solution is found
				evaluation = evaluate();
				if (evaluation == 1) {
					found = true;
					break;
				} else {
					annealing();
				}
			}
			if (found) {
				break;
			}
			temperature = temperature * cooling;
		}
		if (found) {
			long over = System.currentTimeMillis();
			double time = over - begin;
			String solution = Integer.toString(queens[0]);
			for (int i = 1; i < dimension; i++) {
				solution = solution + " " + queens[i];
			}
			System.out.println("Solution found: " + solution);
			System.out.println("Time elapsed: " + time + "ms.");
			new Board(queens, dimension);
		} else
			System.out.println("Solution not found.");
	}

}
