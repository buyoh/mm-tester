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
     * int input.WIDTH          The width of the board.
     * int input.HEIGHT         The height of the board.
     * int input.N              The number of panels placed in advance.
     * int[] input.posX         The x coordinate of panels placed in advance.
     * int[] input.posY         The y coordinate of panels placed in advance.
     * int output.M             The number of panels to be placed later.
     * int[] output.addPosX     The x coordinate of panels to be placed later.
     * int[] output.addPosY     The y coordinate of panels to be placed later.
     * int output.score         Score.
     + 
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

        /* Draw pannels */
        final int cellw = FIELD_WIDTH / input.WIDTH;
        final int cellh = FIELD_HEIGHT / input.HEIGHT; 

        g2.setColor(new Color(0xdc143c));
        for (int i = 0; i < input.N; i++) {
            g2.fillRect(input.posX[i] * cellw, input.posY[i] * cellh, cellw, cellh);
        }

        g2.setColor(new Color(0x4169e1));
        for (int i = 0; i < output.M; i++) {
            g2.fillRect(output.addPosX[i] * cellw, output.addPosY[i] * cellh, cellw, cellh);
        }

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(0xD3D3D3));
        for (int i = 1; i <= input.WIDTH; i++) {
            g2.drawLine(i * cellw, 0, i * cellw, FIELD_HEIGHT);
        }
        for (int i = 1; i <= input.HEIGHT; i++) {
            g2.drawLine(0, i * cellh, FIELD_WIDTH, i * cellh);
        }

        return bi;
    }
}
