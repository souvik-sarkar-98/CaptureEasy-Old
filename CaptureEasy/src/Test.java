import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class Test {

    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private JProgressBar jpbCircularProg;
        private Timer timer;
int i=1;
        public TestPane() {
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int iv = Math.min(100, i++);
                    jpbCircularProg.setValue(iv);
                    if (iv == 100) {
                    	timer.stop();    
                    	}
                }
            });
            jpbCircularProg = new JProgressBar();
            jpbCircularProg.setUI(new ProgressCircleUI());
            jpbCircularProg.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            //Border emptyBorder = BorderFactory.createEmptyBorder();
            //jpbCircularProg.setBorder(emptyBorder);
            jpbCircularProg.setStringPainted(true);
            jpbCircularProg.setFont(jpbCircularProg.getFont().deriveFont(24f));
            jpbCircularProg.setForeground(Color.ORANGE);
            if (jpbCircularProg.isVisible() == false) {
                jpbCircularProg.setVisible(true);
            }

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = GridBagConstraints.REMAINDER;
            add(jpbCircularProg, gbc);

            JButton toggle = new JButton("Toggle");
            toggle.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.restart();
                    }
                }
            });

            add(toggle, gbc);
        }

    }

    public class ProgressCircleUI extends BasicProgressBarUI {

        @Override
        public Dimension getPreferredSize(JComponent c) {
            Dimension d = super.getPreferredSize(c);
            int v = Math.max(d.width, d.height);
            d.setSize(v, v);
            return d;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Insets b = progressBar.getInsets(); // area for border
            int barRectWidth = progressBar.getWidth() - b.right - b.left;
            int barRectHeight = progressBar.getHeight() - b.top - b.bottom;
            if (barRectWidth <= 0 || barRectHeight <= 0) {
                return;
            }

            // draw the cells
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(progressBar.getForeground());
            double degree = 360 * progressBar.getPercentComplete();
            double sz = Math.min(barRectWidth, barRectHeight);
            double cx = b.left + barRectWidth * .5;
            double cy = b.top + barRectHeight * .5;
            double or = sz * .5;
            double ir = or * .5; //or - 20;
            Shape inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
            Shape outer = new Arc2D.Double(
                    cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);
            Area area = new Area(outer);
            area.subtract(new Area(inner));
            g2.fill(area);
            g2.dispose();

            // Deal with possible text painting
            if (progressBar.isStringPainted()) {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
            }
        }
    }

}