import java.io.*;
import java.security.*;
import java.util.*;

public class Main {

    public static void main (String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int M = sc.nextInt();
            int[] x = new int[N];
            int[] y = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            int[] a = new int[M];
            int[] b = new int[M];
            for (int i = 0; i < M; i++) {
                a[i] = sc.nextInt();
                b[i] = sc.nextInt();
            }
            int K = 2;
            System.out.println(K);
            System.out.println(a[0]);
            System.out.println(b[0]);
        }
        catch (Exception e) {

        }
    }

}
