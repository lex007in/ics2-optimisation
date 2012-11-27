package handmade;

import util.Utils;

public class HandMadeCalculate {
	static double[][] matrix1;
	static double[][] matrix2;

	public static long init(int n1, int n2) {
		long start = System.currentTimeMillis();
		matrix1 = Utils.getRandomArray(n1, n2);
		matrix2 = Utils.getRandomArray(n1, n2);
		return System.currentTimeMillis() - start;
	}

	public static long calculate() {
		long start = System.currentTimeMillis();
		multiply(matrix1, matrix2);
		return System.currentTimeMillis() - start;
	}

	private static double[][] multiply(double[][] m1, double[][] m2) {
		double[][] result = new double[m1.length][m2[0].length];
		final int aColumns = m1.length;
		final int aRows = m1[0].length;
		final int bColumns = m2.length;
		final int bRows = m2[0].length;

		double m2T[][] = new double[bColumns][bRows];
		for (int i = 0; i < bRows; i++) {
			for (int j = 0; j < bColumns; j++) {
				m2T[j][i] = m2[i][j];
			}
		}

		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < aColumns; j++) {
				double summand = 0.0;
				for (int k = 0; k < bColumns; k++) {
					summand += m1[i][k] * m2T[j][k];
				}
				result[i][j] = summand;
			}
		}
		return result;
	}
}
