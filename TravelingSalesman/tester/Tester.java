import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Tester 
{
    /********************************************************************/

    JFrame jf;
    Visualizer v;
    InputStream is;
    OutputStream os;

    static Process proc;
    static String fileName, exec;
    static boolean save, vis, numb;

    InputData input;
    OutputData output;
    final int VIS_SIZE = 1020;

    /********************************************************************/

    public double runTest (String seed) {

        try {
            input = new InputData(Long.parseLong(seed));
            if (proc != null) try {
                output = new OutputData(input, is, os);
                boolean [] used = new boolean[input.N];
                for (int i = 0; i < input.N; ++i) {
                    if (output.perm[i] < 0 || output.perm[i] >= input.N) {
                        System.err.println("All elements of your return must be between 0 and " + (input.N-1) + ", and your return contained " + output.perm[i] + ".");
                        return -1;
                    }
                    if (used[output.perm[i]]) {
                        System.err.println("All elements of your return must be unique, and your return contained " + output.perm[i] + " twice.");
                        return -1;
                    }
                    used[output.perm[i]] = true;
                }
            } catch (Exception e) {
                System.err.println("Failed to get result from your answer.");
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        double score = 0.0;
        for (int i = 0; i < input.N; i++) {
            double dx = (double)(input.posX[output.perm[i]] - input.posX[output.perm[(i + 1) % input.N]]);
            double dy = (double)(input.posY[output.perm[i]] - input.posY[output.perm[(i + 1) % input.N]]);
            score += Math.sqrt(dx * dx + dy * dy);
        }
        return score;
    }

    public Tester (String seed) {
        if (exec != null) {
            try {
                Runtime rt = Runtime.getRuntime();
                proc = rt.exec(exec);
                os = proc.getOutputStream();
                is = proc.getInputStream();
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
        if (vis) {
            jf = new JFrame();
            v = new Visualizer(input, output);
            jf.getContentPane().setPreferredSize(new Dimension(VIS_SIZE, VIS_SIZE));
            jf.pack();
            jf.setVisible(true);
            jf.addWindowListener(v);
            jf.getContentPane().add(v);
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
            } else if (args[i].equals("-num")) {
                numb = true;
            }
        }
        fileName = seed;
        Tester test = new Tester(seed);
    }

}
