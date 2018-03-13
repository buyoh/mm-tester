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
                BufferedImage bi = new BufferedImage((SIZE + 4) * 10, (SIZE + 4) * 10, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = (Graphics2D)bi.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(0xD3D3D3));
                g2.fillRect(0, 0, (SIZE + 4) * 10, (SIZE + 4) * 10);
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(10, 10, (SIZE + 2) * 10, (SIZE + 2) * 10);

                g2.setColor(new Color(0x000000));
                for (int i = 0; i < N; i++) {
                    int a = perm[i];
                    int b = perm[(i + 1) % N];
                    g2.drawLine(posX[a] * 10 + 20, posY[a] * 10 + 20, 
                                posX[b] * 10 + 20, posY[b] * 10 + 20);
                }

                for (int i = 0; i < N; i++) {
                    g2.setColor(new Color(0xFFFFFF));
                    g2.fillOval(posX[i] * 10 + 17, posY[i] * 10 + 17, 6, 6);
                    g2.setColor(new Color(0x000000));
                	g2.drawOval(posX[i] * 10 + 17, posY[i] * 10 + 17, 6, 6);
                }

                g2.setFont(new Font("Arial", Font.BOLD, 9));
                FontMetrics fm = g2.getFontMetrics();
                for (int i = 0; i < N; ++i) {
                    char[] ch = ("" + i).toCharArray();
                    int x = posX[i] * 10 + 15;
                    int y = posY[i] * 10 + 15;
                    g2.drawChars(ch, 0, ch.length, x, y);
                }

                g.drawImage(bi, 0, 0, (SIZE + 4) * 10, (SIZE + 4) * 10, null);
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

    final int MAXN = 200, MINN = 50;
    final int SIZE = 50 + 1;
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
        } catch (Exception e) {
            //addFatalError("An exception occurred while generating test case.");
            e.printStackTrace();
        }

    }

    public double runTest (String seed) {

        try {
            generate(seed);
            if (proc != null) try {
                perm = getPermutation();
                boolean [] used = new boolean[N];
                for (int i = 0; i < N; ++i) {
                    if (perm[i] < 0 || perm[i] >= N) {
                        /*ddFatalError("All elements of your return must be between 0 
                         and " + (N-1) + ", and your return contained " + perm[i] + ".");*/
                        return -1;
                    }
                    if (used[perm[i]]) {
                        /*addFatalError("All elements of your return must be unique, 
                        and your return contained " + perm[i] + " twice."); */
                        return -1;
                    }
                    used[perm[i]] = true;
                }
            } catch (Exception e) {
                //addFatalError("Failed to get result from permute.");
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        double score = 0.0;
        for (int i = 0; i < N; i++) {
            double dx = (double)(posX[perm[i]] - posX[perm[(i + 1) % N]]);
            double dy = (double)(posY[perm[i]] - posY[perm[(i + 1) % N]]);
            score += Math.sqrt(dx * dx + dy * dy);
        }

        if (vis) {
            jf.setSize((SIZE + 4) * 10, (SIZE + 6) * 10);
            jf.setVisible(true);
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
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}