import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class Visualizer extends JFrame
{
    final int WIDTH  = 1000;
    final int HEIGHT = 1000;
    final int PADDING = 10;
    final int VIS_SIZE_X = 1000 + PADDING * 2;
    final int VIS_SIZE_Y = 1000 + PADDING * 2;
    
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
     * int input.N
     * int[] input.h
     * int[] input.w
     * int[] output.posX
     * int[] output.posY
     * int output.score
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
        g2.fillRect(PADDING, PADDING, WIDTH, HEIGHT);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw rectangles */
        for (int i = 0; i < input.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.N) * (float)i, 0.60f, 1.0f);
            g2.setColor(c);
            g2.fillRect(output.posX[i], HEIGHT - output.posY[i] - input.h[i], input.w[i], input.h[i]);
            g2.setColor(new Color(0x3F3F3F));
            g2.drawRect(output.posX[i], HEIGHT - output.posY[i] - input.h[i], input.w[i], input.h[i]);
        }

        /* Draw score */
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(0, HEIGHT - output.score, WIDTH, HEIGHT - output.score);

        g2.setFont(new Font("Courier", Font.BOLD, 15));
        FontMetrics fm = g2.getFontMetrics();
        char[] ch = ("Score = " + output.score).toCharArray();
        int x = WIDTH / 9 * 4;
        int y = HEIGHT - output.score - 5;
        g2.drawChars(ch, 0, ch.length, x, y);
     
        return bi;
    }
}
