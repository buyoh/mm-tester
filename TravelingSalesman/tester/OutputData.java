import java.io.*;
import java.util.*;

public class OutputData
{
    final int[] perm;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < perm.length; ++i) {
            sb.append(perm[i]).append('\n');
        }
        return sb.toString();
    }

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
