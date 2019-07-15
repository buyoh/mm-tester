import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.security.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.HashMap;

public class Tester {

    /********************************************************************/

    JFrame jf;
    Visualizer v;
    InputStream is;
    OutputStream os;
    Scanner sc;

    static Process proc;
    static String fileName, exec;
    static boolean vis,save;
    static int sel_ver;
    static boolean press;

    final int MAXN = 1000, MINN = 100;
    final int MAXK = 20, MINK = 5;
    final int VIS_SIZE = 1000;
    double Score;
    int N,K;
    int posX[],posY[];
    int retX[],retY[];

    /********************************************************************/

    public class Visualizer extends JPanel implements WindowListener {
        
        public void paint(Graphics g) {
            try {
                BufferedImage bi = new BufferedImage(VIS_SIZE + 20, VIS_SIZE + 20, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = (Graphics2D)bi.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(0xD3D3D3));
                g2.fillRect(0, 0, VIS_SIZE + 20, VIS_SIZE + 20);
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(10, 10, VIS_SIZE, VIS_SIZE);

                Color [] dotColor = new Color[N];
                for (int i = 0; i < N; i++) {
                    int idx = -1;
                    double dist = 1.0e9;
                    for (int j = 0; j < K; j++) {
                        int lx = Math.abs(posX[i] - retX[j]);
                        int ly = Math.abs(posY[i] - retY[j]);
                        double dt = Math.sqrt((double)(lx * lx + ly * ly));
                        if (dist > dt) {
                            dist = dt;
                            idx = j;
                        }
                    }

                    dotColor[i] = Color.getHSBColor((1.0f / (float)K) * (float)idx, 1.0f, 0.95f);
                    g2.setColor(new Color(0x000000));
                    g2.setStroke(new BasicStroke(1.0f));
                    g2.drawLine(10 + posX[i], 10 + posY[i], 10 + retX[idx], 10 + retY[idx]);
                }

                for (int i = 0; i < N; i++) {
                    g2.setColor(dotColor[i]);
                    g2.fillOval(posX[i] + 6, posY[i] + 6, 8, 8);
                    g2.setColor(new Color(0x000000));
                    g2.drawOval(posX[i] + 6, posY[i] + 6, 8, 8);
                }

                for (int i = 0; i < K; i++) {
                    Color c = Color.getHSBColor((1.0f / (float)K) * (float)i, 1.0f, 0.95f);
                    g2.setColor(c);
                    g2.fillOval(retX[i] + 4, retY[i] + 4, 12, 12);
                    g2.setColor(new Color(0x000000));
                    g2.drawOval(retX[i] + 4, retY[i] + 4, 12, 12);
                }

                g.drawImage(bi, 0, 0, VIS_SIZE + 20, VIS_SIZE + 20, null);
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

    public void generate (String seedStr) {
        try {
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);
            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
            K = rnd.nextInt(MAXK - MINK + 1) + MINK;
            posX = new int[N];
            posY = new int[N];
            boolean used[][] = new boolean[1001][1001];
            int esum = 0;
            while (esum < N) {
                int x = rnd.nextInt(1001);
                int y = rnd.nextInt(1001);
                if (used[x][y]) continue;
                used[x][y] = true;
                posX[esum] = x;
                posY[esum] = y;
                esum++;
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
                getPos();
                boolean [][] used = new boolean[1001][1001];
                for (int i = 0; i < K; i++) {
                    if (retX[i] < 0 || retY[i] > 1000) {
                        System.err.println("The coordinate x = " + retX[i] + ", y = " + retY[i] + " is out of range.");
                        return -1;
                    }
                    if (used[retX[i]][retY[i]]) {
                        System.err.println("The coordinate x = " + retX[i] + ", y = " + retY[i] + " is duplicated.");
                        return -1;
                    }
                    used[retX[i]][retY[i]] = true;
                }
            } catch (Exception e) {
                System.err.println("Failed to get result from output.");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        double Score = 0.0;
        for (int i = 0; i < N; i++) {
            double dist = 1.0e9;
            for (int j = 0; j < K; j++) {
                int lx = Math.abs(posX[i] - retX[j]);
                int ly = Math.abs(posY[i] - retY[j]);
                double dt = Math.sqrt((double)(lx * lx + ly * ly));
                dist = Math.min(dist, dt);
            }
            Score += dist;
        }

        if (vis) {
            jf.getContentPane().setPreferredSize(new Dimension(VIS_SIZE + 20, VIS_SIZE + 20));
            jf.pack();
            jf.setVisible(true);
        }

        return Score;
    }

    private void getPos () throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(K).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n');
        }
        os.write(sb.toString().getBytes());
        os.flush();

        retX = new int[K];
        retY = new int[K];
        for (int i = 0; i < K; ++i) {
            retX[i] = sc.nextInt();
            retY[i] = sc.nextInt();
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
                vis = true;
                save = true;
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}
