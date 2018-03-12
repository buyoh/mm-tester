import java.awt.*;
import javax.swing.JPanel;
import java.security.*;

public class Tester {

    public class Visualizer extends JPanel {

    }

    /********************************************************************/
    static String fileName, exec;
    static boolean save, vis, debug;

    static final int maxN = 1000;
    static final int minN = 20;
    static final int Size = 100;

    /********************************************************************/

    public void generate (String seedStr) {
        try {
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            long seed = Long.parseLong(seedStr);
            rnd.setSeed(seed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double runTest () {
        return -1.0;
    }

    public Tester (String seed) {
        generate(seed);
        System.out.println(runTest());
    }

    public static void main (String[] args) {
        String seed = "1";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-seed")) {
            	seed = args[++i];
            } else if (args[i].equals("-exec")) {
                exec = args[++i];
            } else if (args[i].equals("-vis")) {
                vis = true;
            } else if (args[i].equals("-save")) {
                save = true;
            } else if (args[i].equals("-debug")) {
                debug = true;
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}