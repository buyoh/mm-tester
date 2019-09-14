import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class Visualizer extends JFrame
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

    public void visualize ()
    {
        this.setVisible(true);
        Insets insets = getInsets();
        final int width  = VIS_SIZE_X + insets.left + insets.right;
        final int height = VIS_SIZE_Y + insets.top + insets.bottom;
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void paint (Graphics g)
    {
        try {
            super.paint(g);
            BufferedImage bi = drawImage();
            g.drawImage(bi, getInsets().left, getInsets().top, VIS_SIZE_X, VIS_SIZE_Y, null);
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    /**
     * int input.N           Number of destinations.
     * int input.M           Number of trucks.
     * int depotX,depotY     Warehouse coordinates.
     * int[] posX,posY       The coordinates of the i-th destination.
     * int[] cap             Loading capacity of the i-th truck.
     * int[] speed           The traveling speed of the i-th track.
     *
     * int output.K          The number of lines in the answer.
     * int[] T               Track number.
     * int[] L               Number of destinations.
     * int[][] D             Destination number.
     * double[] dist         Total mileage of the i-th track.
     * double[] time         Total travel time for the i-th track.
     * int[] last_idx        The destination number last delivered by the i-th truck.
     * double score          Score.
     * 
     * @see InputData
     * @see OutputData
     */
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
        final int vht = FIELD_HEIGHT / 10;
        final int vwt = VEHICLE_VIEW_WIDTH - 10;
        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < input.M; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillRect(0, vht * i, vwt, vht - 10);
            Color c = Color.getHSBColor((1.0f / (float)input.M) * (float)i, 1.0f, 0.95f);
            g2.setColor(c);
            g2.drawRect(0, vht * i, vwt, vht - 10);
        }

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(10, 0);

        int worst_idx = -1;
        double worst_time = -1.0;
        for (int i = 0; i < input.M; i++) {
            if (worst_time < output.dist[i] / (double)input.speed[i]) {
                worst_time = output.dist[i] / (double)input.speed[i];
                worst_idx = i;
            }
        }

        for (int i = 0; i < input.M; i++) {
            g2.setColor(new Color(0x000000));
            FontMetrics fm = g2.getFontMetrics();
            char[] ch0 = ("Vehicle #" + i).toCharArray();
            char[] ch1 = ("capacity : " + input.cap[i]).toCharArray();
            char[] ch2 = ("speed : " + input.speed[i]).toCharArray();
            char[] ch3 = ("distance : " + output.dist[i]).toCharArray();
            char[] ch4 = ("time : " + output.dist[i] / (double)input.speed[i] + "\n").toCharArray();
            g2.setFont(new Font("Courier", Font.BOLD, 14));
            g2.drawChars(ch0, 0, ch0.length, 0, i * 100 + 20);
            g2.setFont(new Font("Courier", Font.BOLD, 12));
            g2.drawChars(ch1, 0, ch1.length, 0, i * 100 + 34);
            g2.drawChars(ch2, 0, ch2.length, 0, i * 100 + 49);
            g2.drawChars(ch3, 0, ch3.length, 0, i * 100 + 64);
            if (i == worst_idx) {
                g2.setFont(new Font("Courier", Font.BOLD, 12));
                g2.setColor(new Color(0xFF0000));
            }
            g2.drawChars(ch4, 0, ch4.length, 0, i * 100 + 79);
            
        }
        return bi;
    }

}
