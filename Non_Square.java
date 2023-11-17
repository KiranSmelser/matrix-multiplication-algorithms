import java.util.Arrays;
public class Non_Square {
    private static final int threshold=4;
    public static int[][] NonSquare(int[][] a, int[][] b) {
        // Check if the matrices are compatible for multiplication
        if (a[0].length!=b.length) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication");
        }
        // Pad matrices
        int al=a.length;
        int shared=a[0].length;
        int bw=b[0].length;
        int max=Math.max(Math.max(al, shared), Math.max(bw, al));
        int size=(int) Math.pow(2, Math.ceil(Math.log(max) / Math.log(2)));
        int[][] apadded=new int[size][size];
        int[][] bpadded=new int[size][size];
        float sample=Math.log(size)/Math.log(2)
        while(sample!=(int)sample) {
            size++;
            apadded=new int[size][size];
            bpadded=new int[size][size];
        }

        for (int i=0; i<al; i++) {
            apadded[i]=Arrays.copyOf(a[i],size);
        }
        for (int i=0; i<b.length; i++) {
            bpadded[i]=Arrays.copyOf(b[i], size);
        }
        // Recursive divide-and-conquer multiplication
        if (size<=threshold) { 
            return multiplyStandard(apadded, bpadded);
        } else {
            // Divide matrices
            int[][] a11=submatrix(apadded,0,0,size/2,size/2);
            int[][] A12=submatrix(apadded,0,size/2,size/2,size);
            int[][] A21=submatrix(apadded,size/2,0,size,size/2);
            int[][] A22=submatrix(apadded,size/2,size/2,size,size);
            int[][] B11=submatrix(bpadded,0,0,size/2,size/2);
            int[][] B12=submatrix(bpadded,0,size/2,size/2,size);
            int[][] B21=submatrix(bpadded,size/2,0,size,size/2);
            int[][] B22=submatrix(bpadded,size/2,size/2,size,size);
            // Calculate products
            int[][] P1=NonSquare(add(a11,A22),add(B11, B22));
            int[][] P2=NonSquare(add(A21,A22),B11);
            int[][] P3=NonSquare(a11,subtract(B12,B22));
            int[][] P4=NonSquare(A22,subtract(B21,B11));
            int[][] P5=NonSquare(add(a11,A12),B22);
            int[][] P6=NonSquare(subtract(A21,a11), add(B11,B12));
            int[][] P7=NonSquare(subtract(A12,A22), add(B21,B22));
            // Calculate submatrices of the result
            int[][] C11=subtract(add(P1,P4), add(P5,P7));
            int[][] C12=add(P3,P5);
            int[][] C21=add(P2,P4);
            int[][] C22=subtract(add(P1,P3), add(P2,P6));
            // Combine the result 
            return combine(combine(C11,C12),combine(C21,C22));
        }
    }
    private static int[][] multiplyStandard(int[][] A, int[][] B) {
        int m=A.length;
        int n=A[0].length;
        int p=B[0].length;
        int[][] result=new int[m][p];
        for (int i=0;i<m;i++) {
            for (int j=0;j<p;j++) {
                for (int k=0;k<n;k++) {
                    result[i][j]+=A[i][k]*B[k][j];
                }
            }
        }
        return result;
    }
    private static int[][] submatrix(int[][] matrix,int start1,int start2,int end1,int end2) {
        int row=end1-start1;
        int col=end2-start2;
        int[][] result=new int[row][col];
        for (int i=0;i<row;i++) {
            result[i]=Arrays.copyOfRange(matrix[start1+i],start2,end1);
        }
        return result;
    }

    private static int[][] add(int[][] a,int[][] b) {
        if(a.length==0) {
            return b;
        }
        if(b.length==0) {
            return a;
        }
        int row=a.length;
        int col=a[0].length;
        int[][] result=new int[row][col];
        for (int i=0;i<row;i++) {
            for (int j=0;j<col;j++) {
                result[i][j]=a[i][j]+b[i][j];
            }
        }
        return result;
    }

    private static int[][] subtract(int[][] a,int[][] b) {
        int row=a.length;
        int col=a[0].length;
        int[][] result=new int[row][col];
        for (int i=0;i<row;i++) {
            for (int j=0;j<col;j++) {
                result[i][j]=a[i][j]-b[i][j];
            }
        }
        return result;
    }
    private static int[][] combine(int[][] a,int[][] b) {
        int rows1=a.length;
        int cols1=a[0].length;
        int rows2=b.length;
        int cols2=b[0].length;
        if (rows1 != rows2) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for concatenation");
        }
        int[][] result=new int[rows1][cols1 + cols2];
        for (int i=0; i<rows1; i++) {
            result[i]=Arrays.copyOf(a[i],cols1+cols2);
            System.arraycopy(b[i],0,result[i],cols1,cols2);
        }
        return result;
    }
    public static void main(String[] args) {
        int[][] A={{1, 2, 3}, {4, 5, 6}, {4, 5, 6}, {4, 5, 6}};
        int[][] B={{7, 8,7,8}, {9, 10,7,8}, {11, 12,7,8}};
        int[][] result=NonSquare(A, B);
        for (int[] row : result) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
