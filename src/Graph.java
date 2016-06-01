import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> nodes; // Node objects that constitute this Graph object
    private ArrayList<Edge> edges; // Edge objects that are a part of this Graph object

    // Constructor method for class Graph
    public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    // GET METHODS
    public ArrayList<Node> getNodes() { return this.nodes; }
    public ArrayList<Edge> getEdges() { return edges; }
    public int getNumNodes() { return this.nodes.size(); }

    // SET METHODS
    public void setNodes(ArrayList<Node> nodes) { this.nodes = nodes; }
    public void setEdges(ArrayList<Edge> edges) { this.edges = edges; }

    // OTHER METHODS

    // Returns an array list of Node objects where Node object is the source of the Edge objects
    public ArrayList<Node> getSuccessors(Node n) {
        ArrayList<Node> node_successors = new ArrayList<Node>();

        for (Edge e: edges) {
            if(e.getSource() == n)
                node_successors.add(e.getDestination());
        }

        return node_successors;
    }

    // Returns the Edge object that links two Node objects
    public Edge getEdge(Node nsource, Node ndestination) {
        for (Edge e: edges) {
            if(e.getSource() == nsource && e.getDestination() == ndestination)
                return e;
        }
        return null;
    }

    // Returns the Node object with the lowest sleep costs in Graph objects with which Node n share an edge with
    public double getLowestSleepCost(Node n){
        double lowest_sleep_cost = Integer.MAX_VALUE;
        ArrayList<Node> node_successors = getSuccessors(n);

        for (Node n1: node_successors) {
            double sleep_costs = n1.getSleep_cost();

            if(sleep_costs < lowest_sleep_cost && sleep_costs != -1)
                lowest_sleep_cost = sleep_costs;
        }

        if(lowest_sleep_cost == Integer.MAX_VALUE)
            return 0;
        else
            return lowest_sleep_cost;
    }

    // Returns the Node objects where the Edge objects between Node objects have a gas station
    public ArrayList<Node> gasStationSuccessors(Node n) {
        ArrayList<Node> viable_successors = new ArrayList<Node>();

        for (Edge e: edges) {
            if(n == e.getSource() && e.getGas_station())
                viable_successors.add(e.getDestination());
        }
        return viable_successors;
    }
}
