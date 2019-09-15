import java.io.*;
import java.security.*;
import java.util.*;

public class Main {

    public static void main (String[] args) {
        try 
        {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            final int size = 100;
            boolean used[][] = new boolean[size][size];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                used[x][y] = true;
            }
            System.out.println(size * size - N);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (!used[x][y]) {
                        System.out.println(x + " " + y);
                    }
                }
            }
        }
        catch (Exception e) 
        {

        }
    }

}
