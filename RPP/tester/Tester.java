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
    Scanner sc;

    static Process proc;
    static String fileName, exec;
    static boolean save, vis;

    final int N = 400;
    final int RECT_MAX = 5, RECT_MIN = 1;
    final int BOX_SIZE = 100;
    int [] h, w;
    int [] dir;
    int [] posX, posY;

    /********************************************************************/

    public void generate (String seedStr) {

        try {   
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);

            h = new int[N];
            w = new int[N];
            for (int i = 0; i < N; i++) {
                h[i] = rnd.nextInt(RECT_MAX - RECT_MIN + 1) + RECT_MIN;
                w[i] = rnd.nextInt(RECT_MAX - RECT_MIN + 1) + RECT_MIN;
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
                getPermutation();
                boolean [][] used = new boolean[BOX_SIZE][BOX_SIZE];
                for (int i = 0; i < N; i++) {
                    int a = (dir[i] ? w[i] : h[i]);
                    int b = (dir[i] ? h[i] : w[i]);
                    for (int x = posX[i]; x < posX[i] + a; x++) {
                        for (int y = posY[i]; y < posY[i] + b; y++) {
                            if (used[x][y]) {
                                System.err.println("There are overlapping rectangles.");
                                return -1;
                            }
                            if (x < 0 || y < 0 || x >= BOX_SIZE || y >= BOX_SIZE) {
                                System.err.println("There is a rectangle which does not fit in the box.");
                                return -1;
                            }
                            used[x][y] = true;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to get result.");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        if (vis) {
            jf.setSize((SIZE + 4) * 10, (SIZE + 6) * 10);
            jf.setVisible(true);
        }
        
        double score = -1.0;
        //boolean [][] usedPos = new boolean[BOX_SIZE + 1][BOX_SIZE + 1];


        return score;
    }

    private void getPermutation () throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(h[i]).append(' ');
            sb.append(w[i]).append('\n');
        }
        os.write(sb.toString().getBytes());
        os.flush();

        dir  = new int[N];
        posX = new int[N];
        posY = new int[N]; 
        for (int i = 0; i < N; ++i) {
            dir[i]  = sc.nextInt();
            posX[i] = sc.nextInt();
            posY[i] = sc.nextInt();
        }
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
                sc = new Scanner(is);
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
