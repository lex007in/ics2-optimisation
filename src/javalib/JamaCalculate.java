package javalib;

import util.Utils;
import Jama.Matrix;

public class JamaCalculate {
	
	private static Matrix matrix1;
	private static Matrix matrix2;
	
	public static long init(int n1, int n2) {
		long start = System.currentTimeMillis();
		matrix1= new Matrix(Utils.getRandomArray(n1, n2));
		matrix2= new Matrix(Utils.getRandomArray(n1, n2));
		return System.currentTimeMillis() - start;
	}

	public static long calculate() {
		long start = System.currentTimeMillis();
		matrix1.times(matrix2);
		return System.currentTimeMillis() - start;
	}
}
