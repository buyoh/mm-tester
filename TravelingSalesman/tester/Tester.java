import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;


public class Tester 
{
    static long seed = 1;
    static String exec = "";
    static boolean save = false;
    static boolean vis  = false;
    static boolean json = false;

    private double score;
    private Input input;
    private Output output;

    public class Json {
        public long seed;
        public double score;        
    }

    private String getJsonString ()
    {
        String ret = "";
        try {
            Json json = new Json();
            json.seed = seed;
            json.score = score;
            ObjectMapper mapper = new ObjectMapper();
            ret = mapper.writeValueAsString(json);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("JSON generation failed.");
        }
        return ret;
    }

    private Tester ()
    {
        try {
            Process proc = Runtime.getRuntime().exec(exec);
            InputStream  is = proc.getInputStream();
            InputStream  es = proc.getErrorStream();
            OutputStream os = proc.getOutputStream();
            new ErrorReader(es).start();
            input  = new Input(seed);
            output = new Output(input, is, os);
            proc.destroy();
            if ((vis || save) && output.score >= 0) {
                Visualizer v = new Visualizer(input, output);
                if (save) v.saveImage(String.valueOf(seed));
                if (vis ) v.visualize();
            }
            score = output.score;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to get result from your answer.");
            score = -1.0;
        } finally {
            if (json) {
                System.out.println(getJsonString());
            } else {
                System.out.println("Score = " + score);
            }
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
            } else if (args[i].equals("-json")) {
                json = true;
            }
        }
        Tester test = new Tester();
    }
}
