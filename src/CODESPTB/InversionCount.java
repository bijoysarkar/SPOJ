import java.io.*;

public class InversionCount {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			int no_of_test_cases = Integer.parseInt(br.readLine().trim());
			while (no_of_test_cases-- > 0) {
				int size = Integer.parseInt(br.readLine().trim());
				int input[] = new int[size];
				String inputStr[] = br.readLine().trim().split(" ");

				for (int i = 0; i < size; i++) {
					input[i] = Integer.parseInt(inputStr[i]);
				}
				System.out.println(mergesort(input, 0, size - 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static int mergesort(int input[], int start, int end) {
		int length = end - start + 1;
		if (length == 1) {
			return 0;
		} else {
			int start1 = start;
			int end1 = start + (length - 1) / 2;
			int start2 = end1 + 1;
			int end2 = end;
			int invcount = mergesort(input, start1, end1)
					+ mergesort(input, start2, end2);

			int copy[] = new int[length];
			int i, j, k;
			for (i = start1, j = start2, k = 0; i <= end1 && j <= end2;) {
				if (input[i] <= input[j]) {
					copy[k++] = input[i++];
				} else {
					invcount = invcount + (end1 - i + 1);
					copy[k++] = input[j++];
				}
			}
			if (j > end2) {
				for (; i <= end1;)
					copy[k++] = input[i++];
			} else {
				for (; j <= end2;)
					copy[k++] = input[j++];
			}

			for (k = 0, i = start1; k < length; i++, k++) {
				input[i] = copy[k];
			}
			return invcount;
		}
	}

}
