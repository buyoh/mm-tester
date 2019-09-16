import java.io.*;
import java.security.*;

public class InputData
{
    final int MAXN   = 1000;
    final int MINN   = 20;
    final int MAXR   = 100;
    final int MINR   = 10;
    final int WIDTH  = 1000 + 1;
    final int HEIGHT = 1000 + 1;

    int N,R;
    int[] px;
    int[] py;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(R).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(px[i]).append(' ');
            sb.append(py[i]).append('\n');
        }
        return sb.toString();
    }

    public InputData (final long seed) throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        N = rnd.nextInt(MAXN - MINN + 1) + MINN;
        R = rnd.nextInt(MAXR - MINR + 1) + MINR;
        px = new int[N];
        py = new int[N];
        boolean [][] usedPos = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < N; i++) {
            while (true) {
                int x = rnd.nextInt(WIDTH);
                int y = rnd.nextInt(HEIGHT);
                if (usedPos[x][y]) continue;
                usedPos[x][y] = true;
                px[i] = x;
                py[i] = y;
                break;
            }
        }
    }
}
