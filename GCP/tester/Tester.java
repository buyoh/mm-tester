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
                
                if (save) {
                    ImageIO.write(bi, "png", new File(fileName +".png"));
                }

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
    static boolean save, vis;

    final int MAXN = 200, MINN = 20;
    int VIS_SIZE;
    int N,M,L;
    boolean [][] G;
    int [] perm;

    /********************************************************************/

    public void generate (String seedStr) {

        try {   
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);

            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
            M = 0;
            L = rnd.nextInt(N / 2) + 1;
            G = new boolean[N][N];
            for (int i = 0; i < L; i++) {
                for (int idx = 0; idx < N; idx++) {
                    int to = rnd.nextInt(N);
                    while (to == idx) to = rnd.nextInt(N);
                    if (!G[idx][to]) {
                        G[idx][to] = true;
                        G[to][idx] = true;
                    }
                }
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
                System.err.println("Failed to get result from permute.");
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        if (vis) {
            jf.setSize(VIS_SIZE, VIS_SIZE);
            jf.setVisible(true);
        }

        double score = -1;
        
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
                vis = true;
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}
