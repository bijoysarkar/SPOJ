import java.io.*;

class MCOINS {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));

			String input1[] = br.readLine().trim().split(" ");
			int K = Integer.parseInt(input1[0]);
			int L = Integer.parseInt(input1[1]);

			String input2[] = br.readLine().trim().split(" ");
			int count = input2.length;
			int tower_height[] = new int[count];

			int max = 0;
			for (int i = 0; i < count; i++) {
				int input = Integer.parseInt(input2[i]);
				tower_height[i] = input;
				if (input > max)
					max = input;
			}

			char solution[] = new char[max + 1];

			solution[0] = 'B';
			solution[1] = 'A';

			int i;
			for (i = 2; i < K; i++) {				
				if (solution[i - 1] == 'B')
					solution[i] = 'A';
				else
					solution[i] = 'B';
			}

			for (; i < L; i++) {
				if (solution[i - 1] == 'B' || solution[i - K] == 'B')
					solution[i] = 'A';
				else
					solution[i] = 'B';
			}

			for (; i <= max; i++) {
				if (solution[i - 1] == 'B' || solution[i - K] == 'B'
						|| solution[i - L] == 'B')
					solution[i] = 'A';
				else
					solution[i] = 'B';
			}

			StringBuilder sb = new StringBuilder();
			for(int j:tower_height){
				sb.append(solution[j]);
			}
			System.out.print(sb.toString());
			
		} catch (Exception e) {
			
		} 
	}

}
