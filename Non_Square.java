import java.util.Arrays;
/**
    *Non_Square.java
    *Author: Connor O'Neill
    *creates an alogrithm for solving non square matrix multiplication
    *using strassens method
*/
public class Non_Square {
    public static int[][] NonSquare(int[][] a,int[][] b, int c) {
        int threshold=c;
        //makes sure the dimesions are correct
        if (a[0].length!=b.length) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication");
        }
        int al=a.length;
        int shared=a[0].length;
        int bw=b[0].length;
        //pads the matrices
        int max=Math.max(Math.max(al,shared),Math.max(bw,al));
        int size=(int) Math.pow(2,Math.ceil(Math.log(max) / Math.log(2)));
        int[][] apadded=new int[size][size];
        int[][] bpadded=new int[size][size];
        double sample=Math.log(size)/Math.log(2);
        while(sample!=(int)sample) {
            size++;
            apadded=new int[size][size];
            bpadded=new int[size][size];
        }
        //copys the information into the padded matrix
        for (int i=0; i<al; i++) {
            apadded[i]=Arrays.copyOf(a[i],size);
        }
        for (int i=0; i<b.length; i++) {
            bpadded[i]=Arrays.copyOf(b[i],size);
        }
        //for testing to do normal matrix multiplication instead
        if (size<=threshold) { 
            return multiplyStandard(apadded,bpadded);
        } 
        //Strassen's method
        else {
            return multiply(apadded,bpadded);
        }
    }
    public static int[][] multiply(int[][] apadded,int[][] bpadded) {
        int[][] finalMatrix=new int[apadded.length][apadded.length];
        //base case for multiplication
        if (apadded.length==1) {
            finalMatrix [0][0]=apadded[0][0]*bpadded[0][0];
        }
        else {
            //creates 8 new matrices
            int[][] apadded11=new int[apadded.length/2][apadded.length/2];
            int[][] apadded12=new int[apadded.length/2][apadded.length/2];
            int[][] apadded21=new int[apadded.length/2][apadded.length/2];
            int[][] apadded22=new int[apadded.length/2][apadded.length/2];
            int[][] bpadded11=new int[apadded.length/2][apadded.length/2];
            int[][] bpadded12=new int[apadded.length/2][apadded.length/2];
            int[][] bpadded21=new int[apadded.length/2][apadded.length/2];
            int[][] bpadded22=new int[apadded.length/2][apadded.length/2];
            //gives the correct values into each matrix
            divide(apadded,apadded11,0 ,0);
            divide(apadded,apadded12,0 ,apadded.length/2);
            divide(apadded,apadded21,apadded.length/2,0);
            divide(apadded,apadded22,apadded.length/2,apadded.length/2);
            divide(bpadded,bpadded11,0 ,0);
            divide(bpadded,bpadded12,0 ,apadded.length/2);
            divide(bpadded,bpadded21,apadded.length/2,0);
            divide(bpadded,bpadded22,apadded.length/2,apadded.length/2);
            //creates 7 new matrices and multiplys the results together
            int [][] matrix1=multiply(add(apadded11,apadded22),add(bpadded11,bpadded22));
            int [][] matrix2=multiply(add(apadded21,apadded22),bpadded11);
            int [][] matrix3=multiply(apadded11,sub(bpadded12,bpadded22));
            int [][] matrix4=multiply(apadded22,sub(bpadded21,bpadded11));
            int [][] matrix5=multiply(add(apadded11,apadded12),bpadded22);
            int [][] matrix6=multiply(sub(apadded21,apadded11),add(bpadded11,bpadded12));
            int [][] matrix7=multiply(sub(apadded12,apadded22),add(bpadded21,bpadded22));
            //combines all 7 into one big matrix
            int [][] combined1=add(sub(add(matrix1,matrix4),matrix5),matrix7);
            int [][] combined2=add(matrix3,matrix5);
            int [][] combined3=add(matrix2,matrix4);
            int [][] combined4=add(sub(add(matrix1,matrix3),matrix2),matrix6);
            combine(combined1,finalMatrix,0 ,0);
            combine(combined2,finalMatrix,0 ,apadded.length/2);
            combine(combined3,finalMatrix,apadded.length/2,0);
            combine(combined4,finalMatrix,apadded.length/2,apadded.length/2);
        }
        return finalMatrix;
    }
    //function that adds the data in two matrices
    public static int[][] add(int[][] a,int[][] b)  {
        int length=a.length;
        int[][] result=new int[length][length];
        for (int i=0; i<length; i++) {
            for (int j=0; j<length; j++) {
                result[i][j]=a[i][j] + b[i][j];
            }
        }
        return result;
    }
    //function that subtracts the data in two matrices
    public static int[][] sub(int[][] a,int[][] b) {
        int length=a.length;
        int[][] result=new int[length][length];
        for (int i=0; i<length; i++) {
            for (int j=0; j<length; j++) {
                result[i][j]=a[i][j] - b[i][j];
            }
        }
        return result;
    }
    //function that combines two matrices into one
    public static void combine(int[][] a,int[][] b,int val1,int val2) {
        int length=a.length;
        for(int i=0,i2=val1; i < length; i++) {
            for(int j=0,j2=val2; j < length; j++) {
                b[i2][j2]=a[i][j];
                j2++;
            }
            i2++;
        }
    } 
    //function that copys data from one matrix to another
    public static void divide(int[][] a,int[][] b,int val1,int val2) {
    	int length=b.length;
        for(int i=0,i2=val1; i < length; i++) {
            for(int j=0,j2=val2; j < length; j++) {
                b[i][j]=a[i2][j2];
                j2++;
            }
            i2++;
        }
    }
    //standard matrix multplication, used for testing
    private static int[][] multiplyStandard(int[][] a,int[][] b) {
        int m=a.length;
        int n=a[0].length;
        int p=b[0].length;
        int[][] result=new int[m][p];
        for (int i=0;i<m;i++) {
            for (int j=0;j<p;j++) {
                for (int k=0;k<n;k++) {
                    result[i][j]+=a[i][k]*b[k][j];
                }
            }
        }
        return result;
    }
}
