public class StrassensMatrixMultiplication {
    
	public static int[][] multiply_matrix(int[][] A, int[][] B)
    {
		// Get size of array
        int length = A.length;
        
        // Initialize result array
        int[][] resultArray = new int[length][length];
        
        
        // Base case when matrix is split into single components
        if (length == 1) {
        	resultArray[0][0] = A[0][0] * B[0][0];
        }
        else
        {
        	// Initialize the split arrays for matrix A
            int[][] A11 = new int[length/2][length/2];
            int[][] A12 = new int[length/2][length/2];
            int[][] A21 = new int[length/2][length/2];
            int[][] A22 = new int[length/2][length/2];
            
            // Initialize the split arrays for matrix B
            int[][] B11 = new int[length/2][length/2];
            int[][] B12 = new int[length/2][length/2];
            int[][] B21 = new int[length/2][length/2];
            int[][] B22 = new int[length/2][length/2];
  
            
            // Splitting matrix A into 4 equal parts
            split(A, A11, 0 , 0);
            split(A, A12, 0 , length/2);
            
            split(A, A21, length/2, 0);
            split(A, A22, length/2, length/2);
            

            // Splitting matrix B into 4 equal parts
            split(B, B11, 0 , 0);
            split(B, B12, 0 , length/2);
            
            split(B, B21, length/2, 0);
            split(B, B22, length/2, length/2);
            
            
            // Do the matrix multiplication with recursive calls
            int [][] i1 = multiply_matrix(addMatrices(A11, A22), addMatrices(B11, B22));
            int [][] i2 = multiply_matrix(addMatrices(A21, A22), B11);
            int [][] i3 = multiply_matrix(A11, subtractMatrices(B12, B22));
            int [][] i4 = multiply_matrix(A22, subtractMatrices(B21, B11));
            int [][] i5 = multiply_matrix(addMatrices(A11, A12), B22);
            int [][] i6 = multiply_matrix(subtractMatrices(A21, A11), addMatrices(B11, B12));
            int [][] i7 = multiply_matrix(subtractMatrices(A12, A22), addMatrices(B21, B22));
  
            // Combine the 7 multiplication matrices into 4 matrices
            int [][] C11 = addMatrices(subtractMatrices(addMatrices(i1, i4), i5), i7);
            int [][] C12 = addMatrices(i3, i5);
            int [][] C21 = addMatrices(i2, i4);
            int [][] C22 = addMatrices(subtractMatrices(addMatrices(i1, i3), i2), i6);
  
            // Join the four matrices to get resulting matrix
            join(C11, resultArray, 0 , 0);
            join(C12, resultArray, 0 , length/2);
            join(C21, resultArray, length/2, 0);
            join(C22, resultArray, length/2, length/2);
        }
        
        // return the resulting matrix
        return resultArray;
    }
	
    
    // Helper function to add two matrices
    public static int[][] addMatrices(int[][] A, int[][] B)
    {
        int length = A.length;
        int row, col;
        int[][] addResult = new int[length][length];
        
        for (row = 0; row < length; row++)
        {
        	for (col = 0; col < length; col++)
        	{
        		addResult[row][col] = A[row][col] + B[row][col];
        	}
        }
        return addResult;
    }
	
    
    // Helper function to subtract two matrices
    public static int[][] subtractMatrices(int[][] A, int[][] B)
    {
        int length = A.length;
        int row, col;
        int[][] subResult = new int[length][length];
        
        for (row = 0; row < length; row++)
        {
            for (col = 0; col < length; col++)
            {
            	subResult[row][col] = A[row][col] - B[row][col];
            }
        }
        
        return subResult;
    }
    
    
    // Helper function to join matrices into the resulting matrix
    public static void join(int[][] C, int[][] P, int iB, int jB) 
    {
    	int length = C.length;
    	int i1, i2, j1, j2;
    	
        for(i1 = 0, i2 = iB; i1 < length; i1++, i2++)
        {
            for(j1 = 0, j2 = jB; j1 < length; j1++, j2++)
            {
                P[i2][j2] = C[i1][j1];
            }
        }
    }  
    
    
    // Helper function to split a matrix into two half-sized matrices
    public static void split(int[][] P, int[][] C, int iB, int jB) 
    {
    	int length = C.length;
    	int i1, i2, j1, j2;
    	
        for(i1 = 0, i2 = iB; i1 < length; i1++, i2++)
        {
            for(j1 = 0, j2 = jB; j1 < length; j1++, j2++)
            {
                C[i1][j1] = P[i2][j2];
            }
        }
    }
}
