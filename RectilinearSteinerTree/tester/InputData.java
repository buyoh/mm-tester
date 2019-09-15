import java.io.*;
import java.security.*;

public class InputData
{
    final int[] dx = {1, 0, -1, 0};
    final int[] dy = {0, 1, 0, -1};
    final int MAXN   = 200;
    final int MINN   = 10;
    final int WIDTH  = 100;
    final int HEIGHT = 100;

    final int N;
    final int[] posX;
    final int[] posY;

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

    public InputData (final long seed) throws Exception
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
}
