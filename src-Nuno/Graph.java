import java.util.ArrayList;

/**
 * Created by Nuno Silva on 03/05/2016.
 */
public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    // Constructor method for class Graph
    public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    // GET METHODS

    public ArrayList<Node> getNodes() { return this.nodes; }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    // SET METHODS

    public void setNodes(ArrayList<Node> nodes) { this.nodes = nodes; }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    // OTHER METHODS

    //Get number of Node objects in array list nodes
    public int getNumNodes() {
        return this.nodes.size();
    }

    //Returns an array list of Node objects where Node object is the source of the Edge objects
    public ArrayList<Node> getSuccessors(Node n) {
        ArrayList<Node> node_successors = new ArrayList<Node>();

        for (Edge e: edges) {
            if(e.getSource() == n)
                node_successors.add(e.getDestination());
        }

        return node_successors;
    }
}
