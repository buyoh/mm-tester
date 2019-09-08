/**
 * Class that generates output data.
 * @author kosakkun
 */

import java.io.*;
import java.util.*;

public class OutputData
{
    int[][] plan;

    /**
     * The output data is converted to a character string and returned.
     * @return Output data in character string format.
     */
    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < plan.length; i++) {
            sb.append(plan[i].length);
            for (int j = 0; j < plan[i].length; j++) {
                sb.append(' ').append(plan[i][j]);
            }
        }
        return sb.toString();
    }

    /**
     * Give input data to the solver and get output data.
     * @param input The input data of the problem.
     * @param is The input stream connected to the output of the solver.
     * @param os The output stream connected to the solver input.
     * @see InputData
     */
    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        plan = new int[input.M][];
        for (int i = 0; i < input.M; ++i) {
            int L = sc.nextInt();
            plan[i] = new int[L];
            for (int j = 0; j < L; j++) {
                plan[i][j] = sc.nextInt();
            }
        }
    }
}
