package io.github.vhula.tpks.view.graph;

import io.github.vhula.tpks.model.graph.Connection;
import io.github.vhula.tpks.model.graph.Graph;
import io.github.vhula.tpks.model.graph.State;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vadym Hula
 * Date: 20.10.12
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class GraphPanel extends JPanel {

    private Graph graph;

    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    public GraphPanel(Graph graph) {
        this.graph = graph;
        setSize(graph.statesCount() * 400, 1024);
        setPreferredSize(new Dimension(graph.statesCount() * 400, 1024));
        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int koefWidth = 100;
        int koefHeight = getHeight() / 4;
        int shapeRadius = 100;
        g2.setStroke(new BasicStroke(4.0f));
        for (int i = 0; i < graph.statesCount(); i++) {
            Shape shape = new Ellipse2D.Double(koefWidth * (i + 1) + shapeRadius * i, koefHeight, shapeRadius, shapeRadius);
            g2.setColor(Color.YELLOW);
            g2.fill(shape);
            g2.setColor(Color.BLUE);
            g2.draw(shape);
            g2.setFont(new Font("LucidaSans", Font.BOLD, 14));
            g2.setColor(Color.BLUE.darker());
            g2.drawString(graph.getState(i).toString(), koefWidth * (i + 1) + shapeRadius * i + 10, koefHeight + 35);
            shapes.add(shape);
        }
        ArrayList<Integer> starts = new ArrayList<Integer>();
        ArrayList<Integer> ends = new ArrayList<Integer>();
        int prev = -1;
        int opt = 0;
        int opt2 = 0;
        int loop = 50;
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i < graph.connectionsCount(); i++) {
            Connection connection = graph.getConnections().get(i);
            State start = connection.getStartState();
            State end = connection.getEndState();
            int startIdx = start.getNumber();
            int endIdx = end.getNumber();
            String conString = connection.toStringConnection();
            g2.setColor(Color.BLACK);
            Shape startShape = shapes.get(startIdx);
            Shape endShape = shapes.get(endIdx);
            if (starts.contains(startIdx)) {
                opt += 50;
                opt2 += 10;
            } else {
                if (ends.contains(endIdx)) {
                    opt2 += 10;
                } else {
                    opt2 = 0;
                }
                opt = 0;
            }
            while (opt2 > shapeRadius / 3) {
                opt2--;
            }
            int sx = (int) (startShape.getBounds2D().getCenterX()) + opt2 + loop / 10;
            int sy = (int) (startShape.getBounds2D().getCenterY() - startShape.getBounds2D().getHeight() / 2) + opt2 / 2;
            if (i % 2 == 0)
                sy = (int) (startShape.getBounds2D().getCenterY() + startShape.getBounds2D().getHeight() / 2) - opt2 / 2;
            int sx2 = sx;
            int sy2 = sy - loop - opt - i * 10;
            if (i % 2 == 0)
                sy2 = sy + loop + opt + i * 10;
            int ex = (int) (endShape.getBounds2D().getCenterX()) - opt2 - loop / 10;
            int ey = (int) (endShape.getBounds2D().getCenterY() - endShape.getBounds2D().getHeight() / 2) + opt2 / 2;
            if (i % 2 == 0)
                ey = (int) (endShape.getBounds2D().getCenterY() + endShape.getBounds2D().getHeight() / 2) - opt2 / 2;
            int ex2 = ex;
            int ey2 = ey - loop - opt - i * 10;
            if (i % 2 == 0)
                ey2 = ey + loop + opt + i * 10;
            if (sy < 0 || sy2 < 0) {
                setPreferredSize(new Dimension(getWidth(), getHeight() * 2));
            }
            g2.drawLine(sx, sy, sx2, sy2);
            g2.drawLine(sx2, sy2, ex2, ey2);
            g2.drawLine(ex2, ey2, ex, ey);
            g2.fillOval(ex - 5, ey - 5, 15, 15);
            if (endIdx < startIdx)
                g2.drawString(conString, sx2 - 10, sy2 - 10);
            else
                g2.drawString(conString, sx2 + 10, sy2 + 10);
            starts.add(startIdx);
            ends.add(endIdx);
        }

    }

    public Graph getGraph() {
        return graph;
    }

}
