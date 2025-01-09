import java.util.Arrays;

public class Thread2 extends Thread{
    private final SyncThreadMonitor syncMonitor;
    private final SharedResourceMonitor resMonitor;
    public Thread2(SyncThreadMonitor sm, SharedResourceMonitor rm) {
        syncMonitor = sm;
        resMonitor = rm;
    }

    @Override
    public void run(){
        for (int i = 0; i < DataManager.N; i++) {
            DataManager.A[i] = 1;
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.MR[i][j] = 1;
            }
        }

        syncMonitor.signal1();
        try {
            syncMonitor.wait1();
        } catch (InterruptedException e) { throw new RuntimeException(e); }

        var b2 = DataManager.findMinValue(Arrays.copyOfRange(DataManager.B, DataManager.H, DataManager.H*2));
        resMonitor.assignValue_b(Long.min(b2, resMonitor.copyScalar_b()));

        syncMonitor.signal2();
        try {
            syncMonitor.wait2();
        } catch (InterruptedException e) { throw new RuntimeException(e); }

        for (int i = DataManager.H; i < DataManager.H*2; i++){
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

        b2 = resMonitor.copyScalar_b();
        long p2 = resMonitor.copyScalar_p();

        for (int i = DataManager.H; i < DataManager.H*2; i++) {
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.C[i] += (DataManager.A[i] * DataManager.MB[i][j]);
            }
            DataManager.C[i] *= p2;
        }

        for (int i = DataManager.H; i < DataManager.H*2; i++) {
            for (int j = 0; j < DataManager.N; j++) {
                DataManager.D[i] += DataManager.B[i] * DataManager.MK[i][j];
            }
        }

        for (int i = DataManager.H; i < DataManager.H*2; i++){
            DataManager.e += DataManager.C[i]*DataManager.D[i];
        }

        try {
            syncMonitor.wait4();
        } catch (InterruptedException e) { throw new RuntimeException(e); }
        DataManager.e = DataManager.e + b2;
        System.out.println(DataManager.e);
    }
}
