import java.util.Arrays;

public class Thread1 extends Thread{
    private final SyncThreadMonitor syncMonitor;
    private final SharedResourceMonitor resMonitor;
    public Thread1(SyncThreadMonitor sm, SharedResourceMonitor rm) {
        syncMonitor = sm;
        resMonitor = rm;
    }

    @Override
    public void run(){
        for (int i = 0; i < DataManager.N; i++) {
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.MZ[i][j] = 1;
            }
        }

        syncMonitor.signal1();
        try {
            syncMonitor.wait1();
        } catch (InterruptedException e) { throw new RuntimeException(e); }

        var b1 = DataManager.findMinValue(Arrays.copyOfRange(DataManager.B, 0, DataManager.H));
        resMonitor.assignValue_b(Long.min(b1, resMonitor.copyScalar_b()));

        syncMonitor.signal2();
        try {
            syncMonitor.wait2();
        } catch (InterruptedException e) { throw new RuntimeException(e); }


        for (int i = 0; i < DataManager.H; i++){
            for (int j = 0; j < DataManager.N; j++){
                for (int k = 0; k < DataManager.N; k++){
                    DataManager.MK[i][j] += DataManager.MZ[i][k] * DataManager.MR[k][j];
                }
            }
        }

        syncMonitor.signal3();
        try {
            syncMonitor.wait3();
        } catch (InterruptedException e) { throw new RuntimeException(e); }

        long p1 = resMonitor.copyScalar_p();

        for (int i = 0; i < DataManager.H; i++) {
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.C[i] += (DataManager.A[i] * DataManager.MB[i][j]);
            }
            DataManager.C[i] *= p1;
        }

        for (int i = 0; i < DataManager.H; i++) {
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.D[i] += DataManager.B[i] * DataManager.MK[i][j];
            }
        }

        for (int i = 0; i < DataManager.H; i++){
            DataManager.e += DataManager.C[i]*DataManager.D[i];
        }

        syncMonitor.signal4();
    }
}
