import java.io.*;
import java.security.*;
import java.util.*;

public class SlidingPuzzle {

    public static void main (String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int M = sc.nextInt();
            int Board[][] = new int[N][M];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    Board[r][c] = sc.nextInt();
                }
            }
            System.out.println(N * M);
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    System.out.println(r + " " + c);
                }
            }
        }
        catch (Exception e) {

        }
    }

}
