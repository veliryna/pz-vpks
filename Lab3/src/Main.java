// Програмне забезпечення високопродуктивних комп'ютерних систем
// Лабораторна робота 3. Мова Java. Монітори
// Варіант №11
// e = ((p*(A*MB))*(B* (MZ*MR) ) + min(B)
// 1. MZ
// 2. e, A, MR
// 3. MB, B, p
// 4. -
// Величко Ірина, ІП-03
// 17.06.2023

public class Main {
    public static void main(String[] args) throws InterruptedException{
        SyncThreadMonitor sm = new SyncThreadMonitor();
        SharedResourceMonitor rm = new SharedResourceMonitor();
        Thread1 t1 = new Thread1(sm, rm);
        Thread2 t2 = new Thread2(sm, rm);
        Thread3 t3 = new Thread3(sm, rm);
        Thread4 t4 = new Thread4(sm, rm);
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        long end = System.currentTimeMillis();
        System.out.println("Program Execution Time: " + (end - start) + " ms");
    }
}