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
            score = output.score;
            proc.waitFor();
            proc.destroy();
            Visualizer v = new Visualizer(input, output);
            v.saveImage(seed);
            if (vis) {
                Dimension dm = new Dimension(v.VIS_SIZE_X, v.VIS_SIZE_Y);
                JFrame jf = new JFrame();
                jf.getContentPane().setPreferredSize(dm);
                jf.pack();
                jf.setVisible(true);
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //jf.addWindowListener(v);
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
