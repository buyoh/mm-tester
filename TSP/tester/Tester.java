import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.security.*;
import javax.swing.*;
import javax.imageio.*;

public class Tester {

    public class Visualizer extends JPanel {

    }

    /********************************************************************/
    InputStream is;
    OutputStream os;
    BufferedReader br;

    static Process proc;
    static String fileName, exec;
    static boolean save, vis, debug;

    final int MAXN = 30, MINN = 20;
    final int SIZE = 100;
    int N;
    int [] posX, posY;
    int [] perm;

    /********************************************************************/

    public void generate (String seedStr) {
        try {  
            
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);

            N = rnd.nextInt(MAXN - MINN) + MINN;
            perm = new int[N];
            posX = new int[N];
            posY = new int[N];
            boolean [][] usedPos = new boolean[SIZE][SIZE];

            for (int i = 0; i < N; ++i) {
                int x = -1, y = -1;
                do {
                    x = rnd.nextInt(SIZE);
                    y = rnd.nextInt(SIZE);
                } while (usedPos[x][y]);
                posX[i] = x;
                posY[i] = y;
                usedPos[x][y] = true;
            }

            if (debug) {
                System.out.println("=================\nN = " + N);
                System.out.println("=================\nposition");
                for (int i = 0; i < N; ++i) {
                    System.out.println(posX[i] + " " + posY[i]);
                }
            }

        } catch (Exception e) {
            //addFatalError("An exception occurred while generating test case.");
            e.printStackTrace();
        }

    }

    public double runTest (String seed) {
        try {
            generate(seed);
            if (proc != null) {
                try {
                    perm = getPermutation();
                    boolean [] used = new boolean[N];
                    for (int i = 0; i < N; ++i) {
                        if (perm[i] < 0 || perm[i] >= N) {
                            //addFatalError("All elements of your return must be between 0 and " + (N-1) + ", and your return contained " + perm[i] + ".");
                            return -1;
                        }
                        if (used[perm[i]]) {
                            //addFatalError("All elements of your return must be unique, and your return contained " + perm[i] + " twice.");
                            return -1;
                        }
                        used[perm[i]] = true;
                    }
                } catch (Exception e) {
                    //addFatalError("Failed to get result from permute.");
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        if (debug) {
            System.out.println("=================\nresut");
            for (int i = 0; i < perm.length; i++) {
                System.out.println(perm[i]);
            }
        }

        double score = 0.0;
        for (int i = 0; i < N; i++) {
            double dx = (double)(posX[perm[i]] - posX[perm[(i + 1) % N]]);
            double dy = (double)(posY[perm[i]] - posY[perm[(i + 1) % N]]);
            score += Math.sqrt(dx * dx + dy * dy);
        }
        
        return score;
    }

    private int [] getPermutation () throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n');
        }
        os.write(sb.toString().getBytes());
        os.flush();

        int [] ret = new int[N];
        for (int i = 0; i < N; ++i) {
            ret[i] = Integer.parseInt(br.readLine());
        }
        return ret;
    }

    public Tester (String seed) {
        if (exec != null) {
            try {
                Runtime rt = Runtime.getRuntime();
                proc = rt.exec(exec);
                os = proc.getOutputStream();
                is = proc.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                //new ErrorReader(proc.getErrorStream()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Score = " + runTest(seed));
        if (proc != null) {
            try { 
                proc.destroy(); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            }
        }
    }

    public static void main (String[] args) {
        String seed = "1";
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-seed")) {
            	seed = args[++i];
            } else if (args[i].equals("-exec")) {
                exec = args[++i];
            } else if (args[i].equals("-vis")) {
                vis = true;
            } else if (args[i].equals("-save")) {
                save = true;
            } else if (args[i].equals("-debug")) {
                debug = true;
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}