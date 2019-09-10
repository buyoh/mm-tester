import java.io.*;
import java.security.*;

public class InputData
{
    final int MAX_N = 500, MIN_N = 50;
    final int MAX_M = 10,  MIN_M = 3;
    final int MAX_CAP = 20, MIN_CAP = 5;
    final int MAX_SPEED = 20, MIN_SPEED = 1;
    final int WIDTH  = 1000 + 1;
    final int HEIGHT = 1000 + 1;

    final int N,M;
    final int depotX, depotY;
    final int[] posX, posY;
    final int[] cap;
    final int[] speed;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        sb.append(depotX).append(' ');
        sb.append(depotY).append('\n');
        for (int i = 0; i < N; i++) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n'); 
        }
        for (int i = 0; i < M; i++) {
            sb.append(cap[i]).append(' ');
            sb.append(speed[i]).append('\n'); 
        }
        return sb.toString();
    }

    public InputData (final long seed) throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        M = rnd.nextInt(MAX_M - MIN_M + 1) + MIN_M;
        depotX = rnd.nextInt(WIDTH);
        depotY = rnd.nextInt(HEIGHT);
        posX = new int[N];
        posY = new int[N];
        boolean[][] usedPos = new boolean[WIDTH][HEIGHT];
        usedPos[depotX][depotY] = true;
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
        cap   = new int[M];
        speed = new int[M];
        for (int i = 0; i < M; i++) {
            cap[i]   = rnd.nextInt(MAX_CAP - MIN_CAP + 1) + MIN_CAP;
            speed[i] = rnd.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED;
        }
    }
}
