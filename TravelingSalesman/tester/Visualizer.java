/**
 * Visualizer class.
 * @author kosakkun
 */

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class Visualizer extends JPanel implements WindowListener 
{
    final int FIELD_HEIGHT = 1000;
    final int FIELD_WIDTH  = 1000;
    final int PADDING = 10;
    final int VIS_SIZE_X = FIELD_WIDTH + PADDING * 2;
    final int VIS_SIZE_Y = FIELD_HEIGHT + PADDING * 2;
    final BufferedImage bi;

    @Override
    public void paint (Graphics g)
    {
        try {
            super.paint(g);
            g.drawImage(bi, 0, 0, VIS_SIZE_X, VIS_SIZE_Y, null);
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}

    /**
     * Output visualized image as png format data.
     * @param fileName The name of the image data to be output.
     */
    public void saveImage (String fileName) throws IOException
    {
        ImageIO.write(bi, "png", new File(fileName +".png"));
    }

    /**
     * Draw from input data and output data.
     * @param input The input data of the problem.
     * @param output The output data of the problem.
     * @see InputData
     * @see OutputData
     */
    public Visualizer (final InputData input, final OutputData output) throws Exception
    {
        bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw background */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_WIDTH, FIELD_HEIGHT);

        /*
         * input.N     Number of vertices.
         * input.posX  The x coordinate of the vertex.
         * input.posY  The y coordinate of the vertex.
         * output.perm Order of visiting vertices.
         */

        /* Draw path */
        g2.setColor(new Color(0x000000));
        for (int i = 0; i < input.N; i++) {
            int a = output.perm[i];
            int b = output.perm[(i + 1) % input.N];
            g2.drawLine(input.posX[a] + PADDING, input.posY[a] + PADDING, 
                        input.posX[b] + PADDING, input.posY[b] + PADDING);
        }

        /* Draw vertex */
        final int R = 8;
        for (int i = 0; i < input.N; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(input.posX[i] + PADDING - R / 2, input.posY[i] + PADDING - R / 2, R, R);
            g2.setColor(new Color(0x000000));
            g2.drawOval(input.posX[i] + PADDING - R / 2, input.posY[i] + PADDING - R / 2, R, R);
        }   
    }
}
