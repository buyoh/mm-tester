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

    final int MAXN = 10, MINN = 100;
    final int SIZE = 100;
    boolean [][] Board;
    int N;
    int posX [];
    int posY [];
    int M;
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
            posX = new int[SIZE];
            posY = new int[SIZE];
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

    public int runTest (String seed) 
    {
        try 
        {
            generate(seed);

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return -1;
        }

        return -1;
    }

    private void getAnswer () throws IOException {
        
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
