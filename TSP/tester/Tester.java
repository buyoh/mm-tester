import java.awt.*;

public class Tester {

    /********************************************************************/
    static String seed, exec;
    static boolean save, vis;

    /********************************************************************/

    public static void main (String[] args) {
        seed = "1";
        for (int i = 0; i < args.length; i++) {
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
    }

}