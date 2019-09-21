import java.io.*;
import java.security.*;

public class Input
{
    final int MAXN   = 1000;
    final int MINN   = 50;
    final int WIDTH  = 1000 + 1;
    final int HEIGHT = 1000 + 1;

    final int N;
    final int[] posX;
    final int[] posY;

    public Input (final long seed) throws Exception
    {
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

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n');
        }
        return sb.toString();
    }
}
