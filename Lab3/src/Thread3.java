import java.util.Arrays;

public class Thread3 extends Thread{
    private final SyncThreadMonitor syncMonitor;
    private final SharedResourceMonitor resMonitor;
    public Thread3(SyncThreadMonitor sm, SharedResourceMonitor rm) {
        syncMonitor = sm;
        resMonitor = rm;
    }

    @Override
    public void run(){
        for (int i = 0; i < DataManager.N; i++) {
            DataManager.B[i] = 1;
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.MB[i][j] = 1;
            }
        }
        resMonitor.assignValue_p(1);

        syncMonitor.signal1();
        try {
            syncMonitor.wait1();
        } catch (InterruptedException e) { throw new RuntimeException(e); }

        long b3 = DataManager.findMinValue(Arrays.copyOfRange(DataManager.B, DataManager.H*2, DataManager.H*3));
        resMonitor.assignValue_b(Long.min(b3, resMonitor.copyScalar_b()));

        syncMonitor.signal2();
        try {
            syncMonitor.wait2();
        } catch (InterruptedException e) { throw new RuntimeException(e); }

        for (int i = DataManager.H*2; i < DataManager.H*3; i++){
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

        long p3 = resMonitor.copyScalar_p();

        for (int i = DataManager.H*2; i < DataManager.H*3; i++) {
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.C[i] += (DataManager.A[i] * DataManager.MB[i][j]);
            }
            DataManager.C[i] *= p3;
        }

        for (int i = DataManager.H*2; i < DataManager.H*3; i++) {
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.D[i] += DataManager.B[i] * DataManager.MK[i][j];
            }
        }

        for (int i = DataManager.H*2; i < DataManager.H*3; i++){
            DataManager.e += DataManager.C[i]*DataManager.D[i];
        }
        syncMonitor.signal4();
    }
}
