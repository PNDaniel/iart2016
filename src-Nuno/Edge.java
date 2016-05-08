import java.util.ArrayList;

/**
 * Created by Nuno Silva on 06/05/2016.
 */

public class Edge {

    private Node source;
    private Node destination;
    private double distance;
    private double ports;

    // Constructor method for class Edge
    public Edge(Node source, Node destination, double distance, double ports) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.ports = ports;
    }

    // GET METHODS

    public Node getSource() { return this.source; };

    public Node getDestination() { return this.destination; };

    public double getDistance() { return distance; }

    public double getPorts() { return this.ports; }

    // SET METHODS

    public void setSource(Node source) { this.source = source; }

    public void setDestination(Node destination) { this.destination = destination; }

    public void setDistance(double distance) { this.distance = distance; }

    public void setPorts(double ports) { this.ports = ports; }

    // OTHER METHODS


}