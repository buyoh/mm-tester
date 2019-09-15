import java.io.*;
import java.util.*;

public class OutputData
{
    final int BOX_SIZE = 1000;

    final int[] posX;
    final int[] posY;
    final int score;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < posX.length; ++i) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n');
        }
        return sb.toString();
    }

    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        posX = new int[input.N];
        posY = new int[input.N];
        for (int i = 0; i < input.N; i++) {
            posX[i] = sc.nextInt();
            posY[i] = sc.nextInt();
        }

        /* Check whether the output satisfies the constraints. */
        boolean[][] used = new boolean[BOX_SIZE][BOX_SIZE];
        for (int i = 0; i < input.N; i++) {
            for (int x = posX[i]; x < posX[i] + input.w[i]; x++) {
                for (int y = posY[i]; y < posY[i] + input.h[i]; y++) {
                    if (x < 0 || y < 0 || x >= BOX_SIZE || y >= BOX_SIZE) {
                        System.err.println("There is a rectangle which does not fit in the box.");
                        score = -1;
                        return;
                    }
                    if (used[x][y]) {
                        System.err.println("There are overlapping rectangles.");
                        score = -1;
                        return;
                    }
                    used[x][y] = true;
                }
            }
        }

        /* Calculate the score. */
        int highest_pos = 0;
        for (int i = 0; i < input.N; i++) {
            highest_pos = Math.max(highest_pos, posY[i] + input.h[i]);
        }
        score = highest_pos;
    }
}
