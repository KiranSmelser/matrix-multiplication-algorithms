import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;


public class SquareTestcases {
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
        int maxMatrixSize = 1024;
        Random random = new Random();
                
        String filePath = "C:\\Users\\elij1\\Downloads\\TestOutput.csv";

        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write the header to the CSV file
            writer.println("Coppersmith-Winograd runtime (nanoseconds),Strassen for square matrices runtime (nanoseconds),Strassen for non-square matrices runtime (nanoseconds)");

        for (int size = 2; size <= maxMatrixSize; size *= 2) {
            int[][] A = new int[size][size];
            int[][] B = new int[size][size];
            
            // Non-Square matrices
//            int nonSquareSize1 = size*2-1;
//            int nonSquareSize2 = size/2+1;
//            int[][] C = new int[nonSquareSize1][nonSquareSize1];
//            int[][] D = new int[nonSquareSize2][nonSquareSize2];
            
            // Fill non-square matrices C and D with random values
//            for (int i = 0; i < nonSquareSize1; i++) {
//                for (int j = 0; j < nonSquareSize2; j++) {
//                    C[i][j] = random.nextInt(10);
//                    D[i][j] = random.nextInt(10);
//                }
//            }

            // Fill matrices A and B with random values
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    A[i][j] = random.nextInt(10);
                    B[i][j] = random.nextInt(10);
                }
            }
            
            long strassenSquareStartTime = System.nanoTime();
            int[][] strassenSquareResult = StrassensMatrixMultiplication.multiply_matrix(A, B);
            long strassenSquareEndTime = System.nanoTime();

            long copperStartTime = System.nanoTime();
            int[][] copperResult = CoppersmithWinograd.matrixMultiply(A, B);
            long copperEndTime = System.nanoTime();

            
            
//            long NonSquareStartTime = System.nanoTime();
//            int[][] NonSquareResult = Non_Square.NonSquare(A, B);
//            long NonSquareEndTime = System.nanoTime();

            // Check the accuracy of the result
            int[][] referenceResult = matrixMultiplication(A, B); // Iterative matrix multiplication for comparison
            boolean isEqual = checkEquality(copperResult, referenceResult) && checkEquality(strassenSquareResult, referenceResult);
            
            writer.println(String.valueOf(copperEndTime - copperStartTime) + "," +
            		String.valueOf(strassenSquareEndTime - strassenSquareStartTime));// + "," +
            		//String.valueOf(NonSquareEndTime - NonSquareStartTime));
            

            System.out.println("Matrix size: " + size + "x" + size);
            System.out.println("Coppersmith-Winograd runtime (nanoseconds): " + (copperEndTime - copperStartTime));
            System.out.println("Strassen for square matrices runtime (nanoseconds): " + (strassenSquareEndTime - strassenSquareStartTime));
            System.out.println("Results are accurate: " + isEqual);
        }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
