import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.security.*;
import java.util.*;
import java.util.List;
import javax.imageio.*;
import javax.swing.*;

public class KnightsAndPawnsVis {
    static int maxS = 100, minS = 10;
    static int[] dr = {1,  1, -1, -1, 2,  2, -2, -2};
    static int[] dc = {2, -2,  2, -2, 1, -1,  1, -1};
    static int invalidScore = -1;
    int H, W;                       // board size
    volatile char[][] board;        // board (P for a pawn, K for a knight . for empty space)
    volatile int K;
    volatile int[][] attacks;       // number of other pieces attacked by this knight
    // -----------------------------------------
    String generate(String seedStr) {
      try {
        // generate test case
        SecureRandom r1 = SecureRandom.getInstance("SHA1PRNG"); 
        long seed = Long.parseLong(seedStr);
        r1.setSeed(seed);
        H = r1.nextInt(maxS - minS + 1) + minS;
        W = r1.nextInt(maxS - minS + 1) + minS;
        if (seed == 1) {
            H = 5;
            W = 10;
        }
        else if (seed == 2) {
            W = H = (minS + maxS) / 2;
        }
        else if (seed == 3) {
            W = H = maxS;
        }

        // generate the board
        double p = r1.nextDouble() * 0.6;   // probability of a pawn being placed in a cell
        board = new char[H][W];

        for (int r = 0; r < H; ++r)
        for (int c = 0; c < W; ++c)
            board[r][c] = (r1.nextDouble() < p ? 'P' : '.');

        StringBuffer sb = new StringBuffer();
        sb.append("H = ").append(H).append('\n');
        sb.append("W = ").append(W).append('\n');
        sb.append("Probability of a pawn = ").append(p).append('\n');
        for (int i = 0; i < H; ++i) {
            sb.append(new String(board[i])).append('\n');
        }
        return sb.toString();
      }
      catch (Exception e) {
        addFatalError("An exception occurred while generating test case.");
        e.printStackTrace(); 
        return "";
      }
    }
    // -----------------------------------------
    void updateCell(int r, int c) {
        attacks[r][c] = 0;
        if (board[r][c] != 'K')
            return;
        for (int k = 0; k < 8; ++k) {
            int r1 = r + dr[k];
            int c1 = c + dc[k];
            if (r1 >= 0 && c1 >= 0 && r1 < H && c1 < W && board[r1][c1] != '.')
                attacks[r][c]++;
        }
    }
    // -----------------------------------------
    void updateBoard() {
        for (int r = 0; r < H; ++r)
        for (int c = 0; c < W; ++c)
            updateCell(r, c);
    }
    // -----------------------------------------
    boolean isValidBoard() {
        updateBoard();
        for (int r = 0; r < H; ++r)
        for (int c = 0; c < W; ++c)
            if (board[r][c] == 'K' && attacks[r][c] != 2)
                return false;
        return true;
    }
    // -----------------------------------------
    public double runTest(String seed) {
      try {
        String test = generate(seed);
        if (debug)
            System.out.println(test);

        // start with no knights placed
        K = 0;
        attacks = new int[H][W];

        if (vis) {
            jf.setVisible(true);
            Insets frameInsets = jf.getInsets();
            int fw = frameInsets.left + frameInsets.right + 8;
            int fh = frameInsets.top + frameInsets.bottom + 8;
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            Insets screenInsets = toolkit.getScreenInsets(jf.getGraphicsConfiguration());
            screenSize.width -= screenInsets.left + screenInsets.right;
            screenSize.height -= screenInsets.top + screenInsets.bottom;
            if (SZ == 0) {
                SZ = Math.min(Math.min((screenSize.width - fw - 120) / W, (screenSize.height - fh) / H), 40);
                if (!plain && SZ < 12) plain = true;
            }
            Dimension dim = v.getVisDimension();
            v.setPreferredSize(dim);
            jf.setSize(Math.min(dim.width + fw, screenSize.width), Math.min(dim.height + fh, screenSize.height));
            manualReady = false;
            draw();
        }

        if (proc != null) {
            // call the solution
            String[] boardArg = new String[H];
            for (int i = 0; i < H; ++i) {
                boardArg[i] = new String(board[i]);
            }

            String[] placement;
            try {
                placement = placeKnights(boardArg);
            } catch (Exception e) {
                addFatalError("Failed to get result from placeKnights.");
                return invalidScore;
            }

            // check the return and score it
            if (placement == null) {
                addFatalError("Failed to get result from placeKnights.");
                return invalidScore;
            }
            if (placement.length != H) {
                addFatalError("Your return must contain " + H + " strings, but it contained " + placement.length + ".");
                return invalidScore;
            }

            for (int i = 0; i < H; ++i) {
                if (placement[i] == null) {
                    addFatalError("Each element of your return must be a string of length " + W + ", but element " + i + " was empty.");
                    return invalidScore;
                }
                if (placement[i].length() != W) {
                    addFatalError("Each element of your return must be a string of length " + W + ", but element " + i + " had length " + placement[i].length() + ".");
                    return invalidScore;
                }
                for (int j = 0; j < W; ++j) {
                    char c = placement[i].charAt(j);
                    if (c != '.' && c != 'K') {
                        addFatalError("Each character in your return must be '.' or 'K', but element " + i + " contained character '" + c + "'.");
                        return invalidScore;
                    }
                    if (c == 'K') {
                        if (board[i][j] == 'P') {
                            addFatalError("Trying to put a knight on a cell (" + i + ", " + j + ") which already contains a pawn.");
                            return invalidScore;
                        }
                        board[i][j] = 'K';
                        K++;
                    }
                }
            }

            if (vis)
                draw();
        }

        if (manual) {
            // let player know that we're waiting for their input
            addFatalError("Manual play on");

            // wait till player finishes (possibly on top of automated return)
            while (!manualReady)
            {   try { Thread.sleep(50);}
                catch (Exception e) { e.printStackTrace(); } 
            }
        }

        if (!isValidBoard()) {
            addFatalError("Not all knights attack exactly 2 pieces.");
            return invalidScore;
        }
        return K;
      }
      catch (Exception e) { 
        addFatalError("An exception occurred while trying to get your program's results.");
        e.printStackTrace(); 
        return invalidScore;
      }
    }
// ------------- visualization part ------------
    static String seed;
    JFrame jf;
    Vis v;
    static String exec;
    static boolean vis, manual, plain, debug, save;
    static Process proc;
    InputStream is;
    OutputStream os;
    BufferedReader br;
    static int SZ;
    volatile boolean manualReady;
    // -----------------------------------------
    String[] placeKnights(String[] board) throws IOException {
        // pass the params to the solution and get the return
        StringBuffer sb = new StringBuffer();
        sb.append(board.length).append('\n');
        for (int i = 0; i < board.length; ++i)
            sb.append(board[i]).append('\n');
        os.write(sb.toString().getBytes());
        os.flush();

        // get the return - an array of strings
        int nret = Integer.parseInt(br.readLine());
        String[] ret = new String[nret];
        for (int i = 0; i < nret; ++i)
            ret[i] = br.readLine();
        return ret;
    }
    // -----------------------------------------
    void draw() {
        if (!vis) return;
        v.repaint();
    }
    // -----------------------------------------
    public class Vis extends JPanel implements MouseListener, WindowListener {
        public void paint(Graphics g) {
            super.paint(g);
            Dimension dim = getVisDimension();
            BufferedImage bi = new BufferedImage(dim.width,dim.height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D)bi.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // background
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(0,0,dim.width,dim.height);
            // board
            g2.setColor(Color.WHITE);
            g2.fillRect(0,0,W*SZ,H*SZ);
            g2.setBackground(Color.WHITE);

            g2.setColor(Color.BLACK);
            for (int i = 0; i <= H; i++)
                g2.drawLine(0,i*SZ,W*SZ,i*SZ);
            for (int i = 0; i <= W; i++)
                g2.drawLine(i*SZ,0,i*SZ,H*SZ);

            updateBoard();

            g2.setFont(new Font("Segoe UI Symbol",Font.BOLD, SZ - 3));
            FontMetrics fm = g2.getFontMetrics();
            int h = fm.getHeight()/2;
            for (int i = 0; i < H; ++i)
            for (int j = 0; j < W; ++j) {
                if (board[i][j] == 'K') {
                    if (attacks[i][j] != 2) {
                        // red for knights which attack incorrect number of pieces
                        g2.setColor(Color.RED);
                        g2.fillRect(j * SZ + 1, i * SZ + 1, SZ - 1, SZ - 1);
                        if (plain)
                            continue;
                    }
                }
                // draw the pawn/knight
                if (board[i][j] != '.') {
                    g2.setColor(board[i][j] == 'K' ? Color.BLACK : Color.GRAY);
                    if (plain) {
                        g2.fillRect(j * SZ + 1, i * SZ + 1, SZ - 1, SZ - 1);
                    } else {
                        char[] ch = { board[i][j] == 'K' ? '\u265E' : '\u265F'};
                        g2.drawChars(ch, 0, ch.length, j * SZ + SZ/2 - fm.charWidth(ch[0])/2, i * SZ + SZ / 2 + h - 2);
                    }
                }
            }

            // "buttons" to control visualization options
            g2.setFont(new Font("Arial",Font.BOLD,13));
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(Color.BLACK);
            int xText = SZ * W + 10;
            int wText = 100;
            int yText = 10;
            int hButton = 30;
            int hText = 20;
            int vGap = 10;

            if (manualReady) 
                g2.clearRect(xText,yText,wText,hButton);
            drawString(g2,"READY",xText,yText,wText,hButton,0);
            g2.drawRect(xText,yText,wText,hButton);
            yText += hButton + vGap;

            if (plain) 
                g2.clearRect(xText,yText,wText,hButton);
            drawString(g2,"PLAIN",xText,yText,wText,hButton,0);
            g2.drawRect(xText,yText,wText,hButton);
            yText += hButton + vGap;

            // current score
            drawString(g2,"SCORE",xText,yText,wText,hText,0);
            yText += hText;
            boolean valid = isValidBoard();
            g2.setColor(valid ? Color.BLACK : Color.RED);
            drawString(g2,String.format("%d", valid ? K : invalidScore),xText,yText,wText,hText,0);
            yText += hText * 2;

            if (save) {
                try {
                    ImageIO.write(bi,"png", new File(seed + ".png"));
                } catch (Exception e) { e.printStackTrace(); }
            }

            g.drawImage(bi,0,0,null);
        }
        // -------------------------------------
        void drawString(Graphics2D g2, String text, int x, int y, int w, int h, int align) {
            FontMetrics metrics = g2.getFontMetrics();
            Rectangle2D rect = metrics.getStringBounds(text, g2);
            int th = (int)(rect.getHeight());
            int tw  = (int)(rect.getWidth());
            if (align == 0) x = x + (w - tw) / 2;
            else if (align > 0) x = (x + w) - tw;
            y = y + (h - th) / 2 + metrics.getAscent();
            g2.drawString(text, x, y);
        }
        // -------------------------------------
        public Vis() {
            addMouseListener(this);
            jf.addWindowListener(this);
        }
        // -------------------------------------
        public Dimension getVisDimension() {
            return new Dimension(W * SZ + 126, H * SZ + 1);
        }
        // -------------------------------------
        // WindowListener
        public void windowClosing(WindowEvent e){ 
            if(proc != null)
                try { proc.destroy(); } 
                catch (Exception ex) { ex.printStackTrace(); }
            System.exit(0); 
        }
        public void windowActivated(WindowEvent e) { }
        public void windowDeactivated(WindowEvent e) { }
        public void windowOpened(WindowEvent e) { }
        public void windowClosed(WindowEvent e) { }
        public void windowIconified(WindowEvent e) { }
        public void windowDeiconified(WindowEvent e) { }
        88
    // -----------------------------------------
    public KnightsAndPawnsVis(String seed) {
      try {
        if (vis) {
            jf = new JFrame();
            jf.setTitle("Seed " + seed);
            v = new Vis();
            JScrollPane sp = new JScrollPane(v);
            jf.getContentPane().add(sp);
        }
        if (exec != null) {
            try {
                Runtime rt = Runtime.getRuntime();
                proc = rt.exec(exec);
                os = proc.getOutputStream();
                is = proc.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                new ErrorReader(proc.getErrorStream()).start();
            } catch (Exception e) { e.printStackTrace(); }
        }
        System.out.println("Score = " + runTest(seed));
        if (proc != null)
            try { proc.destroy(); } 
            catch (Exception e) { e.printStackTrace(); }
      }
      catch (Exception e) { e.printStackTrace(); }
    }
    // -----------------------------------------
    public static void main(String[] args) {
        seed = "1";
        vis = true;
        manual = false;
        SZ = 0; //Auto-fit to desktop size
        plain = false;
        save = false;
        for (int i = 0; i<args.length; i++)
        {   if (args[i].equals("-seed"))
                seed = args[++i];
            if (args[i].equals("-exec"))
                exec = args[++i];
            if (args[i].equals("-novis"))
                vis = false;
            if (args[i].equals("-manual"))
                manual = true;
            if (args[i].equals("-size"))
                SZ = Integer.parseInt(args[++i]);
            if (args[i].equals("-debug"))
                debug = true;
            if (args[i].equals("-plain"))
                plain = true;
            if (args[i].equals("-save"))
                save = true;
        }
        if (exec == null)
            manual = true;
        if (manual)
            vis = true;

        KnightsAndPawnsVis f = new KnightsAndPawnsVis(seed);
    }
    // -----------------------------------------
    void addFatalError(String message) {
        System.out.println(message);
    }
}

class ErrorReader extends Thread{
    InputStream error;
    public ErrorReader(InputStream is) {
        error = is;
    }
    public void run() {
        try {
            byte[] ch = new byte[50000];
            int read;
            while ((read = error.read(ch)) > 0)
            {   String s = new String(ch,0,read);
                System.out.print(s);
                System.out.flush();
            }
        } catch(Exception e) { }
    }
}
