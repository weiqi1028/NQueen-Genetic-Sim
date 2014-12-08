import java.util.ArrayList;
import java.util.Random;

public class MyRandom {
	public ArrayList<Integer> random(int number){		// generate non-duplicate numbers
		ArrayList<Integer> list = new ArrayList();
		Random rand = new Random();
		boolean duplicate;
		int n = 0;
		n = rand.nextInt(number) + 1;
		list.add(n);
		for (int i =1; i < number; i++){
			duplicate = true;
			do{
				n = rand.nextInt(number) + 1;
				duplicate = false;
				for (int j = 0; j < i; j++){
					if (n == list.get(j)){
						duplicate = true;
					}
				}
			}while(duplicate);
			list.add(n);
		}
		return list;
	}
}
