import java.io.*;
import java.util.*;

public class OutputData
{
    final int[] centorX;
    final int[] centorY;
    final int[] belongV;
    final double score;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < centorX.length; ++i) {
            sb.append(centorX[i]).append(' ');
            sb.append(centorY[i]).append('\n');
        }
        return sb.toString();
    }

    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        centorX = new int[input.K];
        centorY = new int[input.K];
        for (int i = 0; i < input.K; i++) {
            centorX[i] = sc.nextInt();
            centorY[i] = sc.nextInt();
        }

        /* Calculate the score. */
        double sum = 0.0;
        belongV = new int[input.N];
        for (int i = 0; i < input.N; i++) {
            int idx = -1;
            double dist = 1.0e9;
            for (int j = 0; j < input.K; j++) {
                int lx = Math.abs(input.posX[i] - centorX[j]);
                int ly = Math.abs(input.posY[i] - centorY[j]);
                double dt = Math.sqrt((double)(lx * lx + ly * ly));
                if (dist > dt) {
                    dist = dt;
                    idx = j;
                }
            }
            sum += dist;
            belongV[i] = idx;
        }

        /* Check whether the output satisfies the constraints. */
        boolean [][] used = new boolean[input.WIDTH][input.HEIGHT];
        for (int i = 0; i < input.K; i++) {
            if (centorX[i] < 0 || centorY[i] < 0 || centorX[i] >= input.WIDTH || centorY[i] >= input.HEIGHT) {
                System.err.println("The coordinate x = " + centorX[i] + ", y = " + centorY[i] + " is out of range.");
                score = -1.0;
                return;
            }
            if (used[centorX[i]][centorY[i]]) {
                System.err.println("The coordinate x = " + centorX[i] + ", y = " + centorY[i] + " is duplicated.");
                score = -1.0;
                return;
            }
            used[centorX[i]][centorY[i]] = true;
        }
        
        score = sum;
    }
}
