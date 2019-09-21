import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class Visualizer extends JFrame
{
    final int FIELD_HEIGHT = 1000;
    final int FIELD_WIDTH  = 1000;
    final int PADDING = 10;
    final int VIS_SIZE_X = FIELD_WIDTH + PADDING * 2;
    final int VIS_SIZE_Y = FIELD_HEIGHT + PADDING * 2;
    
    final Input input;
    final Output output;

    public Visualizer (final Input _input, final Output _output) throws Exception
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
     * int input.N          Number of vertices.
     * int[] input.posX     The x coordinate of the vertex.
     * int[] input.posY     The y coordinate of the vertex.
     * int[] output.perm    Order of visiting vertices.
     *
     * @see Input
     * @see Output
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

        /* Draw path */
        g2.setColor(new Color(0x000000));
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < input.N; i++) {
            int a = output.perm[i];
            int b = output.perm[(i + 1) % input.N];
            g2.drawLine(input.posX[a], input.posY[a], input.posX[b], input.posY[b]);
        }

        /* Draw vertex */
        final int R = 6;
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < input.N; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(input.posX[i] - R / 2, input.posY[i] - R / 2, R, R);
            g2.setColor(new Color(0x000000));
            g2.drawOval(input.posX[i] - R / 2, input.posY[i] - R / 2, R, R);
        }

        return bi;
    }
}
