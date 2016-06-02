import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

// http://stackoverflow.com/questions/8458970/change-size-color-of-vertex-in-jung

public class SimpleGraphView {

    public SimpleGraphView(ArrayList<Node> nodes, ArrayList<Edge> edges, boolean check) {
        // Create a graph with Integer vertices and String edges
        Graph<Integer, String> g = new SparseGraph<Integer, String>();

        for (int i = 0; i < nodes.size(); i++)
            g.addVertex(nodes.get(i).getNumID());
        //for(int i = 0; i < 5; i++) g.addVertex(i);
        for (int i = 0; i < edges.size(); i++) {
            g.addEdge(edges.get(i).getId(), edges.get(i).getSource().getNumID(), edges.get(i).getDestination().getNumID());
        }

        // Layout implements the graph drawing logic
        Layout<Integer, String> layout = new CircleLayout<Integer, String>(g);
        layout.setSize(new Dimension(800, 600));

        // VisualizationServer actually displays the graph
        BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(800, 600)); //Sets the viewing area size

        // Transformer maps the vertex number to a vertex property
        Transformer<Integer, Paint> vertexColor = new Transformer<Integer, Paint>() {
            public Paint transform(Integer i) {
                if (i == 1) return Color.GREEN;
                return Color.BLACK;
            }
        };
        Transformer<Integer, Shape> vertexSize = new Transformer<Integer, Shape>() {
            public Shape transform(Integer i) {
                //Shape rectangle = new Rectangle2D.Float(-20, -10, 40, 20);
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
                // in this case, the vertex is twice as large
                if (i == 2) return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
                else return circle;
            }
        };
        Transformer<String, Paint> edgePaint = new Transformer<String, Paint>() {
            @Override
            public Paint transform(String s) {    // s represents the edge
               /* if (...){    // your condition
                    return Color.RED;
                }
                else {
                    return Color.DARK_GRAY;
                }*/
                return Color.BLACK;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        //vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        //vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

        JFrame frame;
        Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        Dimension windowSize = new Dimension(new Dimension(800, 600));
        int wdwLeft = 400 + screenSize.width / 2 - windowSize.width / 2;
        int wdwTop = screenSize.height / 2 - windowSize.height / 2;

        if (check == true) {
            frame = new JFrame("Simple Graph View - Solution");
            frame.setLocation(wdwLeft, wdwTop);
        } else {
            frame = new JFrame("Simple Graph View - Start");
            frame.setLocation(wdwLeft - 900, wdwTop);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}