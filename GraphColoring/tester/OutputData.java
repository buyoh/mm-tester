import java.io.*;
import java.util.*;

public class OutputData
{
    final int[] col;
    final int score;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < col.length; ++i) {
            sb.append(col[i]).append('\n');
        }
        return sb.toString();
    }

    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        col = new int[input.N];
        for (int i = 0; i < input.N; i++) {
            col[i] = sc.nextInt();
        }

        /* Check whether the output satisfies the constraints. */
        for (int i = 0; i < input.M; i++) {
            if (col[input.a[i]] == col[input.b[i]]) {
                System.err.println("Vertex " + input.a[i] + " and vertex " + input.b[i] +
                                   " are connected, but your output is the same color.");
                score = -1;
                return;
            }
        }

        /* Calculate the score. */
        int cnum = 0;
        boolean[] used = new boolean[input.N];
        for (int i = 0; i < input.N; i++) {
            if (!used[col[i]]) {
                used[col[i]] = true;
                cnum++;
            }
        }
        score = cnum;
    }
}
