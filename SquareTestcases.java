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

    // Function to print out the matrix results from the different algorithms side-by-side
    public static String arrayToStringSideBySide(int[][] array1, int[][] array2, int[][] array3) {
        StringBuilder result = new StringBuilder();
	    
	// Get the max length of the arrays
        int maxLength = Math.max(Math.max(array1.length, array2.length), array3.length);

	// Print each matrix side-by-side
        for (int i = -1; i < maxLength; i++) {
	    // Print first array
            if (i < array1.length) {
		// Print indentifier above matrix
            	if (i == -1) {
            		result.append("Iterative:");

			// Check if the size of the printed first row is less than "Iterative" for formatting
            		if (arrayRowToString(array1[0]).length() > 10) {
	            		for (int j = 0; j < arrayRowToString(array1[0]).length() - 10; j++)
	            			result.append(" ");
	            		result.append("\t\t");
            		}
            		else
            			result.append("\t");
            	}
            	else {
			// Print row
            		result.append(arrayRowToString(array1[i]));
            		result.append("\t\t");
            	}            		
            }
		
	    // Print second array
            if (i < array2.length) {
		// Print indentifier above matrix
            	if (i == -1) {
            		result.append("Strassen's:");

			// Check if the size of the printed first row is less than "Iterative" for formatting
            		if (arrayRowToString(array1[0]).length() > 10) {
	            		for (int j = 0; j < arrayRowToString(array1[0]).length() -11; j++)
	            			result.append(" ");
	            		result.append("\t\t");
            		}
            		else
            			result.append("\t");
            	}
            	else {
			// Print row
            		result.append(arrayRowToString(array2[i]));
            		result.append("\t\t");
            	}
            }

            if (i < array3.length) {
		// Print indentifier above matrix
            	if (i == -1) {
            		result.append("Winograd's:");
            	}
            	else {
			// Print row
            		result.append(arrayRowToString(array3[i]));
		}
            }
            result.append("\n");
        }
        return result.toString();
    }
    
    // Function to print out the input matrices side-by-side
    public static String arrayToStringSideBySide(int[][] array1, int[][] array2) {
        StringBuilder result = new StringBuilder();

	// Get the max length of the arrays
        int maxLength = Math.max(array1.length, array2.length);

	// Print each matrix side-by-side
        for (int i = -1; i < maxLength; i++) {
	    // Print first array
            if (i < array1.length) {
		// Print indentifier above matrix
            	if (i == -1) {
            		result.append("Matrix A:");
			
			// Check if the size of the printed first row is less than "Iterative" for formatting
            		if (arrayRowToString(array1[0]).length() > 10) {
	            		for (int j = 0; j < arrayRowToString(array1[0]).length() - 9; j++)
	            			result.append(" ");
	            		result.append("\t\t");
            		}
            		else
            			result.append("\t");
            	}
            	else {
			// Print row
            		result.append(arrayRowToString(array1[i]));
            		result.append("\t\t");
            	}            		
            }

            // Print second array
            if (i < array2.length) {
		// Print indentifier above matrix
            	if (i == -1) {
            		result.append("Matrix B:");
            	}
            	else {
			// Print row
            		result.append(arrayRowToString(array2[i]));
		}
            }
            result.append("\n");
        }

        return result.toString();
    }
    
    // Returns the array row as a string
    private static String arrayRowToString(int[] row) {
        StringBuilder rowString = new StringBuilder();

        for (int j = 0; j < row.length; j++) {
            rowString.append(row[j]);
            if (j < row.length - 1) {
                rowString.append(", ");
                if (row[j] < 100)
                	rowString.append(" ");
                if (row[j] < 10)
                	rowString.append(" ");
            }
        }
        return rowString.toString();
    }

    public static void main(String[] args) {
        int maxMatrixSize = 8;
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

            int[][] StrassenResult = StrassensMatrixMultiplication.multiply_matrix(A, B);
            
            int[][] WinogradResult = CoppersmithWinograd.matrixMultiply(A, B);

            // Check the accuracy of the result
            int[][] referenceResult = matrixMultiplication(A, B); // Iterative matrix multiplication for comparison
            
            boolean isEqual = checkEquality(StrassenResult, referenceResult) && checkEquality(WinogradResult, referenceResult);
            
            // Print results
            System.out.println("Matrix size: " + size + "x" + size);
            System.out.print(arrayToStringSideBySide(A,B));
            System.out.println("\nResults:");
            System.out.print(arrayToStringSideBySide(referenceResult,StrassenResult,WinogradResult));
            System.out.println("Results are accurate: " + isEqual + "\n\n");
        }
    }
}
