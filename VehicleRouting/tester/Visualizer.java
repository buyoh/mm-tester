import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class Visualizer extends JPanel
{
    final int FIELD_HEIGHT = 1000;
    final int FIELD_WIDTH  = 1000;
    final int VEHICLE_VIEW_WIDTH = 250;
    final int PADDING = 10;
    final int VIS_SIZE_X = FIELD_WIDTH + VEHICLE_VIEW_WIDTH + PADDING * 2;
    final int VIS_SIZE_Y = FIELD_HEIGHT + PADDING * 2;
    
    final InputData input;
    final OutputData output;

    public Visualizer (final InputData _input, final OutputData _output) throws Exception
    {
        this.input = _input;
        this.output = _output;
    }

    public void saveImage (String fileName) throws IOException
    {
        BufferedImage bi = drawImage();
        ImageIO.write(bi, "png", new File(fileName +".png"));
    }

    @Override
    public void paint (Graphics g)
    {
        try {
            super.paint(g);
            BufferedImage bi = drawImage();
            g.drawImage(bi, 0, 0, VIS_SIZE_X, VIS_SIZE_Y, null);
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    private BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* Draw background */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_WIDTH, FIELD_HEIGHT);

        /* input
         * int N,M;
         * int depotX,depotY;
         * int[] posX,posY;
         * int[] cap;
         * int[] speed;
         *
         * output
         * int K;
         * int[] T;
         * int[] L;
         * int[][] D;
         * double[] dist:
         * double[] time;
         * int[] last_idx;
         * double score;
         */

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw delivery routes */
        int[] last_idx = new int[input.M];
        Arrays.fill(last_idx, -1);
        for (int i = 0; i < output.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.M) * (float)output.T[i], 1.0f, 0.95f);
            g2.setColor(c);
            if (last_idx[output.T[i]] >= 0) {
                g2.drawLine(input.posX[last_idx[output.T[i]]], input.posY[last_idx[output.T[i]]], 
                            input.depotX, input.depotY);
            }
            int cur_x = input.depotX;
            int cur_y = input.depotY;
            for (int j = 0; j < output.L[i]; j++) {
                int nxt_x = input.posX[output.D[i][j]];
                int nxt_y = input.posY[output.D[i][j]];
                g2.drawLine(cur_x, cur_y, nxt_x, nxt_y);
                cur_x = nxt_x;
                cur_y = nxt_y;
            }
            last_idx[output.T[i]] = output.D[i][output.L[i] - 1];
        }

        /* Draw the destinations */
        final int R1 = 8;
        for (int i = 0; i < output.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.M) * (float)output.T[i], 1.0f, 0.95f);
            for (int j = 0; j < output.L[i]; j++) {
                int idx = output.D[i][j];
                g2.setColor(c);
                g2.fillOval(input.posX[idx] - R1 / 2, input.posY[idx] - R1 / 2, R1, R1);
                g2.setColor(new Color(0x000000));
                g2.drawOval(input.posX[idx] - R1 / 2, input.posY[idx] - R1 / 2, R1, R1);
            }
        }

        /* Draw the depot */
        final int R2 = 20;
        g2.setColor(new Color(0x000000));
        g2.fillOval(input.depotX - R2 / 2, input.depotY - R2 / 2, R2, R2);


        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(FIELD_WIDTH + PADDING, 0);

        /* Draw information of vehicles */
        final int vht = FIELD_HEIGHT / 10 + 1;
        final int vwt = VEHICLE_VIEW_WIDTH - 10;
        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < input.M; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillRect(0, vht * i, vwt, vht - 10);
            Color c = Color.getHSBColor((1.0f / (float)input.M) * (float)i, 1.0f, 0.95f);
            g2.setColor(c);
            g2.drawRect(0, vht * i, vwt, vht - 10);
        }

        int worst_idx = -1;
        double worst_time = -1.0;
        for (int i = 0; i < input.M; i++) {
            if (worst_time < output.dist[i] / (double)input.speed[i]) {
                worst_time = output.dist[i] / (double)input.speed[i];
                worst_idx = i;
            }
        }


        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(10, 0);

        for (int i = 0; i < input.M; i++) {
            g2.setColor(new Color(0x000000));
            FontMetrics fm = g2.getFontMetrics();
            char[] ch0 = ("Vehicle #" + i).toCharArray();
            char[] ch1 = ("capacity : " + input.cap[i]).toCharArray();
            char[] ch2 = ("speed : " + input.speed[i]).toCharArray();
            char[] ch3 = ("distance : " + output.dist[i]).toCharArray();
            char[] ch4 = ("time : " + output.dist[i] / (double)input.speed[i] + "\n").toCharArray();
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawChars(ch0, 0, ch0.length, 0, i * 100 + 28);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.drawChars(ch1, 0, ch1.length, 0, i * 100 + 42);
            g2.drawChars(ch2, 0, ch2.length, 0, i * 100 + 57);
            g2.drawChars(ch3, 0, ch3.length, 0, i * 100 + 72);
            if (i == worst_idx) {
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.setColor(new Color(0xFF0000));
            }
            g2.drawChars(ch4, 0, ch4.length, 0, i * 100 + 87);
            
        }
        return bi;
    }

}
