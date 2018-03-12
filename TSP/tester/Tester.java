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

    final int MAXN = 200, MINN = 20;
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

            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
            perm = new int[N];
            posX = new int[N];
            posY = new int[N];
            boolean [][] usedPos = new boolean[N][N];

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
                System.out.println("N = " + N);
                for (int i = 0; i < N; ++i) {
                    System.out.println(posX[i] + " " + posY[i]);
                }
            }

        } catch (Exception e) {
            //addFatalError("An exception occurred while generating test case.");
            e.printStackTrace();
        }

    }

    public double runTest () {

        return -1.0;
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

        int nRet = Integer.parseInt(br.readLine());
        int [] ret = new int[nRet];
        for (int i = 0; i < nRet; ++i) {
            ret[i] = Integer.parseInt(br.readLine());
        }

        return ret;
    }

    public Tester (String seed) {
        generate(seed);
        System.out.println(runTest());
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