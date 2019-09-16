import java.io.*;
import java.util.*;

public class OutputData
{
    final int M;
    final int[] rx;
    final int[] ry;
    final int score;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M);
        for (int i = 0; i < M; ++i) {
            sb.append(rx[i]).append(' ');
            sb.append(ry[i]).append('\n');
        }
        return sb.toString();
    }

    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        M = sc.nextInt();
        rx = new int[M];
        ry = new int[M];
        for (int i = 0; i < M; i++) {
            rx[i] = sc.nextInt();
            ry[i] = sc.nextInt();
        }

        /* Check whether the output satisfies the constraints. */
        for (int i = 0; i < M; i++) {
            if (rx[i] < 0 || ry[i] < 0 || rx[i] >= input.WIDTH || ry[i] >= input.HEIGHT) {
                System.err.println("The coordinates of the disk must satisfy 0 <= xi,yi <= 1000, but your output " +
                                   "is the coordinates of the " + i + " disk is (" + rx[i] + "," + ry[i] + ").");
                score = -1;
                return;
            }
        }
        for (int i = 0; i < input.N; i++) {
            boolean flag = false;
            for (int j = 0; j < M; j++) {
                int dx = Math.abs(input.px[i] - rx[j]);
                int dy = Math.abs(input.py[i] - ry[j]);
                if (dx * dx + dy * dy <= input.R * input.R) {
                    flag = true;
                }
            }
            if (!flag) {
                System.err.println("Point at coordinate x = " + input.px[i] + ", y = " + input.py[i] +
                                   " is not covered by a disk.");
                score = -1;
                return;
            }
        }

        /* Calculate the score. */
        score = M;
    }
}
