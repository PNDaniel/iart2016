import java.util.ArrayList;

public class Edge {
    private Node source; // Source Node object
    private Node destination; // Destination Node object
    private double distance; // Distance (km) associated to this Edge object
    private double ports; // Cost of the ports (euro) associated to this Edge object

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
}