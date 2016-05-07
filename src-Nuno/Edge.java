import java.util.ArrayList;

/**
 * Created by Nuno Silva on 06/05/2016.
 */

public class Edge {

    private Node source;
    private Node destination;

    // Constructor method for class Edge
    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    // GET METHODS

    public Node getSource() { return this.source; };

    public Node getDestination() { return this.destination; };

    // SET METHODS

    public void setSource(Node source) { this.source = source; }

    public void setDestination(Node destination) { this.destination = destination; }
}