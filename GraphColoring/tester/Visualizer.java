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
    final int METER_WIDTH  = 50;
    final int PADDING = 10;
    final int VIS_SIZE_X = FIELD_WIDTH + PADDING * 2 + METER_WIDTH;
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
     * int input.N             Number of vertices.
     * int input.M             Number of edges.
     * int[] input.a           Edge vertex A.
     * int[] input.b           Edge vertex B.
     * int[][] input.edge      true is connected, false is not connected.   
     * int[] output.col        The color of the i-th vertex.
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
        g2.setColor(new Color(0x000000));
        g2.fillRect(PADDING, PADDING, FIELD_WIDTH, FIELD_HEIGHT);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw edges information. */
        int cell_width  = FIELD_WIDTH / input.N;
        int cell_height = FIELD_HEIGHT / input.N;
        for (int x = 0; x < input.N; x++) {
            for (int y = 0; y < input.N; y++) {
                if (!input.edge[x][y]) continue;
                Color c = Color.getHSBColor((1.0f / (float)input.N) * (float)output.col[x], 0.75f, 1.0f);
                g2.setColor(c);
                g2.fillRect(cell_width * x, cell_width * y, cell_width, cell_height);
            }
        }
        for (int x = 0; x < input.N; x++) {
            for (int y = 0; y < input.N; y++) {
                if (!input.edge[x][y]) continue;
                Color c = Color.getHSBColor((1.0f / (float)input.N) * (float)output.col[y], 0.75f, 1.0f);
                g2.setColor(c);
                g2.fillRect(cell_width * x + 2, cell_width * y + 2, cell_width - 4, cell_height - 4);
            }
        }

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(FIELD_WIDTH + 10, 0);

        /* Draw a number meter of colors. */
        for (int i = 0; i < input.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)input.N) * (float)i, 0.80f, 1.0f);
            g2.setColor(c);
            g2.fillRect(0, FIELD_HEIGHT / input.N * i, METER_WIDTH - 10, FIELD_HEIGHT / input.N);
        }
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(new Color(0x000000));
        g2.drawRect(0, 0, METER_WIDTH - 12, FIELD_HEIGHT / input.N * output.score);
        
        FontMetrics fm = g2.getFontMetrics();
        char[] ch = ("" + output.score).toCharArray();
        g2.setFont(new Font("Courier", Font.BOLD, 14));
        g2.drawChars(ch, 0, ch.length, METER_WIDTH / 4,FIELD_HEIGHT / input.N * (output.score - 1));

        return bi;
    }
}
