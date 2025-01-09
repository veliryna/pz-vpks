public class SharedResourceMonitor {
    private long b = Integer.MAX_VALUE;
    private long p;
    public synchronized void assignValue_b(long value) {
        b = value;
    }
    public synchronized void assignValue_p(long value) { p = value; }
    public synchronized long copyScalar_b() {
        return b;
    }
    public synchronized long copyScalar_p() {
        return p;
    }
}
