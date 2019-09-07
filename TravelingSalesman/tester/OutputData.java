/**
 * Class that generates output data.
 * @author kosakkun
 */

import java.io.*;
import java.util.*;

public class OutputData
{
    final int[] perm;

    /**
     * The output data is converted to a character string and returned.
     * @return Output data in character string format.
     */
    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < perm.length; ++i) {
            sb.append(perm[i]).append('\n');
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
        perm = new int[input.N];
        for (int i = 0; i < input.N; i++) {
            perm[i] = sc.nextInt();
        }
    }
}
