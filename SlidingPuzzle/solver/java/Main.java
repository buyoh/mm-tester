import java.io.*;
import java.security.*;
import java.util.*;

public class Main {

    public static void main (String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int Board[][] = new int[N][N];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    Board[r][c] = sc.nextInt();
                }
            }
            System.out.println(N * N);
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    System.out.println(r + " " + c);
                }
            }
        }
        catch (Exception e) {

        }
    }

}
