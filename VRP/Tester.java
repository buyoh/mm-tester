import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.security.*;
import javax.swing.*;
import javax.imageio.*;

public class Tester {

    public class Visualizer extends JPanel implements WindowListener {
    	
        public void paint(Graphics g) {

            try {
                
                /*if (save) {
                    ImageIO.write(bi, "png", new File(fileName +".png"));
                }*/

            } catch (Exception e) { 
                e.printStackTrace();
            }
        }

        public Visualizer () {
            jf.addWindowListener(this);
        }

        public void windowClosing(WindowEvent e) {
            if (proc != null) {
            	try { 
            		proc.destroy();
            	} catch (Exception ex) {
            		ex.printStackTrace();
            	}
            }
            System.exit(0);
        }

        public void windowActivated(WindowEvent e) { }
        public void windowDeactivated(WindowEvent e) { }
        public void windowOpened(WindowEvent e) { }
        public void windowClosed(WindowEvent e) { }
        public void windowIconified(WindowEvent e) { }
        public void windowDeiconified(WindowEvent e) { }

    }

    /********************************************************************/

    JFrame jf;
    Visualizer v;
    InputStream is;
    OutputStream os;
    BufferedReader br;

    static Process proc;
    static String fileName, exec;
    static boolean save, vis, numb;

    final int SIZE = 50 + 1;
    final int SIZE_VIS_X = SIZE * 10;
    final int SIZE_VIS_Y = SIZE * 10;
    final int MAXN = 200, MINN = 50;
    final int MAXM = 10,  MINM = 3;
    final int MAX_CAP = 10, MIN_CAP = 3;
    final int MAX_SPEED = 20, MIN_SPEED = 1;

    int N,M;
    int depotX, depotY;
    int [] posX, posY;
    int [] cap;
    int [] speed;
    int [][] perm;

    /********************************************************************/

    public void generate (String seedStr) {

        try {

            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);

            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
            M = rnd.nextInt(MAXM - MINM + 1) + MINM;

            depotX = rnd.nextInt(SIZE);
            depotY = rnd.nextInt(SIZE);
            boolean [][] usedPos = new boolean[SIZE][SIZE];
            usedPos[depotX][depotY] = true;
            posX = new int[N];
            posY = new int[N];
            for (int i = 0; i < N; i++) {
                int x,y;
                do {
                    x = rnd.nextInt(SIZE);
                    y = rnd.nextInt(SIZE);
                } while (usedPos[x][y]);
                usedPos[x][y] = true;
                posX[i] = x;
                posY[i] = y;
            }

            cap   = new int[M];
            speed = new int[M];
            for (int i = 0; i < M; i++) {
                cap[i]   = rnd.nextInt(MAX_CAP - MIN_CAP + 1) + MIN_CAP;
                speed[i] = rnd.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED;
            }
            
        } catch (Exception e) {
            System.err.println("An exception occurred while generating test case.");
            e.printStackTrace();
        }

    }

    public double runTest (String seed) {

        try {
            generate(seed);
            if (proc != null) try {
                perm = getPermutation();
            } catch (Exception e) {

                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        if (vis) {
            jf.setSize(SIZE_VIS_X, SIZE_VIS_Y);
            jf.setVisible(true);
        }
        
        double score = -1.0;

        return score;
    }

    private int [][] getPermutation () throws IOException {
        
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        sb.append(depotX).append(' ');
        sb.append(depotY).append('\n');
        for (int i = 0; i < N; i++) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n'); 
        }
        sb.append(M).append('\n');
        for (int i = 0; i < M; i++) {
            sb.append(cap[i]).append(' ');
            sb.append(speed[i]).append('\n'); 
        }
        os.write(sb.toString().getBytes());
        os.flush();

        int [][] ret = new int[M][];
        for (int i = 0; i < M; ++i) {
            String[] vehicle = br.readLine().split("[\\s]+");
            int L = Integer.parseInt(vehicle[0]);
            ret[i] = new int[L];
            for (int j = 0; j < L; j++) {
                ret[i][j] = Integer.parseInt(vehicle[i + 1]);
            }
        }
        return ret;
    }

    public Tester (String seed) {
    	if (vis) {
    		jf = new JFrame();
            v = new Visualizer();
            jf.getContentPane().add(v);
        }
        if (exec != null) {
            try {
                Runtime rt = Runtime.getRuntime();
                proc = rt.exec(exec);
                os = proc.getOutputStream();
                is = proc.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
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
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}