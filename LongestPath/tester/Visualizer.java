import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
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
     * int   tester.N  Number of vertices.
     * int   tester.M  Number of edges.
     * int[] tester.x  The x coordinate of the vertex.
     * int[] tester.y  The y coordinate of the vertex.
     * int[] tester.a  Edge vertex A.
     * int[] tester.b  Edge vertex B.
     * int   tester.K  Number of vertices on simple path.
     * int[] tester.v  Simple path.
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

        /* Draw the graph */
        final int R1 = 6;
        g2.setColor(new Color(0xE0E0E0));
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i < tester.M; i++) {
            int a = tester.a[i];
            int b = tester.b[i];
            g2.drawLine(tester.x[a], tester.y[a], tester.x[b], tester.y[b]);
        }
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < tester.N; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(tester.x[i] - R1 / 2, tester.y[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0xE0E0E0));
            g2.drawOval(tester.x[i] - R1 / 2, tester.y[i] - R1 / 2, R1, R1);
        }

        /* Draw the path */
        final int R2 = 6;
        g2.setColor(new Color(0x000000));
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 1; i < tester.K; i++) {
            int a = tester.v[i - 1];
            int b = tester.v[i];
            g2.drawLine(tester.x[a], tester.y[a], tester.x[b], tester.y[b]);
        }
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < tester.K; i++) {
            int v = tester.v[i];
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(tester.x[v] - R2 / 2, tester.y[v] - R2 / 2, R2, R2);
            g2.setColor(new Color(0x000000));
            g2.drawOval(tester.x[v] - R2 / 2, tester.y[v] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
