import java.io.File;
import java.io.FileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Tester 
{
    static long seed = 1;
    static String exec = "";
    static boolean save = false;
    static boolean vis  = false;
    static boolean json = false;
    static boolean debug = false;
    private double score;

    private String getJsonString ()
    {
        String ret = "";
        try {
            class Json {
                public long seed;
                public double score;    
            }
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
            new ErrorReader(proc.getErrorStream()).start();
            Input input = new Input(seed);
            Output output = new Output(input, proc.getInputStream(), proc.getOutputStream());
            proc.destroy();
            if (debug) {
                File ifile = new File("input-" + seed + ".txt");
                FileWriter ifw = new FileWriter(ifile);
                ifw.write(input.getString());
                ifw.close();
                File ofile = new File("output-" + seed + ".txt");
                FileWriter ofw = new FileWriter(ofile);
                ofw.write(output.getString());
                ofw.close();
            }
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
            } else if (args[i].equals("-debug")) {
                debug = true;
            }
        }
        Tester test = new Tester();
    }
}
