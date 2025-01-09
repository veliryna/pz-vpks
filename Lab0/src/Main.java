/*
 Дисципліна: ПРОГРАМНЕ ЗАБЕЗПЕЧЕННЯ ВПКС
 Лабораторна робота: Лабораторна робота ЛР0.3 ПОТОКИ В МОВІ JAVA
 Виконавець: Величко Ірина Євгенівна, група ІП-03
 Дата: 28.02.2023
 Варіант:
 1.18 d = (A*B) + (C*(B*(MA*MD)))
 2.24 MG = SORT(MF - MH * MK)
 3.6 O = MAX(MP*MR)*V
*/
public class Main {
    static int N = 10; // size of vectors and matrices
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread has started.");
        Thread T1 = new Thread(() -> {
            System.out.println("Thread T1 is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int d = F1();
            System.out.println("Result of thread T1:\n" + d);
            System.out.println("Thread T1 has finished.\n");
        });

        Thread T2 = new Thread(() -> {
            System.out.println("Thread T2 is running...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int[][] MG = F2();
            System.out.println("Result of thread T2:");
            for(int[] line : MG) {
                for (int elem : line)
                    System.out.print(elem + " ");
                System.out.println();
            }
            System.out.println("Thread T2 has finished.\n");
        });

        Thread T3 = new Thread(() -> {
            System.out.println("Thread T3 is running...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int[] O = F3();
            System.out.println("Result of thread T3:");
            for (int elem : O)
                System.out.print(elem + " ");
            System.out.println();
            System.out.println("Thread T3 has finished.\n");
        });
        T1.start();
        T2.start();
        T3.start();

        T1.join();
        T2.join();
        T3.join();
        System.out.println("Main thread has finished");
    }

    public static int F1() {
        int[] A = Data.createVector(N);
        int[] B = Data.createVector(N);
        int[] C = Data.createVector(N);
        int[][] MA = Data.createMatrix(N);
        int[][] MD = Data.createMatrix(N);
        return Data.multiplyVectors(A, B) + Data.multiplyVectors(C, Data.multiplyVectorOnMatrix(B, Data.multiplyMatrices(MA, MD)));
    }

    public static int[][] F2() {
        int[][] MF = Data.createMatrix(N);
        int[][] MH = Data.createMatrix(N);
        int[][] MK = Data.createMatrix(N);
        return Data.sortMatrixRows(Data.subtractMatrices(MF, Data.multiplyMatrices(MH,MK)));
    }

    public static int[] F3() {
        int[] V = Data.createVector(N);
        int[][] MP = Data.createMatrix(N);
        int[][] MR = Data.createMatrix(N);
        return Data.multiplyNumberOnVector(Data.maxInMatrix(Data.multiplyMatrices(MP, MR)), V);
    }
}