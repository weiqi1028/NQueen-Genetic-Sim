import java.util.*;
import java.io.*;

class Individual{
	public int[] encode;
}

public class Genetic {
	int population;			// the population
	int queen;				// the number of queens
	int generation = 0;
	boolean found = false;
	//Individual[] group = new Individual[10];	// states
	Vector<Individual> group = new Vector();
	MyRandom myRand = new MyRandom();
	
	Genetic(int dimension, int population){
		this.queen = dimension;
		this.population = population;
	}
	
	void initialize(){
		ArrayList<Integer> list = new ArrayList();
		for (int i = 0; i < population; i++){
			Individual individual = new Individual();
			for (int j = 0; j < queen; j++){
				individual.encode = new int[queen];
			}
			group.addElement(individual);
		}
		for (int i = 0; i < population; i++){
			list = myRand.random(queen);
			for (int j = 0; j < queen; j++){
				group.elementAt(i).encode[j] = list.get(j);
			}
		}
	}
	
	private int charToInt(char c){		// convert a char to an integer
		String s;
		s = String.valueOf(c);
		int number;
		number = Integer.parseInt(s);
		return number;
	}
	
	double fitnessFunction(Individual sample){		// fitness function
		double wrong = 0;
		double fitness;
		int[] number = new int[queen];
		for (int k = 0; k < queen; k++)
			number[k] = sample.encode[k];
		for (int i = 0; i < queen-1; i++){
			for (int j = i + 1; j < queen; j++){
				if (number[i] == number[j]){
					wrong++;
				}
				else{
					if (number[i] - number[j] < 0){
						if (number[j] == number[i] + j - i)
							wrong++;
					}
					else{
						if (number[i] == number[j] + j - i)
							wrong++;
					}
				} //	end of if
			} // end of loop on j
		} // end of loop on i
		fitness = 1 / (wrong + 1);
		return fitness;
	}
	int select(Vector<Individual> sample){
		int parent = 0;
		double sumfitness = 0;
		for (int i = 0; i < sample.size(); i++)
			sumfitness = sumfitness + fitnessFunction(sample.elementAt(i));
		double[] p = new double[sample.size()];
		for (int i = 0; i < sample.size(); i++)
			p[i] = fitnessFunction(sample.elementAt(i)) / sumfitness;
		double[] q = new double[sample.size()];
		q[0] = p[0];
		for (int i = 1; i < sample.size(); i++)
			q[i] = q[i - 1] + p[i];
		double r = Math.random();
		for (int i = 0; i < sample.size(); i++){
			if (r < q[i]){
				parent = i;
				break;
			}
		}
		return parent;
	}
	
	void reproduce(){
		//System.out.println("Reproducing child... ");
		int parentX, parentY, split;				// two random parents (can not be the same)
		Random rand = new Random();

		
		int babies = 0;
		Vector<Individual> temp = new Vector(group.size());
		for (int i = 0; i < group.size(); i++){
			temp.addElement(group.elementAt(i));
		}
		group.clear();
		while (babies < temp.size()){
			parentX = select(temp);
			parentY = select(temp);
			Individual mother = temp.elementAt(parentX);
			Individual father = temp.elementAt(parentY);
			//System.out.println("parentX is " + parentX);		// debug
			//System.out.println("parentY is " + parentY);
			
			Individual child1 = new Individual();
			Individual child2 = new Individual();
			if (parentX == parentY){
				child1 = mother;
				child2 = father;
			}
			else{
				for (int i = 0; i < queen; i++){
					child1.encode = new int[queen];
					child2.encode = new int[queen];
				}
				split = rand.nextInt(queen-1);		// split the gene from position "split" (0~6)
				for (int i = 0; i <= split; i++){
					child1.encode[i] = mother.encode[i];
					child2.encode[i] = father.encode[i];
				}
				for (int i = split + 1; i < queen; i++){
					child1.encode[i] = mother.encode[i];
					child2.encode[i] = father.encode[i];
				}
				/*
				String gene11 = mother.encode.substring(0, split);
				String gene12 = mother.encode.substring(split, queen);
				String gene21 = father.encode.substring(0, split);
				String gene22 = father.encode.substring(split, queen);
				child1.encode = gene11 + gene22;
				child2.encode = gene21 + gene12;
				*/
			}
			double mutate = Math.random();
			if (mutate < 0.9){
				int n;
				int position;
				position = rand.nextInt(queen);
				n = rand.nextInt(queen) + 1;
				child1.encode[position] = n;
				position = rand.nextInt(queen);
				n = rand.nextInt(queen) + 1;
				child2.encode[position] = n;
			}
			group.addElement(child1);
			group.addElement(child2);
			babies = babies + 2;
		}
	}
	
	/*
	void input(){			// debug
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Input the number of the queens:");
			String s1 = br.readLine();
			queen = Integer.parseInt(s1);
			System.out.println("Input the population:");
			String s2 = br.readLine();
			population = Integer.parseInt(s2);
		}
		catch (IOException ioe){
			System.err.println(ioe.getMessage());
		}
	}*/
	
	public void process(){
		//input();
		initialize();
		double begin = System.currentTimeMillis();			// calculate the time elapsed
		
		//System.out.println("Initial state:");		// debug
		//for (int i = 0; i < population; i++){
		//	System.out.println(group.elementAt(i).encode);
		//}
		
		Individual solution = new Individual();
		
		double best = 0;
		outer:
		while (!found){
			for (int i = 0; i < population; i++){		// check if one solution is found
				if (fitnessFunction(group.elementAt(i)) == 1){
					double over = System.currentTimeMillis();
					double time = over - begin;
					solution = group.elementAt(i);
					System.out.println("Solution found at " + generation + " generation.");
					System.out.println("The fitness of solution is " + fitnessFunction(group.elementAt(i)));
					String s = Integer.toString(solution.encode[0]);
					for (int j = 1; j < queen; j++){
						s = s + " " + solution.encode[j];
					}
					System.out.println("The solution is: " + s);
					System.out.println("Time elapsed: " + time + "ms.");
					found = true;
					break outer;
				}
			}
			for (int i = 0; i < population; i++){
				if (fitnessFunction(group.elementAt(i)) > best){
					best = fitnessFunction(group.elementAt(i));
				}
			}
			
			reproduce();		// reproduce new generation by crossover
			//System.out.println("Current state: ");		// debug
			//for (int i = 0; i < population; i++)
			//	System.out.println(group.elementAt(i).encode);
			//System.out.println("best one: " + best);

			generation++;
		}

		int[] result = new int[queen];
		for (int i = 0; i < queen; i++){
			result[i] = solution.encode[i];
		}
		Board board = new Board(result, queen);
	}
	
}

