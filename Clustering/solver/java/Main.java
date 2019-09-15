import java.io.*;
import java.security.*;
import java.util.*;

public class Main {

    public static void main (String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int K = sc.nextInt();
            int x[] = new int[N];
            int y[] = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            for (int i = 0; i < K; ++i) {
                System.out.println((i / 4 * 200 + 100) + " " + (i % 4 * 250 + 100));
            }
        }
        catch (Exception e) {

        }
    }

}
