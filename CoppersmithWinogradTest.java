import java.util.Arrays;
import java.util.Random;

public class CoppersmithWinogradTest {
    // Function to perform matrix multiplication
    public static int[][] matrixMultiplication(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    // Function to check if two matrices are equal
    public static boolean checkEquality(int[][] A, int[][] B) {
        if (A.length != B.length) {
            return false;
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (A[i][j] != B[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function to print a 2D array
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        int minMatrixSize = 2;
        int maxMatrixSize = 1024;
        Random random = new Random();

        for (int size = 2; size <= maxMatrixSize; size *= 2) {
            int[][] A = new int[size][size];
            int[][] B = new int[size][size];

            // Fill matrices A and B with random values
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    A[i][j] = random.nextInt(10);
                    B[i][j] = random.nextInt(10);
                }
            }

            long startTime = System.nanoTime();
            int[][] result = CoppersmithWinograd.matrixMultiply(A, B);
            long endTime = System.nanoTime();

            // Check the accuracy of the result
            long refStartTime = System.nanoTime();
            int[][] referenceResult = matrixMultiplication(A, B); // Iterative matrix multiplication for comparison
            long refEndTime = System.nanoTime();
            boolean isEqual = checkEquality(result, referenceResult);

            System.out.println("Matrix size: " + size + "x" + size);
            System.out.println("Coppersmith-Winograd runtime (nanoseconds): " + (endTime - startTime));
            System.out.println("Iterative runtime (nanoseconds): " + (refEndTime - refStartTime));
            System.out.println("Result is accurate: " + isEqual);
        }
    }
}
