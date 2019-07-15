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
            try {
                BufferedImage bi = new BufferedImage(SIZE_VIS + 20, SIZE_VIS + 20, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = (Graphics2D)bi.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(0xD3D3D3));
                g2.fillRect(0, 0, SIZE_VIS + 20, SIZE_VIS + 20);
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(10, 10, SIZE_VIS, SIZE_VIS);

                g2.setColor(new Color(0xdc143c));
                for (int i = 0; i < N; i++) {
                    g2.fillRect(posX[i] * 10 + 10, posY[i] * 10 + 10, 10, 10);
                }

                g2.setColor(new Color(0x4169e1));
                for (int i = 0; i < M; i++) {
                    g2.fillRect(ansX[i] * 10 + 10, ansY[i] * 10 + 10, 10, 10);
                }

                g2.setStroke(new BasicStroke(1.0f));
                g2.setColor(new Color(0xD3D3D3));
                for (int i = 1; i <= SIZE; i++) {
                    g2.drawLine(i * 10, 0, i * 10, SIZE_VIS + 20);
                    g2.drawLine(0, i * 10, SIZE_VIS + 20, i * 10);
                }

                g.drawImage(bi, 0, 0, SIZE_VIS + 20, SIZE_VIS + 20, null);
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

    final int[] dx = {1, 0, -1, 0};
    final int[] dy = {0, 1, 0, -1};
    final int MAXN = 200, MINN = 10;
    final int SIZE = 100;
    final int SIZE_VIS = SIZE * 10;
    boolean [][] Board;
    boolean [][] Connect;
    int N,M;
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
            Board = new boolean[SIZE][SIZE];
            posX = new int[N];
            posY = new int[N];
            for (int i = 0; i < N; i++) {
                while (true) {
                    int x = rnd.nextInt(SIZE);
                    int y = rnd.nextInt(SIZE);
                    if (!Board[x][y]) {
                        Board[x][y] = true;
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

    private void dfs (int x, int y)
    {
        if (Connect[x][y]) return;
        Connect[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || ny < 0 || nx >= SIZE || ny >= SIZE) continue;
            if (!Board[x][y]) continue; 
            dfs(nx, ny);
        }
    }

    public int runTest (String seed) 
    {
        try 
        {
            generate(seed);
            if (proc != null) try {
                getAnswer();
                for (int i = 0; i < M; i++) {
                    if (Board[ansX[i]][ansY[i]]) {
                        System.err.println("Panels overlap with x = " + ansX[i] + ", y = " + ansY[i] + ".");
                        return -1;
                    }
                    Board[ansX[i]][ansY[i]] = true;
                }
                Connect = new boolean[SIZE][SIZE];
                dfs(posX[0], posY[0]);
                for (int i = 0; i < N; i++) {
                    if (!Connect[posX[i]][posY[i]]) {
                        System.err.println("Panels are not connected.");
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
            jf.getContentPane().setPreferredSize(new Dimension(SIZE_VIS + 20, SIZE_VIS + 20));
            jf.pack();
            jf.setVisible(true);
        }

        return M;
    }

    private void getAnswer () throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
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
