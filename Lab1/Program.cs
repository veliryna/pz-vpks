// Програмне забезпечення високопродуктивних комп'ютерних систем
// Лабораторна робота 1. Мова C#. Семафори, бар'єри, критичні секції
// Варіант 15
// Завдання: МА= MD*MC*d + max(Z)*(MX+MM)*p
// 1: d, MA, MM
// 2: MX, MC
// 3: -
// 4: Z, MD, p
// Величко Ірина ІП-03
// 15.06.2023

using System;
using System.Threading;
using System.Linq;
using System.Diagnostics;

namespace PZ
{
    class Program
    {
        private static readonly int N = 1000;
        private static readonly int P = 4;
        private static readonly int H = N / P;

        private static int[,] MA = new int[N, N];
        private static int[,] MD = new int[N, N];
        private static int[,] MC = new int[N, N];
        private static int[,] MX = new int[N, N];
        private static int[,] MM = new int[N, N];
      
        private static int[,] MK = new int[N, N];
        private static int[,] MS = new int[N, N];

        private static int[] Z = new int[N];
        
        private static int z = Int32.MinValue;
        private static int d, p;
        //Семафори для вводу
        private static Semaphore S1 = new Semaphore(0, 3);
        private static Semaphore S2 = new Semaphore(0, 3);
        private static Semaphore S3 = new Semaphore(0, 3);
        private static Semaphore S4 = new Semaphore(0, 3);
        //Семафори для обчислення
        private static Semaphore S5 = new Semaphore(0, 3);
        private static Semaphore S6 = new Semaphore(0, 3);
        private static Semaphore S7 = new Semaphore(0, 3);
        private static Semaphore S8 = new Semaphore(0, 3);
        private static Semaphore S9 = new Semaphore(0, 3);
        private static Semaphore S10 = new Semaphore(0, 3);
        private static Semaphore S11 = new Semaphore(0, 3);
        private static Semaphore S12 = new Semaphore(0, 3);
        //Критичні секції
        private static object CS1 = new object();
        private static object CS2 = new object(); 
        private static object CS3 = new object(); 
        //Бар'єр
        private static Barrier B0 = new Barrier(4);


        static void Main(string[] args)
        {
            Thread t1 = new Thread(Thread1);
            Thread t2 = new Thread(Thread2);
            Thread t3 = new Thread(Thread3);
            Thread t4 = new Thread(Thread4);

            Stopwatch sw = new Stopwatch();
            sw.Start();

            t1.Start();
            t2.Start();
            t3.Start();
            t4.Start();
            t1.Join();
            t2.Join();
            t3.Join();
            t4.Join();

            sw.Stop();
            TimeSpan ts = sw.Elapsed;
            string time = $"{ts.TotalSeconds:00}.{ts.Milliseconds:00} s";
            Console.WriteLine($"Program Execution Time: {time}");
        }

        private static void Thread1(){
            // Введення MM і d
            for (int i = 0; i < N; i++){
               for (int j = 0; j < N; j++){
                   MM[i, j] = 1;
               }
            }
            d = 1;

            S1.Release(3);
            S2.WaitOne();
            S3.WaitOne();
            S4.WaitOne();

            // Обчислення 2. Критична ділянка 1
            var z1 = Z.Max();
            lock (CS2){
                if (z1 > z){
                    z = z1;
                }
            }

            S5.Release(3);
            S6.WaitOne();
            S7.WaitOne();
            S8.WaitOne();

            // Обчислення 3: MKH = MDH * MC
            for (int i = 0; i < H; i++){
               for (int j = 0; j < N; j++){
                   for (int k = 0; k < N; k++){
                       MK[i, j] += MD[i, k] * MC[k, j];
                    }
                }
            }

            S9.Release(3);
            S10.WaitOne();
            S11.WaitOne();
            S12.WaitOne();

            int d1, p1;
            lock (CS1){d1 = d;}
            lock (CS2){z1 = z;}
            lock (CS3){p1 = p;}

            // Обчислення 4: MAH = MK*d + z*(MXH + MMH)*p
            for (int i = 0; i < H; i++){
                for (int j = 0; j < N; j++){
                   MS[i, j] = MX[i, j] + MM[i, j];
                }
            }

            for (int i = 0; i < H; i++){
                for (int j = 0; j < N; j++){
                    MS[i, j] *= (z1*p1);
                }
            }
          
            for (int i = 0; i < H; i++){
                for (int j = 0; j < N; j++){
                    MK[i, j] *= d1;
                }
            }

            for (int i = 0; i < H; i++){
                for (int j = 0; j < N; j++){
                    MA[i, j] = MK[i, j] + MS[i, j];
                }
            }

            B0.SignalAndWait();

            //Виведення результату
            for (int i = 0; i < N; i++){
                for (int j = 0; j < N; j++){
                    Console.Write(MA[i, j].ToString() + ' ');
                }
                Console.WriteLine();
            }

        }

        private static void Thread2(){
            //Введення МХ та МС
            for (int i = 0; i < N; i++){
               for (int j = 0; j < N; j++){
                   MX[i, j] = 1;
               }
            }
            for (int i = 0; i < N; i++){
               for (int j = 0; j < N; j++){
                   MC[i, j] = 1;
               }
            }
            S2.Release(3);
            S1.WaitOne();
            S3.WaitOne();
            S4.WaitOne();

            var z2 = Z.Max();
            lock (CS2){
                if (z2 > z){
                    z = z2;
                }
            }

            S6.Release(3);
            S5.WaitOne();
            S7.WaitOne();
            S8.WaitOne();


            // Обчислення 3: MKH = MDH * MC
            for (int i = H; i < H*2; i++){
               for (int j = 0; j < N; j++){
                   for (int k = 0; k < N; k++){
                       MK[i, j] += MD[i, k] * MC[k, j];
                    }
                }
            }

            S10.Release(3);
            S9.WaitOne();
            S11.WaitOne();
            S12.WaitOne();

            int d2, p2;
            lock (CS1){d2 = d;}
            lock (CS2){z2 = z;}
            lock (CS3){p2 = p;}

            // Обчислення 4: MAH = MK*d + z*(MXH + MMH)*p
            for (int i = H; i < H*2; i++){
                for (int j = 0; j < N; j++){
                   MS[i, j] = MX[i, j] + MM[i, j];
                }
            }

            for (int i = H; i < H*2; i++){
                for (int j = 0; j < N; j++){
                    MS[i, j] *= (z2*p2);
                }
            }
          
            for (int i = H; i < H*2; i++){
                for (int j = 0; j < N; j++){
                    MK[i, j] *= d2;
                }
            }

            for (int i = H; i < H*2; i++){
                for (int j = 0; j < N; j++){
                    MA[i, j] = MK[i, j] + MS[i, j];
                }
            }
            B0.RemoveParticipant();
        }
        private static void Thread3(){
            S3.Release(3);
            S1.WaitOne();
            S2.WaitOne();
            S4.WaitOne();

            var z3 = Z.Max();
            lock (CS2){
                if (z3 > z){
                    z = z3;
                }
            }

            S7.Release(3);
            S5.WaitOne();
            S6.WaitOne();
            S8.WaitOne();

            // Обчислення 3: MKH = MDH * MC
            for (int i = H*2; i < H*3; i++){
               for (int j = 0; j < N; j++){
                   for (int k = 0; k < N; k++){
                       MK[i, j] += MD[i, k] * MC[k, j];
                    }
                }
            }

            S11.Release(3);
            S9.WaitOne();
            S10.WaitOne();
            S12.WaitOne();

            int d3, p3;
            lock (CS1){d3 = d;}
            lock (CS2){z3 = z;}
            lock (CS3){p3 = p;}

            // Обчислення 4: MAH = MK*d + z*(MXH + MMH)*p
            for (int i = H*2; i < H*3; i++){
                for (int j = 0; j < N; j++){
                   MS[i, j] = MX[i, j] + MM[i, j];
                }
            }

            for (int i = H*2; i < H*3; i++){
                for (int j = 0; j < N; j++){
                    MS[i, j] *= (z3*p3);
                }
            }
          
            for (int i = H*2; i < H*4; i++){
                for (int j = 0; j < N; j++){
                    MK[i, j] *= d3;
                }
            }

            for (int i = H*2; i < H*3; i++){
                for (int j = 0; j < N; j++){
                    MA[i, j] = MK[i, j] + MS[i, j];
                }
            }
            B0.RemoveParticipant();
        }
        private static void Thread4(){
            //Введення MD, Z, p
            for (int i = 0; i < N; i++){
               for (int j = 0; j < N; j++){
                   MD[i, j] = 1;
               }
            }
            for (int j = 0; j < N; j++){
                Z[j] = 1;
            }
            p = 1;

            S4.Release(3);
            S1.WaitOne();
            S2.WaitOne();
            S3.WaitOne();

            var z4 = Z.Max();
            lock (CS2){
                if (z4 > z){
                    z = z4;
                }
            }

            S8.Release(3);
            S5.WaitOne();
            S6.WaitOne();
            S7.WaitOne();

            // Обчислення 3: MKH = MDH * MC
            for (int i = H*3; i < H*4; i++){
               for (int j = 0; j < N; j++){
                   for (int k = 0; k < N; k++){
                       MK[i, j] += MD[i, k] * MC[k, j];
                    }
                }
            }

            S12.Release(3);
            S9.WaitOne();
            S10.WaitOne();
            S11.WaitOne();

            int d4, p4;
            lock (CS1){d4 = d;}
            lock (CS2){z4 = z;}
            lock (CS3){p4 = p;}

            // Обчислення 4: MAH = MK*d + z*(MXH + MMH)*p
            for (int i = H*3; i < H*4; i++){
                for (int j = 0; j < N; j++){
                   MS[i, j] = MX[i, j] + MM[i, j];
                }
            }

            for (int i = H*3; i < H*4; i++){
                for (int j = 0; j < N; j++){
                    MS[i, j] *= (z4*p4);
                }
            }
          
            for (int i = H*3; i < H*4; i++){
                for (int j = 0; j < N; j++){
                    MK[i, j] *= d4;
                }
            }

            for (int i = H*3; i < H*4; i++){
                for (int j = 0; j < N; j++){
                    MA[i, j] = MK[i, j] + MS[i, j];
                }
            }
            B0.RemoveParticipant();
        }
    }
}
