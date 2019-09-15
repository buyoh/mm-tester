import java.io.*;
import java.security.*;

public class InputData
{
    final int N = 400;
    final int RECT_MAX = 50;
    final int RECT_MIN = 5;

    final int[] h;
    final int[] w;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(w[i]).append(' ');
            sb.append(h[i]).append('\n');
        }
        return sb.toString();
    }

    public InputData (final long seed) throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        h = new int[N];
        w = new int[N];
        for (int i = 0; i < N; i++) {
            w[i] = rnd.nextInt(RECT_MAX - RECT_MIN + 1) + RECT_MIN;
            h[i] = rnd.nextInt(RECT_MAX - RECT_MIN + 1) + RECT_MIN;
        }
    }
}
