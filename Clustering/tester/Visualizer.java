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
     * int input.N               Number of vertexs.
     * int input.K               Number of centor vertexs.
     * int[] input.posX          The x coordinate of the vertex.
     * int[] input.posY          The y coordinate of the vertex.
     * int[] output.centorX      The x coordinate of the centor vertex.
     * int[] output.centorY      The y coordinate of the centor vertex.
     * int[] output.belongV      Centor number to which each vertex belongs.
     * double output.score       Score
     *
     * @see InputData
     * @see OutputData
     */
    private BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_WIDTH, FIELD_HEIGHT);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw lines */
        Color [] dotColor = new Color[input.N];
        for (int i = 0; i < input.N; i++) {
            final int bidx = output.belongV[i];
            Color c = Color.getHSBColor((1.0f / (float)input.K) * (float)bidx, 1.0f, 0.95f);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(input.posX[i], input.posY[i], output.centorX[bidx], output.centorY[bidx]);
        }

        /* Draw vertexs */
        final int R1 = 8;
        for (int i = 0; i < input.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.K) * (float)output.belongV[i], 1.0f, 0.95f);
            g2.setColor(c);
            g2.fillOval(input.posX[i] - R1 / 2, input.posY[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0x000000));
            g2.drawOval(input.posX[i] - R1 / 2, input.posY[i] - R1 / 2, R1, R1);
        }

        final int R2 = 12;
        g2.setStroke(new BasicStroke(3.0f));
        for (int i = 0; i < input.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.K) * (float)i, 1.0f, 1.0f);
            g2.setColor(c);
            g2.fillOval(output.centorX[i] - R2 / 2, output.centorY[i] - R2 / 2, R2, R2);
            g2.setColor(new Color(0x000000));
            g2.drawOval(output.centorX[i] - R2 / 2, output.centorY[i] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
