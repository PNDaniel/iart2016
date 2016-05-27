import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

import javax.swing.*;
import java.util.ArrayList;

public class WeightedGraph {

    ArrayList<Node> nodes = new ArrayList<Node>();
    ArrayList<Edge> edges = new ArrayList<Edge>();

    private void createAndShowGui() {
        JFrame frame = new JFrame();
        frame.setTitle("Determined Travel Paths with Applied Research");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ListenableGraph<String, MyEdge> g = buildGraph();
        JGraphXAdapter<String, MyEdge> graphAdapter = new JGraphXAdapter<String, MyEdge>(g);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        frame.add(new mxGraphComponent(graphAdapter));
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public WeightedGraph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }*/

    public static class MyEdge extends DefaultWeightedEdge {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }

    public ListenableGraph<String, MyEdge> buildGraph() {
        ListenableDirectedWeightedGraph<String, MyEdge> g = new ListenableDirectedWeightedGraph<String, MyEdge>(MyEdge.class);

       /* String v1 = "Porto";
        String v2 = "Braga";
        String v3 = "Lisboa";
        String v4 = "Faro";
        String v5 = "Rio Tinto";
        String v6 = "Gaia";*/

        // Add nodes to graph
        for (int i = 0; i < nodes.size(); i++) {
            g.addVertex(nodes.get(i).getId());
        }

        for (int i = 0; i < edges.size(); i++) {
            MyEdge e = g.addEdge(edges.get(i).getSource().getId(), edges.get(i).getDestination().getId());
            g.setEdgeWeight(e, edges.get(i).getDistance());
        }

        /*MyEdge e = g.addEdge(v1, v2);
        g.setEdgeWeight(e, 2.0);
        e = g.addEdge(v1, v3);
        g.setEdgeWeight(e, 2.0);
        e = g.addEdge(v2, v4);
        g.setEdgeWeight(e, 4.5);
        e = g.addEdge(v4, v6);
        g.setEdgeWeight(e, 0.5);
        e = g.addEdge(v3, v5);
        g.setEdgeWeight(e, 1.25);
        e = g.addEdge(v5, v6);
        g.setEdgeWeight(e, 3.0);
        e = g.addEdge(v6, v5);
        g.setEdgeWeight(e, 1.75);*/
        return g;
    }
}