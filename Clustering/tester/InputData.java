import java.io.*;
import java.security.*;

public class InputData
{
    final int MAXN   = 1000;
    final int MINN   = 100;
    final int MAXK   = 20;
    final int MINK   = 5;
    final int WIDTH  = 1000 + 1;
    final int HEIGHT = 1000 + 1;

    final int N;
    final int K;
    final int[] posX;
    final int[] posY;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(K).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n');
        }
        return sb.toString();
    }

    public InputData (final long seed) throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        N = rnd.nextInt(MAXN - MINN + 1) + MINN;
        K = rnd.nextInt(MAXK - MINK + 1) + MINK;
        posX = new int[N];
        posY = new int[N];
        boolean used[][] = new boolean[WIDTH][HEIGHT];
        int esum = 0;
        while (esum < N) {
            int x = rnd.nextInt(WIDTH);
            int y = rnd.nextInt(HEIGHT);
            if (used[x][y]) continue;
            used[x][y] = true;
            posX[esum] = x;
            posY[esum] = y;
            esum++;
        }
    }
}
