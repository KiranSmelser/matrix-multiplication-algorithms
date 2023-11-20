import java.util.Random;
/***
     *NonSquareTestcases.java
     *Author: Connor O'Neill
     *tests the non square strassens method 
*/
public class NonSquareTestcases {
    //compares two matricies and checks them for equality
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
    public static void main(String[] args) {
        int maxMatrixSize = 100;
        Random random = new Random();
        //creates the array and fills it with data
        for (int size = 2; size <= maxMatrixSize; size *= 2) {
            int[][] A = new int[size*3][size];
            int[][] B = new int[size][size*2];
            for (int i = 0; i < size*3; i++) {
                for (int j = 0; j < size; j++) {
                    A[i][j] = random.nextInt(10);
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size*2; j++) {
                    B[i][j] = random.nextInt(10);
                }
            }
            //data for timing and execution of the function
            long strassenNonSquareStartTime = System.nanoTime();
            int[][] strassenNonSquareResult = Non_Square.NonSquare(A, B,0); 
            long strassenNonSquareEndTime = System.nanoTime();
            //checks to make sure the answer is correct and calculate the time it took to find the answer
            int[][] referenceResult = Non_Square.NonSquare(A, B, 1000000); // Iterative matrix multiplication for comparison
            boolean isEqual = checkEquality(strassenNonSquareResult, referenceResult);
            System.out.println("Matrix size: " + size*3 + "x" + size + "x" + size*2);
            System.out.println("Strassen for square matrices runtime (nanoseconds): " + (strassenNonSquareEndTime - strassenNonSquareStartTime));
            System.out.println("Results are accurate: " + isEqual);
        }
    }
}
