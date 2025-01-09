public class SyncThreadMonitor {
    private int flag1 = 0;
    private int flag2 = 0;
    private int flag3 = 0;
    private int flag4 = 0;


    public synchronized void wait1() throws InterruptedException {
        if (flag1 != 3) { wait(); }
    }
    public synchronized void signal1() {
        flag1++;
        if (flag1 == 3) { notifyAll(); }
    }
    public synchronized void wait2() throws InterruptedException {
        if (flag2 != 4) { wait(); }
    }
    public synchronized void signal2() {
        flag2++;
        if (flag2 == 4) { notifyAll(); }
    }
    public synchronized void wait3() throws InterruptedException {
        if (flag3 != 4) { wait(); }
    }
    public synchronized void signal3() {
        flag3++;
        if (flag3 == 4) { notifyAll(); }
    }
    public synchronized void wait4() throws InterruptedException {
        if (flag4 != 3) { wait(); }
    }
    public synchronized void signal4() {
        flag4++;
        if (flag4 == 3) { notify(); }
    }

}
