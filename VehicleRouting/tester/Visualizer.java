import java.io.*;
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

        /* Draw delivery routes */
        /*for (int i = 0; i < output.plan.length; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.M) * (float)i, 1.0f, 0.95f);
            g2.setColor(c);
            int cur_x = input.depotX;
            int cur_y = input.depotY;
            for (int j = 0; j < output.plan[i].length; j++) {
                int idx = output.plan[i][j];
                if (j % input.cap[i] == 0) {
                    g2.drawLine(cur_x + PADDING, cur_y + PADDING, input.depotX + PADDING, input.depotY + PADDING);
                    cur_x = input.depotX;
                    cur_y = input.depotY;
                }
                g2.drawLine(cur_x + PADDING, cur_y + PADDING, input.posX[idx] + PADDING, input.posY[idx] + PADDING);
                cur_x = input.posX[idx];
                cur_y = input.posY[idx];
            }
            g2.drawLine(cur_x + PADDING, cur_y + PADDING, input.depotX + PADDING, input.depotY + PADDING);
        }*/

        /* Draw the destinations */
        /*final int R1 = 8;
        for (int i = 0; i < output.plan.length; i++) {
            for (int j = 0; j < output.plan[i].length; j++) {
                Color c = Color.getHSBColor((1.0f / (float)input.M) * (float)i, 1.0f, 0.95f);
                g2.setColor(c);
                int idx = output.plan[i][j];
                g2.fillOval(input.posX[idx] + PADDING - R1 / 2, input.posY[idx] + PADDING  - R1 / 2, R1, R1);
                g2.setColor(new Color(0x000000));
                g2.drawOval(input.posX[idx] + PADDING - R1 / 2, input.posY[idx] + PADDING - R1 / 2, R1, R1);
            }
        }*/

        /* Draw the depot */
        /*final int R2 = 20;
        g2.setColor(new Color(0x000000));
        g2.fillOval(input.depotX + PADDING - R2 / 2, input.depotY + PADDING - R2 / 2, R2, R2);
*/
        /* Draw information of vehicles */
        /*int worst_idx = -1;
        double worst_time = -1.0;
        for (int i = 0; i < input.M; i++) {
            if (worst_time < dist[i] / (double)speed[i]) {
                worst_time = dist[i] / (double)speed[i];
                worst_idx = i;
            }
        }*/

  //      BasicStroke wideStroke = new BasicStroke(2.0f);
    //    g2.setStroke(wideStroke);
      //  for (int i = 0; i < input.M; i++) {
        //    g2.setColor(new Color(0xFFFFFF));
          //  g2.fillRect(FIELD_WIDTH + 20, i * 100 + 10, VEHICLE_VIEW_WIDTH - 10, 90);
           // Color c = Color.getHSBColor((1.0f / (float)input.M) * (float)i, 1.0f, 0.95f);
            //g2.setColor(c);
            //g2.drawRect(FIELD_WIDTH + 20, i * 100 + 10, VEHICLE_VIEW_WIDTH - 10, 90);
            
            /*g2.setColor(new Color(0x000000));
            FontMetrics fm = g2.getFontMetrics();
            char[] ch0 = ("Vehicle" + i).toCharArray();
            char[] ch1 = ("capacity : " + cap[i]).toCharArray();
            char[] ch2 = ("speed : " + speed[i]).toCharArray();
            char[] ch3 = ("distance : " + dist[i]).toCharArray();
            char[] ch4 = ("time : " + dist[i] / (double)speed[i] + "\n").toCharArray();
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawChars(ch0, 0, ch0.length, FIELD_WIDTH + 30, i * 100 + 28);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.drawChars(ch1, 0, ch1.length, FIELD_WIDTH + 30, i * 100 + 42);
            g2.drawChars(ch2, 0, ch2.length, FIELD_WIDTH + 30, i * 100 + 57);
            g2.drawChars(ch3, 0, ch3.length, FIELD_WIDTH + 30, i * 100 + 72);

            if (i == worst_idx) {
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.setColor(new Color(0xFF0000));
            }
            g2.drawChars(ch4, 0, ch4.length, FIELD_WIDTH + 30, i * 100 + 87);
            */
        //}
            return bi;
    }

}
