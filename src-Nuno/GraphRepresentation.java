import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * http://stackoverflow.com/questions/6162618/java-graph-library-for-dynamic-visualisation
 * https://jgraph.github.io/mxgraph/docs/manual_javavis.html
 * Customize Graph:
 * http://stackoverflow.com/questions/4051765/customizing-jgraphx
 * http://www.vainolo.com/wp-content/uploads/2011/05/Capture.png
 * https://www.vainolo.com/2011/04/11/another-day-with-jgraph-styles-and-constrained-children/
 */
public class GraphRepresentation extends JFrame {
    ArrayList<Node> nodes = new ArrayList<Node>();
    ArrayList<Edge> edges = new ArrayList<Edge>();

    public GraphRepresentation(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        super("Determined Travel Paths with Applied Research");

        this.nodes = nodes;
        this.edges = edges;
        mxGraph graph = new mxGraph() {
            // Make all edges unmovable
            public boolean isCellMovable(Object cell) {
                return !getModel().isEdge(cell);
            }

            // Make all vertex boxes unresizable
            public boolean isCellResizable(Object cell) {
                return !getModel().isVertex(cell);
            }
        };
        Object parent = graph.getDefaultParent();

        // Make all vertices and edges uneditable
        graph.setCellsEditable(false);
        // Make all edges unbendable
        graph.setCellsBendable(false);

        graph.getModel().beginUpdate();
        try {
            //System.out.println("Here");
            for (int i = 0; i < nodes.size(); i++) {
                graph.insertVertex(parent, null, nodes.get(i).getId(), nodes.get(i).getPos_x(), nodes.get(i).getPos_y(), 80, 30);
            }
            for (int i = 0; i < edges.size(); i++) {
                graph.insertEdge(parent, null, edges.get(i).getDistance(), edges.get(i).getSource(), edges.get(i).getDestination());
            }
            /*Object v1 = graph.insertVertex(parent, null, "Porto", 20, 20, 80, 30);
            Object v2 = graph.insertVertex(parent, null, "Braga", 240, 150, 80, 30);
            Object v3 = graph.insertVertex(parent, null, "Lisboa",  80, 200, 80, 30);
            Object v4 = graph.insertVertex(parent, null, "Faro", 140, 150, 80, 30);
            Object v5 = graph.insertVertex(parent, null, "Rio Tinto", 240, 20, 80, 30);
            Object v6 = graph.insertVertex(parent, null, "Gaia", 200, 300, 80, 30);

            graph.insertEdge(parent, null, "3.0", v1, v2);
            graph.insertEdge(parent, null, "2.0", v1, v2);
            Object e = graph.insertEdge(parent, null, "1.2", v1, v3);
            graph.insertEdge(parent, null, "5.4", v2, v4);
            graph.insertEdge(parent, null, "0.5", v3, v5);
            graph.insertEdge(parent, null, "1.25", v2, v6);
            Object e1 = graph.insertEdge(parent, null, "3.0", v3, v5);
            Object[] obj = new Object[]{};
            obj = appendValue(obj, v3);
            obj = appendValue(obj, v1);
            obj = appendValue(obj, v5);
            obj = appendValue(obj, e);
            obj = appendValue(obj, e1);

            graph = applyPath(graph, obj);*/
        } finally {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    // Appends a value into the Path's Object array
    private Object[] appendValue(Object[] obj, Object newObj) {
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();
    }

    // Paints the path create by the algorithm red
    private mxGraph applyPath(mxGraph graph, Object[] temp) {
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", temp);
        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", temp);
        return graph;
    }

    /*public void start()
    {
        GraphRepresentation frame = new GraphRepresentation(nodes, edges);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }*/
}