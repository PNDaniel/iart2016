import javax.management.MXBean;
import javax.swing.JFrame;
import javax.swing.text.html.ObjectView;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import jdk.nashorn.internal.ir.LiteralNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * http://stackoverflow.com/questions/6162618/java-graph-library-for-dynamic-visualisation
 * https://jgraph.github.io/mxgraph/docs/manual_javavis.html
 * Customize Graph:
 * http://stackoverflow.com/questions/4051765/customizing-jgraphx
 * https://www.vainolo.com/2011/04/11/another-day-with-jgraph-styles-and-constrained-children/
 * http://www.vainolo.com/wp-content/uploads/2011/05/Capture.png
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
            ArrayList<mxCell> cell = new ArrayList<mxCell>();
            Object[] obj = new Object[]{};
            // pode-se tentar resolver com setValue, metendo um valor no objecto e igualando o valor do objecto as edges?
            // criar o graph.insertVertex directamente num mxCell
            for (int i = 0; i < nodes.size(); i++) {
                Object v = graph.insertVertex(parent, null, nodes.get(i).getId(), nodes.get(i).getPos_x(), nodes.get(i).getPos_y(), 80, 30);
                graph.insertVertex(parent, null, nodes.get(i).getId(), nodes.get(i).getPos_x(), nodes.get(i).getPos_y(), 80, 30);
                obj = appendValue(obj, v);



            /*    mxCell cell1 = new mxCell();
                //cell1.setValue(graph.insertVertex(parent, null, nodes.get(i).getId(), nodes.get(i).getPos_x(), nodes.get(i).getPos_y(), 80, 30));
                //cell.add(cell1);
                Object cenas = graph.insertVertex(parent, null, nodes.get(i).getId(), nodes.get(i).getPos_x(), nodes.get(i).getPos_y(), 80, 30);

                cell1.setValue(cenas);
                System.out.println("King " + cell1.getValue());
                cell.add(cell1);*/
            }
            for (int i = 0; i < edges.size(); i++) {

                Object v1 = edges.get(i).getSource();
                Object v2 = edges.get(i).getDestination();
                Object v3 = edges.get(i).getDistance();

                //graph.insertEdge(parent, null, "3.0",new mxCell(v1),new mxCell(v2));

                graph.insertEdge(parent, null, edges.get(i).getDistance(), obj[i], obj[1]);

/*                graph.insertEdge(parent, null, edges.get(i).getDistance(), new mxCell(edges.get(i).getSource()).getParent(), new mxCell(edges.get(i).getDestination()).getChildAt(0));

                System.out.println("Source " + edges.get(i).getSource());
                System.out.println("Destination " + edges.get(i).getDestination());

                mxCell cell1 = new mxCell();
                mxCell cell2 = new mxCell();
                cell1.setValue(edges.get(i).getSource());
                cell2.setValue(edges.get(i).getDestination());*/

                //graph.insertEdge(parent, null, edges.get(i).getDistance(), cell1.getValue(), cell2.getValue());

                //graph.insertEdge(parent, null, edges.get(i).getDistance(), cell.get(i).getValue(), cell.get(i).getValue());
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