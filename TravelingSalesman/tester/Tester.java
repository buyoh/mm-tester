import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Tester 
{
    static long seed    = 1;
    static String exec  = "";
    static boolean save = false;
    static boolean vis  = false;

    private double calcScore (final InputData input, final OutputData output) throws NullPointerException
    {
        boolean[] used = new boolean[input.N];
        for (int i = 0; i < input.N; i++) {
            if (output.perm[i] < 0 || output.perm[i] >= input.N) {
                System.err.println("All elements of your return must be between 0 and " +
                                   (input.N - 1) + ", and your return contained " + output.perm[i] + ".");
                return -1.0;
            }
            if (used[output.perm[i]]) {
                System.err.println("All elements of your return must be unique, " +
                                   "and your return contained " + output.perm[i] + " twice.");
                return -1.0;
            }
            used[output.perm[i]] = true;
        }
        double score = 0.0;
        for (int i = 0; i < input.N; i++) {
            double dx = (double)(input.posX[output.perm[i]] - input.posX[output.perm[(i + 1) % input.N]]);
            double dy = (double)(input.posY[output.perm[i]] - input.posY[output.perm[(i + 1) % input.N]]);
            score += Math.sqrt(dx * dx + dy * dy);
        }
        return score;
    }

    private Tester ()
    {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(exec);
            InputData input = new InputData(seed);
            OutputData output = new OutputData(input, proc.getInputStream(), proc.getOutputStream());
            System.out.println("Score = " + calcScore(input, output));
            proc.destroy();
            if (vis) {
                JFrame jf = new JFrame();
                Visualizer v = new Visualizer(input, output);
                jf.getContentPane().setPreferredSize(new Dimension(v.VIS_SIZE, v.VIS_SIZE));
                jf.pack();
                jf.setVisible(true);
                jf.addWindowListener(v);
                jf.getContentPane().add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to get result from your answer.");
        }
    }

    public static void main (String[] args)
    {
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-seed")) {
                seed = Long.parseLong(args[++i]);
            } else if (args[i].equals("-exec")) {
                exec = args[++i];
            } else if (args[i].equals("-vis")) {
                vis = true;
            } else if (args[i].equals("-save")) {
                save = true;
                vis = true;
            }
        }
        Tester test = new Tester();
    }

}
