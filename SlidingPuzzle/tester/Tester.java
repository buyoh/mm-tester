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
        
        public void paint(Graphics g) 
        {
            try {
                BufferedImage bi = new BufferedImage(VIS_SIZE_X + 20, VIS_SIZE_Y + 20, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = (Graphics2D)bi.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xD3D3D3));
                g2.fillRect(0, 0, VIS_SIZE_X + 20, VIS_SIZE_Y + 20);
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(10, 10, VIS_SIZE_X, VIS_SIZE_Y);

                g2.setColor(new Color(0x000000));
                for (int x = 0; x < N; x++) {
                    for (int y = 0; y < M; y++) {
                        g2.drawRect(x * PANNEL_SIZE + 10, y * PANNEL_SIZE + 10, PANNEL_SIZE, PANNEL_SIZE);
                    }
                }

                g.drawImage(bi, 0, 0, VIS_SIZE_X + 20, VIS_SIZE_Y + 20, null);
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
    static boolean vis;
    static double delay;

    final int MAXN = 30, MINN = 4;
    final int MAXM = 30, MINM = 4;
    final int SHUFFLE = 100000;
    final int PANNEL_SIZE = 30;
    int VIS_SIZE_X;
    int VIS_SIZE_Y;
    int N,M;
    int [][] Board;
    int AnswerN;
    int posX [];
    int posY [];

    /********************************************************************/

    private boolean move_pannel (int x, int y)
    {
        return true;
    }

    public void generate (String seedStr) 
    {
        try {   
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);
            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
            M = rnd.nextInt(MAXM - MINM + 1) + MINM;
            VIS_SIZE_X = N * PANNEL_SIZE;
            VIS_SIZE_Y = M * PANNEL_SIZE;
            Board = new int[N][M];
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < M; y++) {
                    if (x == N - 1 && y == M - 1) {
                        Board[x][y] = -1;
                    } else {
                        Board[x][y] = x * M + y;
                    }
                }
            }
            for (int i = 0; i < SHUFFLE; i++) {
                while (true) {
                    int x = rnd.nextInt(N);
                    int y = rnd.nextInt(M);
                    if (move_pannel(x, y)) break;
                }
            }
        } catch (Exception e) {
            System.err.println("An exception occurred while generating test case.");
            e.printStackTrace();
        }

    }

    public double runTest (String seed) 
    {
        try {
            generate(seed);
            if (vis) {
                jf.setSize(VIS_SIZE_X + 20, VIS_SIZE_Y + 45);
                jf.setVisible(true);
            }  
            if (proc != null) try {
                getAnswer();
                for (int i = 0; i < AnswerN; i++) {
                    if (move_pannel(posX[i], posY[i])) {
                        if (vis) {
                            v.repaint();
                            Thread.sleep((long)(delay * 1000.0));
                        }
                    } else {
                        System.err.println("Cannot move the pannel x = " + posX[i] + " y = " + posY[i] + ".");
                        return -1;
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to get result from permute.");
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
        return AnswerN;
    }

    private void getAnswer () throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                sb.append(Board[x][y]);
                sb.append((y == M - 1) ? '\n' : ' ');
            }
        }
        os.write(sb.toString().getBytes());
        os.flush();
        AnswerN = sc.nextInt();
        posX = new int[AnswerN];
        posY = new int[AnswerN];
        for (int i = 0; i < AnswerN; ++i) {
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
        delay = 0.2;
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-seed")) {
                seed = args[++i];
            } else if (args[i].equals("-exec")) {
                exec = args[++i];
            } else if (args[i].equals("-vis")) {
                vis = true;
            } else if (args[i].equals("-delay")) {
                delay = Double.parseDouble(args[++i]);
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}
