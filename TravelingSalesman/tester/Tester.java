/**
 * The main class of the tester.
 * @author kosakkun
 */

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Tester 
{
    static String seed  = "1";
    static String exec  = "";
    static boolean save = false;
    static boolean vis  = false;

    /**
     * Calculate the score from the input data and output data and return it.
     * Returns -1 if the constraint is not met.
     * @param input The input data of the problem.
     * @param output The output data of the problem.
     * @return The solver output score for the problem.
     * @see InputData
     * @see OutputData
     */
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

    /**
     * Generates input data from the seed given by the -seed option. The input data is passed to 
     * the program execution command given by the -exec option, and the output data is obtained from 
     * the standard output. And output the result.
     * @see InputData
     * @see OutputData
     * @see Visualizer
     * @see ErrorReader
     */
    private Tester ()
    {
        InputData input;
        OutputData output;
        double score = -1.0;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(exec);
            new ErrorReader(proc.getErrorStream()).start();
            input = new InputData(Long.parseLong(seed));
            output = new OutputData(input, proc.getInputStream(), proc.getOutputStream());
            proc.waitFor();
            proc.destroy();
            score = calcScore(input, output);
            Visualizer v = new Visualizer(input, output);
            if (save) {
                String filename = seed;
                v.saveImage(filename);
            }
            if (vis) {
                Dimension dm = new Dimension(v.VIS_SIZE_X, v.VIS_SIZE_Y);
                JFrame jf = new JFrame();
                jf.getContentPane().setPreferredSize(dm);
                jf.pack();
                jf.setVisible(true);
                jf.addWindowListener(v);
                jf.getContentPane().add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to get result from your answer.");
        }
        System.out.println("Score = " + score);
    }

    /**
     * Set the tester from the options.
     * @param args Tester options.
     */
    public static void main (String[] args)
    {
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
        Tester test = new Tester();
    }
}
