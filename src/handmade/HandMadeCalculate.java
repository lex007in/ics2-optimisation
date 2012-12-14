package handmade;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import util.Utils;

public class HandMadeCalculate {
	static double[][] matrix1;
	static double[][] matrix2;
	private static final int THRESHOLD = 128;
	static ForkJoinPool forkJoinPool;

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
		MatrixMultiplyTask mainTask = new MatrixMultiplyTask(m1, 0, 0, m2, 0, 0, result, 0, 0, m1.length);
        forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(mainTask);
		return result;
	}
	private static class MatrixMultiplyTask extends RecursiveAction {
        private final double[][] A; // Matrix A
        private final int aRow; // first row of current quadrant of A
        private final int aCol; // first column of current quadrant of A

        private final double[][] B; // Similarly for B
        private final int bRow;
        private final int bCol;

        private final double[][] C; // Similarly for result matrix C
        private final int cRow;
        private final int cCol;

        private final int size;

        MatrixMultiplyTask(double[][] m1, int aRow, int aCol, double[][] m2,
                int bRow, int bCol, double[][] result, int cRow, int cCol, int size) {
            this.A = m1;
            this.aRow = aRow;
            this.aCol = aCol;
            this.B = m2;
            this.bRow = bRow;
            this.bCol = bCol;
            this.C = result;
            this.cRow = cRow;
            this.cCol = cCol;
            this.size = size;
        }

        @Override
        protected void compute() {      
            if (size <= THRESHOLD) {
                multiplyStride2();
            } else {

                int h = size / 2;               

                invokeAll(new MatrixMultiplyTask[] {
                        new MatrixMultiplyTask(A, aRow, aCol, // A11
                                B, bRow, bCol, // B11
                                C, cRow, cCol, // C11
                                h),

                        new MatrixMultiplyTask(A, aRow, aCol + h, // A12
                                B, bRow + h, bCol, // B21
                                C, cRow, cCol, // C11
                                h),

                        new MatrixMultiplyTask(A, aRow, aCol, // A11
                                B, bRow, bCol + h, // B12
                                C, cRow, cCol + h, // C12
                                h),

                        new MatrixMultiplyTask(A, aRow, aCol + h, // A12
                                B, bRow + h, bCol + h, // B22
                                C, cRow, cCol + h, // C12
                                h),

                        new MatrixMultiplyTask(A, aRow + h, aCol, // A21
                                B, bRow, bCol, // B11
                                C, cRow + h, cCol, // C21
                                h),

                        new MatrixMultiplyTask(A, aRow + h, aCol + h, // A22
                                B, bRow + h, bCol, // B21
                                C, cRow + h, cCol, // C21
                                h),

                        new MatrixMultiplyTask(A, aRow + h, aCol, // A21
                                B, bRow, bCol + h, // B12
                                C, cRow + h, cCol + h, // C22
                                h),

                        new MatrixMultiplyTask(A, aRow + h, aCol + h, // A22
                                B, bRow + h, bCol + h, // B22
                                C, cRow + h, cCol + h, // C22
                                h) });

            }
        }

        /**
         * Version of matrix multiplication that steps 2 rows and columns at a
         * time. Adapted from Cilk demos. Note that the results are added into
         * C, not just set into C. This works well here because Java array
         * elements are created with all zero values.
         **/

        void multiplyStride2() {
            for (int j = 0; j < size; j += 2) {
                for (int i = 0; i < size; i += 2) {

                	double[] a0 = A[aRow + i];
                    double[] a1 = A[aRow + i + 1];

                    double s00 = 0.0F;
                    double s01 = 0.0F;
                    double s10 = 0.0F;
                    double s11 = 0.0F;

                    for (int k = 0; k < size; k += 2) {

                    	double[] b0 = B[bRow + k];

                        s00 += a0[aCol + k] * b0[bCol + j];
                        s10 += a1[aCol + k] * b0[bCol + j];
                        s01 += a0[aCol + k] * b0[bCol + j + 1];
                        s11 += a1[aCol + k] * b0[bCol + j + 1];

                        double[] b1 = B[bRow + k + 1];

                        s00 += a0[aCol + k + 1] * b1[bCol + j];
                        s10 += a1[aCol + k + 1] * b1[bCol + j];
                        s01 += a0[aCol + k + 1] * b1[bCol + j + 1];
                        s11 += a1[aCol + k + 1] * b1[bCol + j + 1];
                    }

                    C[cRow + i][cCol + j] += s00;
                    C[cRow + i][cCol + j + 1] += s01;
                    C[cRow + i + 1][cCol + j] += s10;
                    C[cRow + i + 1][cCol + j + 1] += s11;
                }
            }
        }
    }
}
