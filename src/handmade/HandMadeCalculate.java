package handmade;

import util.Utils;

public class HandMadeCalculate {
	static double[][] matrix1;
	static double[][] matrix2;
	static double[][] result;
	static double[][] m2T;
	static int size;

	public static long init(int n1) {
		long start = System.currentTimeMillis();
		matrix1 = Utils.getRandomArray(n1, n1);
		matrix2 = Utils.getRandomArray(n1, n1);
		size = n1;
		return System.currentTimeMillis() - start;
	}

	public static long calculate() {
		long start = System.currentTimeMillis();
		multiply();
		return System.currentTimeMillis() - start;
	}

	private static double[][] multiply() {
		 Runtime runtime = Runtime.getRuntime();
	     int numCores = runtime.availableProcessors();
	     Thread[] threads = new Thread[numCores];
	     int marker, step = 0;
	     
	     final int aColumns = matrix1.length;
	 	 final int aRows = matrix1[0].length;
	 	 final int bColumns = matrix2.length;
	 	 final int bRows = matrix2[0].length;

	     
	     result = new double[matrix1.length][matrix2[0].length];
	     
	     m2T = new double[bColumns][bRows];
	     
	 	 for (int i = 0; i < bRows; i++) {
	 	 	for (int j = 0; j < bColumns; j++) {
	 			m2T[j][i] = matrix2[i][j];
	 		}
	 	 }

	     
	     try{
		     step = size / numCores;
	         for (int c = 0; c < numCores; c++) {
	             marker = step * c;
	             threads[c] = new Thread(
	                 new MultiplyMatrix(marker, marker + step, size)
	             );
	             threads[c].start();
	         }
	         for (int c = 0; c < numCores; c++) {
	             threads[c].join();
	         }
		} catch (Exception allExceptions) {
	        System.err.println("Error: " + allExceptions);
	    }
	     
	    return result;
	}
	
	private static class MultiplyMatrix implements Runnable {
        int min, max, size;

        public MultiplyMatrix(int lower, int higher, int size) {
            min = lower;
            max = higher;
            this.size = size;
        }

        public void run() {
            for (int x = min; x < max; x++) {
                for (int y = 0; y < size; y++) {
                	result[x][y] = 0;
                    for (int count = 0; count < size; count++) {
                    	result[x][y] += matrix1[x][count] * m2T[y][count];
                    }
                }
            }
        }
    }
}
