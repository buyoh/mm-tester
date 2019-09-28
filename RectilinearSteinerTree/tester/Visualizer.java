import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class Visualizer extends JFrame
{
    final int FIELD_HEIGHT = 1000;
    final int FIELD_WIDTH  = 1000;
    final int PADDING = 10;
    final int VIS_SIZE_X = FIELD_WIDTH + PADDING * 2;
    final int VIS_SIZE_Y = FIELD_HEIGHT + PADDING * 2;
    final Tester tester;

    public Visualizer (final Tester _tester)
    {
        this.tester = _tester;
    }

    public void saveImage (String fileName)
    {
        try {
            BufferedImage bi = drawImage();
            ImageIO.write(bi, "png", new File(fileName +".png"));
        } catch (Exception e) {
            System.err.println("Visualizer failed to save the image.");
            e.printStackTrace();
        }
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
            BufferedImage bi = drawImage();
            g.drawImage(bi, getInsets().left, getInsets().top, VIS_SIZE_X, VIS_SIZE_Y, null);
        } catch (Exception e) {
            System.err.println("Visualizer failed to draw.");
            e.printStackTrace();
        }
    }

    /**
     * int   tester.WIDTH   The width of the board.
     * int   tester.HEIGHT  The height of the board.
     * int   tester.N       The number of panels placed in advance.
     * int[] tester.x       The x coordinate of panels placed in advance.
     * int[] tester.y       The y coordinate of panels placed in advance.
     * int   tester.M       The number of panels to be placed later.
     * int[] tester.ax      The x coordinate of panels to be placed later.
     * int[] tester.ay      The y coordinate of panels to be placed later.
     *
     * @see Tester
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
        final int cellw = FIELD_WIDTH / tester.WIDTH;
        final int cellh = FIELD_HEIGHT / tester.HEIGHT; 

        g2.setColor(new Color(0xdc143c));
        for (int i = 0; i < tester.N; i++) {
            g2.fillRect(tester.x[i] * cellw, tester.y[i] * cellh, cellw, cellh);
        }

        g2.setColor(new Color(0x4169e1));
        for (int i = 0; i < tester.M; i++) {
            g2.fillRect(tester.ax[i] * cellw, tester.ay[i] * cellh, cellw, cellh);
        }

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(0xD3D3D3));
        for (int i = 1; i <= tester.WIDTH; i++) {
            g2.drawLine(i * cellw, 0, i * cellw, FIELD_HEIGHT);
        }
        for (int i = 1; i <= tester.HEIGHT; i++) {
            g2.drawLine(0, i * cellh, FIELD_WIDTH, i * cellh);
        }

        return bi;
    }
}
