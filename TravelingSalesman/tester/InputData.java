/**
 * Class that generates input data.
 * @author kosakkun
 */

import java.io.*;
import java.security.*;

public class InputData
{
    final int MAXN   = 1000;
    final int MINN   = 50;
    final int WIDTH  = 1000 + 1;
    final int HEIGHT = 1000 + 1;

    final int N;
    final int[] posX;
    final int[] posY;

    /**
     * The input data is converted to a character string and returned.
     * @return Input data in character string format.
     */
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

    /**
     * Generate input data from a random seed.
     * @param seed Random number seed.
     */
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
