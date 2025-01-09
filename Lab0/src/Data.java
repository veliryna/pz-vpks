import java.util.Random;
public class Data {
    public static int[][] multiplyMatrices(int[][] M1, int[][] M2) {
        if (M1[0].length != M2.length){
            throw new ArithmeticException("These matrices cannot be multiplied.\n" +
                    "1st matrix' number of columns must equal number of rows of th 2nd matrix.");
        }
        int[][] result = new int[M1.length][M2[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < M2.length; k++) {
                    result[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return result;
    }

    public static int multiplyVectors(int[] V1, int[] V2){
        if(V1.length != V2.length){
            throw new ArithmeticException("These vectors cannot be multiplied.\n" +
                    "Their lengths must be the same.");
        }
        int product = 0;
        for(int i = 0; i < V1.length; i++){
            product += V1[i]*V2[i];
        }
        return product;
    }

    public static int[] multiplyVectorOnMatrix(int[] V1, int[][] M1){
        if (V1.length != M1.length){
            throw new ArithmeticException("These vector and matrix cannot be multiplied.\n" +
                    "Vector's length must equal the number of rows in matrix.");
        }
        int[] result = new int[M1[0].length];
        for(int i = 0; i < result.length; i++){
            result[i] = 0;
            for (int j = 0; j < V1.length; j++) {
                result[i] += M1[i][j] * V1[j];
            }
        }
        return result;
    }

    public static int[] multiplyNumberOnVector(int n, int[] V){
        int[] result = new int[V.length];
        for(int i = 0; i < V.length; i++){
            result[i] = n * V[i];
        }
        return result;
    }

    public static int[][] subtractMatrices(int[][] M1, int[][] M2){
        if(M1.length != M2.length || M1[0].length != M2[0].length){
            throw new ArithmeticException("These matrices cannot be subtracted.\n" +
                    "Matrices must have equal dimensions.");
        }
        int[][] result = new int[M1.length][M1[0].length];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[i].length; j++){
                result[i][j] = M1[i][j] - M2[i][j];
            }
        }
        return result;
    }

    public static int maxInMatrix(int[][] M){
        int maxElement = Integer.MIN_VALUE;
        for (int[] line : M) {
            for (int elem : line) {
                if (elem > maxElement) {
                    maxElement = elem;
                }
            }
        }
        return maxElement;
    }

    public static int[][] sortMatrixRows(int[][] M){
        for (int i = 0; i < M.length-1; i++) {
            int min_idx = i;
            for (int j = i+1; j < M.length; j++)
                if (sum(M[j]) < sum(M[min_idx]))
                    min_idx = j;

            int[] temp = M[min_idx];
            M[min_idx] = M[i];
            M[i] = temp;
        }
        return M;
    }

    public static int sum(int[] arr){
        int s = 0;
        for(int elem : arr)
            s += elem;
        return s;
    }

    public static int[] createVector(int n){
        Random rnd = new Random();
        int[] v = new int[n];
        for(int i = 0; i < n; i++){
            v[i] = rnd.nextInt(-20, 21);
        }
        return v;
    }

    public static int[][] createMatrix(int n){
        Random rnd = new Random();
        int[][] m = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++) {
                m[i][j] = rnd.nextInt(-20, 21);
            }
        }
        return m;
    }
}
