import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class Visualizer extends JPanel implements WindowListener {
        
    final InputData input;
    final OutputData output;
    final int VIS_SIZE = 1020;

    public void paint(Graphics g) {

        try {
            BufferedImage bi = new BufferedImage(VIS_SIZE, VIS_SIZE, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D)bi.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(new Color(0xD3D3D3));
            g2.fillRect(0, 0, VIS_SIZE, VIS_SIZE);
            g2.setColor(new Color(0xFFFFFF));
            g2.fillRect(10, 10, VIS_SIZE - 20, VIS_SIZE - 20);

            g2.setColor(new Color(0x000000));
            for (int i = 0; i < input.N; i++) {
                int a = output.perm[i];
                int b = output.perm[(i + 1) % input.N];
                g2.drawLine(input.posX[a] + 10, input.posY[a] + 10, 
                            input.posX[b] + 10, input.posY[b] + 10);
            }

            for (int i = 0; i < input.N; i++) {
                g2.setColor(new Color(0xFFFFFF));
                g2.fillOval(input.posX[i] + 6, input.posY[i] + 6, 8, 8);
                g2.setColor(new Color(0x000000));
                g2.drawOval(input.posX[i] + 6, input.posY[i] + 6, 8, 8);
            }

            /*if (numb) {
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                FontMetrics fm = g2.getFontMetrics();
                for (int i = 0; i < input.N; ++i) {
                    char[] ch = ("" + i).toCharArray();
                    int x = input.posX[i] + 5;
                    int y = input.posY[i] + 5;
                    g2.drawChars(ch, 0, ch.length, x, y);
                }
            }*/

            g.drawImage(bi, 0, 0, VIS_SIZE, VIS_SIZE, null);
            //if (save) {
            //    ImageIO.write(bi, "png", new File(fileName +".png"));
            //}

        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    public Visualizer (final InputData input_, final OutputData output_) {
        this.input = input_;
        this.output = output_;
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowActivated(WindowEvent e) { }
    public void windowDeactivated(WindowEvent e) { }
    public void windowOpened(WindowEvent e) { }
    public void windowClosed(WindowEvent e) { }
    public void windowIconified(WindowEvent e) { }
    public void windowDeiconified(WindowEvent e) { }

}
