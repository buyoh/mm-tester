import java.io.*;
import java.util.*;

public class OutputData
{
    final int K;
    final int[] T;
    final int[] L;
    final int[][] D;
    final double[] dist;
    final double[] time;
    final int[] last_idx;
    final double score;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(K).append('\n');
        for (int i = 0; i < K; i++) {
            sb.append(T[i]);
            sb.append(' ').append(L[i]);
            for (int j = 0; j < L[i]; j++) {
                sb.append(' ').append(D[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private double calcDist (int x1, int y1, int x2, int y2)
    {
        double lx = (double)(x1 - x2);
        double ly = (double)(y1 - y2);
        return Math.sqrt(lx * lx + ly * ly);
    }

    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        /* Get the output from the solver. */
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        K = sc.nextInt();
        T = new int[K];
        L = new int[K];
        D = new int[K][];
        for (int i = 0; i < K; i++) {
            T[i] = sc.nextInt();
            L[i] = sc.nextInt();
            D[i] = new int[L[i]];
            for (int j = 0; j < L[i]; j++) {
                D[i][j] = sc.nextInt();
            }
        }

        dist = new double[input.M];
        time = new double[input.M];
        last_idx = new int[input.M];
        Arrays.fill(last_idx, -1);

        /* Check whether the output satisfies the constraints. */
        boolean[] used = new boolean[input.N];
        for (int i = 0; i < K; i++) {
            if (T[i] < 0 || T[i] >= input.M) {
                System.err.println("The track number must be between 0 and " + (input.M - 1)
                                   + ", and your output contains " + T[i] + ".");
                score = -1.0;
                return;
            }
            if (L[i] <= 0 || L[i] > input.cap[T[i]]) {
                System.err.println("The load on truck " + T[i] + " must be between 1 and " + input.cap[T[i]]
                                   + ", but in your output you are trying to load " + L[i] + " pieces of luggage.");
                score = -1.0;
                return;
            }
            for (int j = 0; j < L[i]; j++) {
                int idx = D[i][j];
                if (!used[idx]) {
                    used[idx] = true;
                    continue;
                }
                System.err.println("Your delivery destination must be visited once, but your output has visited"
                                   + idx + " more than once.");
                score = -1.0;
                return;
            }
        }   
        for (int i = 0; i < input.N; i++) {
            if (!used[i]) {
                System.err.println("The delivery destination must visit everything from 0 to "
                                   + (input.N - 1) + ", and your output does not include " + i + ".");
                score = -1.0;
                return;
            }
        }

        /* Calculate the score. */
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < L[i]; j++) {
                if (j == 0 && last_idx[T[i]] < 0) {
                    dist[T[i]] += calcDist(input.depotX, input.depotY, input.posX[D[i][j]], input.posY[D[i][j]]);
                }  else if (j == 0) {
                    dist[T[i]] += calcDist(input.posX[last_idx[T[i]]], input.posY[last_idx[T[i]]], input.depotX, input.depotY);
                    dist[T[i]] += calcDist(input.depotX, input.depotY, input.posX[D[i][j]], input.posY[D[i][j]]);
                } else {
                    dist[T[i]] += calcDist(input.posX[last_idx[T[i]]], input.posY[last_idx[T[i]]], input.depotX, input.depotY);
                }
                last_idx[T[i]] = D[i][j];
            }
        }
        double max_time = -1.0;
        for (int i = 0; i < input.M; i++) {
            time[i] = dist[i] / (double)input.speed[i];
            max_time = Math.max(max_time, time[i]);
        }
        score = max_time;
    }
}
