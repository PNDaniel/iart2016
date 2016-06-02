import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.samples.InternalFrameSatelliteViewDemo;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.*;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import javax.swing.Renderer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

// http://stackoverflow.com/questions/8458970/change-size-color-of-vertex-in-jung

public class SimpleGraphView {

    public SimpleGraphView(ArrayList<Node> nodes, ArrayList<Edge> edges, boolean check) {
        // Create a graph with Integer vertices and String edges
        //Graph<Integer, String> g = new SparseGraph<Integer, String>();
        DirectedSparseMultigraph<Integer, String> g = new DirectedSparseMultigraph<Integer, String>();

        for (int i = 0; i < nodes.size(); i++)
            g.addVertex(nodes.get(i).getNumID());
        for (int i = 0; i < edges.size(); i++) {
            for (int j : g.getVertices()) {
                if (edges.get(i).getSource().getNumID() == j)
                    for (int h : g.getVertices()) {
                        if (edges.get(i).getDestination().getNumID() == h)
                            g.addEdge(edges.get(i).getId(), j, h);
                    }
            }
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
                if (check == true)
                    if (nodes.get(i).getSolution() == true)
                        return Color.GREEN;
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
                if (check == true)
                    for (int i = 0; i < edges.size(); i++) {
                        if (s == edges.get(i).getId())
                            if (edges.get(i).getSolution())
                                return Color.GREEN;
                    }
                return Color.BLACK;
            }
        };
        Transformer<Integer, String> vertexName = new Transformer<Integer, String>() {
            @Override
            public String transform(Integer i) {
                return nodes.get(i).getId();
            }
        };

        Transformer<String, String> edgeLabel = new Transformer<String, String>() {
            @Override
            public String transform(String s) {
                if (check == true)
                    for (int i = 0; i < edges.size(); i++) {
                        if (s == edges.get(i).getId())
                            if (edges.get(i).getSolution())
                                return s;
                    }
                return s;
            }
        };
        Transformer<String, Paint> edgeLabel1 = new Transformer<String, Paint>() {
            @Override
            public Paint transform(String s) {
                if (check == true)
                    for (int i = 0; i < edges.size(); i++) {
                        if (s == edges.get(i).getId())
                            if (edges.get(i).getSolution())
                                return Color.GREEN;
                    }
                return Color.BLACK;
            }
        };
        vv.getRenderContext().setVertexLabelTransformer(vertexName);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        //vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        vv.getRenderContext().setEdgeLabelTransformer(edgeLabel);

        vv.getRenderContext().setLabelOffset(15);

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