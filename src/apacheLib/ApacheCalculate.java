package apacheLib;

import org.apache.commons.math.linear.Array2DRowRealMatrix;

import util.Utils;

public class ApacheCalculate {

	static Array2DRowRealMatrix matrix1;
	static Array2DRowRealMatrix matrix2;

	public static long init(int n1, int n2) {
		long start = System.currentTimeMillis();
		matrix1 = new Array2DRowRealMatrix(Utils.getRandomArray(n1, n2));
		matrix2 = new Array2DRowRealMatrix(Utils.getRandomArray(n1, n2));
		return System.currentTimeMillis() - start;
	}

	public static long calculate() {
		long start = System.currentTimeMillis();
		matrix1.multiply(matrix2);
		return System.currentTimeMillis() - start;
	}
}
