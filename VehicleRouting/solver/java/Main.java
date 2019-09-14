import java.io.*;
import java.security.*;
import java.util.*;

public class Main {

    public static void main (String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int M = sc.nextInt();
            int depotX = sc.nextInt();
            int depotY = sc.nextInt();
            int[] posX = new int[N];
            int[] posY = new int[N];
            for (int i = 0; i < N; i++) {
                posX[i] = sc.nextInt();
                posY[i] = sc.nextInt();
            }
            int[] cap = new int[M];
            int[] speed = new int[M];
            for (int i = 0; i < M; i++) {
                cap[i] = sc.nextInt();
                speed[i] = sc.nextInt();
            }

            int T = 0;
            int K = N / cap[T] + (N % cap[T] > 0 ? 1 : 0);
            int[][] ans = new int[K][];
            for (int i = 0; i < K; i++) {
                int[] truck = new int[2 + Math.min(N, cap[T])];
                truck[0] = T;
                truck[1] = Math.min(N, cap[T]);
                for (int j = 0; j < truck[1]; j++) {
                    truck[j + 2] = --N;
                }
                ans[i] = truck;
            }
            System.out.println(K);
            for (int i = 0; i < K; i++) {
                for (int j = 0; j < ans[i].length; j++) {
                    System.out.print(ans[i][j]);
                    System.out.print((j < ans[i].length - 1 ? " " : "\n"));
                }
            }
        } catch (Exception e) {

        }
    }

}
