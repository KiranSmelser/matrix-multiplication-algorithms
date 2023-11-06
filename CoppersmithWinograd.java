import java.util.Arrays;

class CoppersmithWinograd {
    // Method to perform matrix multiplication
    public static int[][] matrixMultiply(int[][] A, int[][] B) {
        int m = A.length; // Number of rows in the first matrix
        int n = B[0].length; // Number of columns in the second matrix
        int[][] C = new int[m][n]; // Resultant matrix

        // If either dimension is 1, use standard matrix multiplication
        if (m == 1 || n == 1) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < A[0].length; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        } else { // Otherwise, use the Coppersmith-Winograd algorithm
            int maxSize = Math.max(Math.max(m, n), Math.max(A[0].length, B.length));
            int size = 1;
            while (size < maxSize) {
                size *= 2;
            }

            // Padding the matrices to the appropriate size
            int[][] paddedA = new int[size][size];
            int[][] paddedB = new int[size][size];

            for (int i = 0; i < m; i++) {
                paddedA[i] = Arrays.copyOf(A[i], size);
            }

            for (int i = 0; i < B.length; i++) {
                paddedB[i] = Arrays.copyOf(B[i], size);
            }

            // Call the recursive function for matrix multiplication
            int[][] paddedC = multiplyRecursive(paddedA, paddedB);

            // Extract the resulting matrix from the padded one
            for (int i = 0; i < m; i++) {
                C[i] = Arrays.copyOfRange(paddedC[i], 0, n);
            }
        }

        return C;
    }

    // Recursive function to multiply matrices using the Coppersmith-Winograd algorithm
    private static int[][] multiplyRecursive(int[][] A, int[][] B) {
        int n = A.length; // Dimension of the matrices
        int[][] C = new int[n][n]; // Resultant matrix

        if (n == 1) { // If the matrix dimension is 1, perform standard multiplication
            C[0][0] = A[0][0] * B[0][0];
        } else { // Otherwise, apply the Coppersmith-Winograd algorithm
            int newSize = n / 2;
            int[][] A11 = new int[newSize][newSize];
            int[][] A12 = new int[newSize][newSize];
            int[][] A21 = new int[newSize][newSize];
            int[][] A22 = new int[newSize][newSize];
            int[][] B11 = new int[newSize][newSize];
            int[][] B12 = new int[newSize][newSize];
            int[][] B21 = new int[newSize][newSize];
            int[][] B22 = new int[newSize][newSize];

            // Split the matrices into smaller submatrices
            splitMatrix(A, A11, 0, 0);
            splitMatrix(A, A12, 0, newSize);
            splitMatrix(A, A21, newSize, 0);
            splitMatrix(A, A22, newSize, newSize);
            splitMatrix(B, B11, 0, 0);
            splitMatrix(B, B12, 0, newSize);
            splitMatrix(B, B21, newSize, 0);
            splitMatrix(B, B22, newSize, newSize);

            // Perform the necessary recursive matrix multiplications
            int[][] M1 = multiplyRecursive(addMatrices(A11, A22), addMatrices(B11, B22));
            int[][] M2 = multiplyRecursive(addMatrices(A21, A22), B11);
            int[][] M3 = multiplyRecursive(A11, subtractMatrices(B12, B22));
            int[][] M4 = multiplyRecursive(A22, subtractMatrices(B21, B11));
            int[][] M5 = multiplyRecursive(addMatrices(A11, A12), B22);
            int[][] M6 = multiplyRecursive(subtractMatrices(A21, A11), addMatrices(B11, B12));
            int[][] M7 = multiplyRecursive(subtractMatrices(A12, A22), addMatrices(B21, B22));

            // Combine the intermediate results to obtain the final submatrices
            int[][] C11 = addMatrices(subtractMatrices(addMatrices(M1, M4), M5), M7);
            int[][] C12 = addMatrices(M3, M5);
            int[][] C21 = addMatrices(M2, M4);
            int[][] C22 = addMatrices(subtractMatrices(addMatrices(M1, M3), M2), M6);

            // Join the submatrices to form the resulting matrix
            joinMatrices(C11, C, 0, 0);
            joinMatrices(C12, C, 0, newSize);
            joinMatrices(C21, C, newSize, 0);
            joinMatrices(C22, C, newSize, newSize);
        }

        return C;
    }

    // Helper method to split a matrix into submatrices
    private static void splitMatrix(int[][] parent, int[][] child, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < child.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < child.length; j1++, j2++) {
                child[i1][j1] = parent[i2][j2];
            }
        }
    }

    // Helper method to add two matrices
    private static int[][] addMatrices(int[][] A, int[][] B) {
        int n = A.length; // Dimension of the matrices
        int[][] C = new int[n][n]; // Resultant matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    // Helper method to subtract one matrix from another
    private static int[][] subtractMatrices(int[][] A, int[][] B) {
        int n = A.length; // Dimension of the matrices
        int[][] C = new int[n][n]; // Resultant matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    // Helper method to join submatrices into a parent matrix
    private static void joinMatrices(int[][] child, int[][] parent, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < child.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < child.length; j1++, j2++) {
                parent[i2][j2] = child[i1][j1];
            }
        }
    }
}
