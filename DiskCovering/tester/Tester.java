import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.security.*;
import javax.swing.*;
import javax.imageio.*;

public class Tester {

    public class Visualizer extends JPanel implements WindowListener 
    {    
        public void paint(Graphics g) 
        {
            try 
            {
                BufferedImage bi = new BufferedImage(SIZE_VIS, SIZE_VIS, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = (Graphics2D)bi.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(10, 10, SIZE_VIS - 20, SIZE_VIS - 20);

                g2.setColor(new Color(0xF4F4FF));
                for (int i = 0; i < M; i++) {
                    g2.fillOval(ansX[i] + 10 - R, ansY[i] + 10 - R, R * 2, R * 2);
                }

                g2.setColor(new Color(0x4169e1));
                for (int i = 0; i < M; i++) {
                    g2.drawOval(ansX[i] + 10 - R, ansY[i] + 10 - R, R * 2, R * 2);
                }

                for (int i = 0; i < N; i++) {
                    g2.setColor(new Color(0x454552));
                    g2.fillOval(posX[i] + 7, posY[i] + 7, 6, 6);
                    g2.setColor(new Color(0x000000));
                    g2.drawOval(posX[i] + 7, posY[i] + 7, 6, 6);
                }

                g2.setColor(new Color(0xD3D3D3));
                g2.fillRect(0, 0, SIZE_VIS, 10);
                g2.fillRect(0, 0, 10, SIZE_VIS);
                g2.fillRect(SIZE_VIS - 10, 0, 10, SIZE_VIS);
                g2.fillRect(0, SIZE_VIS - 10, SIZE_VIS, 10);

                g.drawImage(bi, 0, 0, SIZE_VIS, SIZE_VIS, null);
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
    Scanner sc;

    static Process proc;
    static String fileName, exec;
    static boolean vis, save;

    final int MAXN = 1000, MINN = 20;
    final int MAXR = 100, MINR = 10;
    final int BOARD_SIZE = 1001;
    final int SIZE_VIS = 1020;
    int N,R,M;
    int posX [];
    int posY [];
    int ansX [];
    int ansY [];

    /********************************************************************/

    public void generate (String seedStr) 
    {
        try 
        {   
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);

            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
            R = rnd.nextInt(MAXR - MINR + 1) + MINR;
            posX = new int[N];
            posY = new int[N];
            boolean [][] used = new boolean[BOARD_SIZE][BOARD_SIZE];
            for (int i = 0; i < N; i++) {
                while (true) {
                    int x = rnd.nextInt(BOARD_SIZE);
                    int y = rnd.nextInt(BOARD_SIZE);
                    if (!used[x][y]) {
                        used[x][y] = true;
                        posX[i] = x;
                        posY[i] = y;
                        break;
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            System.err.println("An exception occurred while generating test case.");
            e.printStackTrace();
        }

    }

    public int runTest (String seed) 
    {
        try 
        {
            generate(seed);
            if (proc != null) try {
                getAnswer();
                for (int i = 0; i < N; i++) {
                    boolean flag = false;
                    for (int j = 0; j < M; j++) {
                        int dx = Math.abs(posX[i] - ansX[j]);
                        int dy = Math.abs(posY[i] - ansY[j]);
                        if (dx * dx + dy * dy <= R * R) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        System.err.println("Point at coordinate x = " + posX[i] + ", y = " + posY[i] + " is not covered by a circle.");
                        return -1;
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to get result from output.");
                return -1;
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return -1;
        }

        if (vis) {
            jf.setSize(SIZE_VIS, SIZE_VIS);
            jf.setVisible(true);
        }

        return M;
    }

    private void getAnswer () throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(R).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n');
        }
        os.write(sb.toString().getBytes());
        os.flush();

        M = sc.nextInt();
        ansX = new int[M];
        ansY = new int[M];
        for (int i = 0; i < M; i++) {
            ansX[i] = sc.nextInt();
            ansY[i] = sc.nextInt();
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
                new ErrorReader(proc.getErrorStream()).start();
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

class ErrorReader extends Thread {

    private final InputStream error;

    public ErrorReader(InputStream is) {
        error = is;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(error)) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                
                System.out.println("[STDERR] " + s);
                System.out.flush();
            }
        }
    }
}
