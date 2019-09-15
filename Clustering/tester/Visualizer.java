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

        Color [] dotColor = new Color[input.N];
        for (int i = 0; i < input.N; i++) {
            int idx = -1;
            double dist = 1.0e9;
            for (int j = 0; j < input.K; j++) {
                int lx = Math.abs(input.posX[i] - output.centorX[j]);
                int ly = Math.abs(input.posY[i] - output.centorY[j]);
                double dt = Math.sqrt((double)(lx * lx + ly * ly));
                if (dist > dt) {
                    dist = dt;
                    idx = j;
                }
            }

            dotColor[i] = Color.getHSBColor((1.0f / (float)input.K) * (float)idx, 1.0f, 0.95f);
            g2.setColor(dotColor[i]);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(PADDING + input.posX[i], PADDING + input.posY[i], PADDING + output.centorX[idx], PADDING + output.centorY[idx]);
        }

        for (int i = 0; i < input.N; i++) {
            g2.setColor(dotColor[i]);
            g2.fillOval(input.posX[i] + 6, input.posY[i] + 6, 8, 8);
            g2.setColor(new Color(0x000000));
            g2.drawOval(input.posX[i] + 6, input.posY[i] + 6, 8, 8);
        }

        g2.setStroke(new BasicStroke(3.0f));
        for (int i = 0; i < input.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.K) * (float)i, 1.0f, 1.0f);
            g2.setColor(c);
            g2.fillOval(output.centorX[i] + 4, output.centorY[i] + 4, 12, 12);
            g2.setColor(new Color(0x000000));
            g2.drawOval(output.centorX[i] + 4, output.centorY[i] + 4, 12, 12);
        }

        return bi;
    }
}
