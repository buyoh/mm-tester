import java.util.Scanner;
import java.util.ArrayList;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int MAXN   = 1000;
    @JsonIgnore public final int MINN   = 50;
    @JsonIgnore public final int WIDTH  = 1000 + 1;
    @JsonIgnore public final int HEIGHT = 1000 + 1;

    public final long seed;
    @JsonIgnore public final int N,M;
    @JsonIgnore public final int[] x,y;
    @JsonIgnore public final int[] a,b;
    @JsonIgnore public final boolean[][] connect;
    @JsonIgnore public final int K;
    @JsonIgnore public final int[] v;
    @JsonIgnore private double score_t = -2.0;

    @JsonIgnore
    public String getInputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        for (int i = 0; i < M; i++) {
            sb.append(a[i]).append(' ');
            sb.append(b[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(K).append('\n');
        for (int i = 0; i < v.length; ++i) {
            sb.append(v[i]).append('\n');
        }
        return sb.toString();
    }

    public double getScore ()
    {
        if (score_t >= -1.0) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        boolean[] used = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (v[i] < 0 || v[i] >= N) {
                System.err.println("All elements of your return must be between 0 and " +
                                   (N - 1) + ", and your return contained " + v[i] + ".");
                return score_t = -1.0;
            }
            if (i > 0 && !connect[a[v[(i - 1 + K) % K]]][b[v[i]]]) {
                System.err.println("There is no edge connecting vertex " + v[i - 1] + 
                                   " and vertex " + v[i] + ".");
                return score_t = -1.0;
            }
            if (used[v[i]]) {
                System.err.println("All elements of your return must be unique, " +
                                   "and your return contained " + v[i] + " twice.");
                return score_t = -1.0;
            }
            used[v[i]] = true;
        }

        /* Calculate the score. */
        double dist = 0.0;
        for (int i = 1; i < N; i++) {
            double dx = (double)(x[v[i - 1]] - x[v[i]]);
            double dy = (double)(y[v[i - 1]] - y[v[i]]);
            dist += Math.sqrt(dx * dx + dy * dy);
        }
        return score_t = dist;
    }

    private boolean intersect (
        int l1_ax, int l1_ay, int l1_bx, int l1_by,
        int l2_ax, int l2_ay, int l2_bx, int l2_by)
    {
        int ta = (l2_ax - l2_bx) * (l1_ay - l2_ay) + (l2_ay - l2_by) * (l2_ax - l1_ax);
        int tb = (l2_ax - l2_bx) * (l1_by - l2_ay) + (l2_ay - l2_by) * (l2_ax - l1_bx);
        int tc = (l1_ax - l1_bx) * (l2_ay - l1_ay) + (l1_ay - l1_by) * (l1_ax - l2_ax);
        int td = (l1_ax - l1_bx) * (l2_by - l1_ay) + (l1_ay - l1_by) * (l1_ax - l2_bx);
        if (ta * tb < 0 && tc * td < 0) {
            return true;
        } else {
            return false;
        }
    }

    public Tester (final long _seed, final String exec) throws Exception
    {
        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(_seed);
        N = rnd.nextInt(MAXN - MINN + 1) + MINN;
        x = new int[N];
        y = new int[N];
        boolean [][] usedPos = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < N; i++) {
            while (true) {
                int xt = rnd.nextInt(WIDTH);
                int yt = rnd.nextInt(HEIGHT);
                if (usedPos[xt][yt]) continue;
                usedPos[xt][yt] = true;
                x[i] = xt;
                y[i] = yt;
                break;
            }
        }
        ArrayList<Integer> edges = new ArrayList<Integer>();
        for (int i = 0; i < N * N; i++) {
            int a1 = i / N;
            int b1 = i % N;
            boolean cross = false;
            for (int j = 0; j < edges.size(); j++) {
                int a2 = edges.get(j) / N;
                int b2 = edges.get(j) % N;
                if (intersect(x[a1], y[a1], x[b1], y[b1], x[a2], y[a2], x[b2], y[b2])) {
                    cross = true;
                    break;
                }
            }
            if (!cross) {
                edges.add(i);
            }
        }
        M = edges.size();
        a = new int[M];
        b = new int[M];
        connect = new boolean[N][N];
        for (int i = 0; i < edges.size(); i++) {
            a[i] = edges.get(i) / N;
            b[i] = edges.get(i) % N;
            connect[a[i]][b[i]] = true;
            connect[b[i]][a[i]] = true;
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        K = sc.nextInt();
        v = new int[K];
        for (int i = 0; i < K; i++) {
            v[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }
    }
}
