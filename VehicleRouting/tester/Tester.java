import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Tester
{
    static String seed  = "1";
    static String exec  = "";
    static boolean save = false;
    static boolean vis  = false;
    
    public Tester ()
    {
        try {
            Process proc = Runtime.getRuntime().exec(exec);
            new ErrorReader(proc.getErrorStream()).start();
            InputData input = new InputData(Long.parseLong(seed));
            OutputData output = new OutputData(input, proc.getInputStream(), proc.getOutputStream());
            proc.waitFor();
            proc.destroy();
            System.out.println("Score = " + output.score);
            if (output.score >= 0.0) {
                Visualizer v = new Visualizer(input, output);
                if (save) v.saveImage(seed);
                if (vis ) v.visualize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to get result from your answer.");
            System.out.println("Score = -1");
        }
        
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
