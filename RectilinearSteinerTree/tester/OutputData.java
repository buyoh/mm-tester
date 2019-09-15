import java.io.*;
import java.util.*;

public class OutputData
{
    final int[] dx = {0, 1, 0, -1};
    final int[] dy = {1, 0, -1, 0};
    final int M;
    final int[] addPosX;
    final int[] addPosY;
    final boolean[][] board;
    final int score;

    boolean[][] connect;

    public String getString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(addPosX[i]).append(' ');
            sb.append(addPosY[i]).append('\n');
        }
        return sb.toString();
    }

    private void dfs (int x, int y, int wl, int hl)
    {
        if (connect[x][y]) return;
        connect[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || ny < 0 || nx >= wl || ny >= hl) continue;
            if (!board[nx][ny]) continue;
            if (connect[nx][ny]) continue;  
            dfs(nx, ny, wl, hl);
        }
    }

    public OutputData (final InputData input, InputStream is, OutputStream os) throws IOException
    {
        os.write(input.getString().getBytes());
        os.flush();
        Scanner sc = new Scanner(is);
        M = sc.nextInt();
        addPosX = new int[M];
        addPosY = new int[M];
        for (int i = 0; i < M; i++) {
            addPosX[i] = sc.nextInt();
            addPosY[i] = sc.nextInt();
        }

        /* Check whether the output satisfies the constraints. */
        board = new boolean[input.WIDTH][input.HEIGHT];
        for (int i = 0; i < input.N; i++) {
            if (board[input.posX[i]][input.posY[i]]) {
                System.err.println("Panels overlap with x = " + input.posX[i] + ", y = " + input.posY[i] + ".");
                score = -1;
                return;
            }
            board[input.posX[i]][input.posY[i]] = true;
        }
        for (int i = 0; i < M; i++) {
            if (board[addPosX[i]][addPosY[i]]) {
                System.err.println("Panels overlap with x = " + addPosX[i] + ", y = " + addPosY[i] + ".");
                score = -1;
                return;
            }
            board[addPosX[i]][addPosY[i]] = true;
        }
        connect = new boolean[input.WIDTH][input.HEIGHT];
        dfs(input.posX[0], input.posY[0], input.WIDTH, input.HEIGHT);
        for (int i = 0; i < input.N; i++) {
            if (!connect[input.posX[i]][input.posY[i]]) {
                System.err.println("Panels are not connected.");
                score = -1;
                return;
            }
        }

        /* Calculate the score. */
        score = M;
    }
}
            