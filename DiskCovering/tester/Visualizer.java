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
     * int input.WIDTH        The width of the board.
     * int input.HEIGHT       The height of the board.
     * int input.N            Number of vertices.
     * int input.R            The radius of the disk.
     * int[] input.px         x coordinate of the i-th vertex.
     * int[] input.py         y coordinate of the i-th vertex.
     * int output.M           Number of disks.
     * int[] output.rx        x coordinate of the i-th disk.
     * int[] output.ry        y coordinate of the i-th disk.
     * int output.score       Score.
     *
     * @see InputData
     * @see OutputData
     */
    private BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw the background */
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_WIDTH, FIELD_HEIGHT);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw the disk */
        g2.setColor(new Color(0xF4F4FF));
        for (int i = 0; i < output.M; i++) {
            g2.fillOval(output.rx[i] - input.R, output.ry[i] - input.R, input.R * 2, input.R * 2);
        }
        g2.setColor(new Color(0x4169e1));
        for (int i = 0; i < output.M; i++) {
            g2.drawOval(output.rx[i] - input.R, output.ry[i] - input.R, input.R * 2, input.R * 2);
        }

        /* Draw the vertex */
        final int R1 = 6;
        for (int i = 0; i < input.N; i++) {
            g2.setColor(new Color(0x454552));
            g2.fillOval(input.px[i] - R1 / 2, input.py[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0x000000));
            g2.drawOval(input.px[i] - R1 / 2, input.py[i] - R1 / 2, R1, R1);
        }

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(-PADDING, -PADDING);

        /* Draw the edge of this image */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, PADDING);
        g2.fillRect(0, 0, PADDING, VIS_SIZE_Y);
        g2.fillRect(VIS_SIZE_X - PADDING, 0, PADDING, VIS_SIZE_Y);
        g2.fillRect(0, VIS_SIZE_Y - PADDING, VIS_SIZE_X, 10);

        return bi;
    }
}
