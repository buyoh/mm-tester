import java.util.Scanner;
import java.util.ArrayList;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester
{
    @JsonIgnore public final int MAXN   = 200;
    @JsonIgnore public final int MINN   = 10;
    @JsonIgnore public final int MAXM   = 1000;
    @JsonIgnore public final int MINM   = 0;
    @JsonIgnore public final int WIDTH  = 1000 + 1;
    @JsonIgnore public final int HEIGHT = 1000 + 1;

    public final long seed;
    @JsonIgnore public final int N,M;
    @JsonIgnore public final int[] x,y;
    @JsonIgnore public final int[] ax,ay;
    @JsonIgnore public LineSegment[] MST;
    @JsonIgnore private double score_t = -2.0;

    @JsonIgnore
    public String getInputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(ax[i]).append(' ');
            sb.append(ay[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public LineSegment getLineSegment (int idx1, int idx2)
    {
        int x1 = idx1 < N ? x[idx1] : ax[idx1 - N];
        int y1 = idx1 < N ? y[idx1] : ay[idx1 - N];
        int x2 = idx2 < N ? x[idx2] : ax[idx2 - N];
        int y2 = idx2 < N ? y[idx2] : ay[idx2 - N];
        return new LineSegment(x1, y1, x2, y2);
    }

    public double getScore ()
    {
        if (score_t >= -1.0) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        if (M < MINM || M > MAXM) {
            System.err.println("The number of added vertices must be between " + MINM + 
                                " and " + MAXM + ", but your output is " + M + ".");
            return score_t = -1.0;
        }

        boolean[][] used = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < N; i++) {
            used[x[i]][y[i]] = true;
        }
        for (int i = 0; i < M; i++) {
            if (ax[i] < 0 || ay[i] < 0 || ax[i] >= WIDTH || ay[i] >= HEIGHT) {
                System.err.println("All vertex coordinates must be 0 <= xi,yi <= 1000, " +
                                   "but your output includes (" + ax[i] + ", " + ay[i] + ").");
                return score_t = -1.0;
            }
            if (used[ax[i]][ay[i]]) {
                System.err.println("All vertices must not have duplicate coordinates, " + 
                                   "but your output will have multiple vertices at (" + 
                                   ax[i] + ", " + ay[i] + ").");
                return score_t = -1.0;
            }
            used[ax[i]][ay[i]] = true;
        }

        /* Calculate the score. */
        class Tuple {
            public Double dist;
            public Integer a,b;
            public Tuple (double _d, int _a, int _b) {
                dist = _d;
                a = _a;
                b = _b;
            }
        }

        ArrayList<Tuple> order = new ArrayList<Tuple>();
        for (int i = 0; i < N + M; i++) {
            for (int j = i + 1; j < N + M; j++) {
                LineSegment e = getLineSegment(i, j);
                order.add(new Tuple(e.calcDist(), i, j));
            }
        }
        order.sort((a, b) -> a.dist.compareTo(b.dist));

        double cost = 0.0;
        ArrayList<LineSegment> tree = new ArrayList<LineSegment>();
        DisjointSet ds = new DisjointSet(N + M);
        for (int i = 0; i < order.size(); i++) {
            int at = order.get(i).a;
            int bt = order.get(i).b;
            if (!ds.same(at, bt)) {
                ds.unite(at, bt);
                tree.add(getLineSegment(at, bt));
                cost += order.get(i).dist;
            }
        }
        MST = new LineSegment[tree.size()];
        tree.toArray(MST);

        return score_t = cost;
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

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        M = sc.nextInt();
        ax = new int[M];
        ay = new int[M];
        for (int i = 0; i < M; i++) {
            ax[i] = sc.nextInt();
            ay[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }
    }
}
