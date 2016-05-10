import java.util.ArrayList;

public class Edge {
    private String id; // Identification of Edge object
    private Node source; // Source Node object
    private Node destination; // Destination Node object
    private double distance; // Distance (km) associated to this Edge object
    private double ports; // Cost of the ports (euro) associated to this Edge object
    private boolean gas_station = false; // Determines if Edge object has a gas station or not

    // Constructor method for class Edge
    public Edge(String id, Node source, Node destination, double distance, double ports) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.ports = ports;
    }

    // GET METHODS
    public String getId() { return this.id; }
    public Node getSource() { return this.source; }
    public Node getDestination() { return this.destination; }
    public double getDistance() { return distance; }
    public double getPorts() { return this.ports; }
    public boolean getGas_station() { return this.gas_station; }

    // SET METHODS
    public void setId(String id) { this.id = id; }
    public void setSource(Node source) { this.source = source; }
    public void setDestination(Node destination) { this.destination = destination; }
    public void setDistance(double distance) { this.distance = distance; }
    public void setPorts(double ports) { this.ports = ports; }
    public void setGas_station(boolean gas_station) { this.gas_station = gas_station;}
}