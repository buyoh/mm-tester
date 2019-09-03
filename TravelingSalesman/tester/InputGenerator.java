import java.io.*;
import java.security.*;

public class InputGenerator
{
    final int MAXN   = 1000;
    final int MINN   = 50;
    final int WIDTH  = 1000 + 1;
    final int HEIGHT = 1000 + 1;

    int N;
    int[] posX;
    int[] posY;

    private void generate (long seed)
    {
        try {
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            rnd.setSeed(seed);
            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
            posX = new int[N];
            posY = new int[N];
            boolean [][] usedPos = new boolean[WIDTH][HEIGHT];
            for (int i = 0; i < N; i++) {
                while (true) {
                    int x = rnd.nextInt(WIDTH);
                    int y = rnd.nextInt(HEIGHT);
                    if (usedPos[x][y]) continue;
                    usedPos[x][y] = true;
                    posX[i] = x;
                    posY[i] = y;
                    break;
                }
            }
        } 
        catch (Exception e) {
            System.err.println("An exception occurred while generating test case.");
            e.printStackTrace();
        }
    }

    public InputGenerator (long seed)
    {
        generate(seed);
    }

}
