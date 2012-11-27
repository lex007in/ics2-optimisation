package util;

import java.util.Random;

public class Utils {
	private static final Random random = new Random();
	
	public static double[][] getRandomArray(int n1, int n2){
		double[][] result= new double[n1][n2];
		for (int i = 0; i < n1; i++) {
			for (int j = 0; j < n2; j++) {
				result[i][j]=random.nextDouble();
			}
		}
		return result;
	}
}
