import java.io.*;
import java.util.*;

public class OutputData
{
    final int[] perm;
    final double score;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < perm.length; ++i) {
            sb.append(perm[i]).append('\n');
        }
        return sb.toString();
    }

    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        perm = new int[input.N];
        for (int i = 0; i < input.N; i++) {
            perm[i] = sc.nextInt();
        }

        /* Check whether the output satisfies the constraints. */
        boolean[] used = new boolean[input.N];
        for (int i = 0; i < input.N; i++) {
            if (perm[i] < 0 || perm[i] >= input.N) {
                System.err.println("All elements of your return must be between 0 and " +
                                   (input.N - 1) + ", and your return contained " + perm[i] + ".");
                score = -1.0;
                return;
            }
            if (used[perm[i]]) {
                System.err.println("All elements of your return must be unique, " +
                                   "and your return contained " + perm[i] + " twice.");
                score = -1.0;
                return;
            }
            used[perm[i]] = true;
        }

        /* Calculate the score. */
        double dist = 0.0;
        for (int i = 0; i < input.N; i++) {
            double dx = (double)(input.posX[perm[i]] - input.posX[perm[(i + 1) % input.N]]);
            double dy = (double)(input.posY[perm[i]] - input.posY[perm[(i + 1) % input.N]]);
            dist += Math.sqrt(dx * dx + dy * dy);
        }
        score = dist;
    }
}
