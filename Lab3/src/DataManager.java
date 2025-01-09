public class DataManager {
    public static final int N = 1000;
    public static final int P = 4;
    public static final int H = N / P;

    public static int[][] MZ = new int[N][N];
    public static int[][] MR = new int[N][N];
    public static int[][] MB = new int[N][N];
    public static int[][] MK = new int[N][N];

    public static long[] B = new long[N];
    public static long[] A = new long[N];
    public static long[] C = new long[N];
    public static long[] D = new long[N];

    public static long e = 0;
    public static long findMinValue(long[] arr){
        long minValue = arr[0];
        for(int i = 1; i < arr.length; i++){
            if(arr[i] < minValue){
                minValue = arr[i];
            }
        }
        return minValue;
    }
}
