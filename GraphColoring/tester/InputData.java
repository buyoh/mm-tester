import java.io.*;
import java.security.*;

public class InputData
{
    final int N = 100;
    final int M;
    int[] a;
    int[] b;
    boolean[][] edge;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(a[i]).append(' ');
            sb.append(b[i]).append('\n');
        }
        return sb.toString();
    }

    public InputData (final long seed) throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        M = rnd.nextInt(N * (N - 1) / 4 - 2 * N) + 2 * N;
        a = new int[M];
        b = new int[M];
        edge = new boolean[N][N];
        int esum = 0;
        while (esum < M) {
            int at = rnd.nextInt(N);
            int bt = rnd.nextInt(N);
            if (at == bt || edge[at][bt]) continue;
            edge[at][bt] = true;
            edge[bt][at] = true;
            a[esum] = at;
            b[esum] = bt;
            esum++;
        }
    }
}
