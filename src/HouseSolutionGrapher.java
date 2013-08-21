import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;


public class HouseSolutionGrapher extends JPanel{
    private static final boolean DEBUG = true;

    HashMap<Double, Double> data = new HashMap<Double, Double>();
    final int PAD = 20;
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        double xInc = (double)(w - 2.0*PAD)/(data.size()-1);
        double scale = (double)(h - 2.0*PAD)/getMax();
        for(Entry<Double, Double> pnt : data.entrySet()) {
            double x = PAD + pnt.getKey()*xInc;
            double y = h - PAD - scale*pnt.getValue();
            g2.fill(new Ellipse2D.Double(pnt.getValue()-2, pnt.getValue()-2, 4, 4));
            //draw the circles that go with this particular point
            //g2.drawOval(pnt.getKey()-, y, width, height)
        }
    }
    private double getMax() {
        double max = -Integer.MAX_VALUE;
        for(double k : data.values()) {
            if(k > max)
                max = k;
        }
        return max;
    }
}

