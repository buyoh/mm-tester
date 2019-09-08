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

    private double calcDist (int x1, int y1, int x2, int y2)
    {
        double lx = (double)(x1 - x2);
        double ly = (double)(y1 - y2);
        return Math.sqrt(lx * lx + ly * ly);
    }

    private double calcScore (final InputData input, final OutputData output) throws NullPointerException
    {
        boolean[] used = new boolean[input.N];
        for (int i = 0; i < output.plan.length; i++) {
            for (int j = 0; j < output.plan[i].length; j++) {
                if (used[output.plan[i][j]]) {
                    System.err.println("There are vertices that are delivered multiple times.");
                    return -1.0;
                }
                used[output.plan[i][j]] = true;
            }
        }
        for (int i = 0; i < input.N; i++) {
            if (!used[i]) {
                System.err.println("There are vertices that are not delivered.");
                return -1.0;
            }
        }
        double score = -1.0;
        for (int i = 0; i < output.plan.length; i++) {
            double dist = 0.0;
            int x = input.depotX, y = input.depotY;
            for (int j = 0; j < output.plan[i].length; j++) {
                if (j % input.cap[i] == 0) {
                    dist += calcDist(x, y, input.depotX, input.depotY);
                    x = input.depotX;
                    y = input.depotY;
                }
                dist += calcDist(x, y, input.posX[output.plan[i][j]], input.posY[output.plan[i][j]]);
                x = input.posX[output.plan[i][j]];
                y = input.posY[output.plan[i][j]];
            }
            dist += calcDist(x, y, input.depotX, input.depotY);
            double time = dist / (double)input.speed[i];
            score = Math.max(score, time);
        }
        return score;
    }

    public Tester ()
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
