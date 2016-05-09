import java.util.ArrayList;
import java.math.*;

public class Node {
    private String id; // Identification of Node object
    private int pos_x; // X coordinate of Node object
    private int pos_y; // Y coordinate of Node object
    private double g; // G cost for Node object
    private boolean mandatory = false; // Boolean value that determines if visit to this Node object is mandatory

    // Constructor method for class Node
    public Node(String id, int pos_x, int pos_y) {
        this.id = id;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    // GET METHODS
    public String getId() { return id; }
    public int getPos_x() { return this.pos_x; }
    public int getPos_y() { return this.pos_y; }
    public double getG() { return this.g; }
    public boolean getMandatory() { return this.mandatory; }

    // SET METHODS
    public void setId(String id) { this.id = id; }
    public void setPos_x(int pos_x) { this.pos_x = pos_x; }
    public void setPos_y(int pos_y) { this.pos_y = pos_y; }
    public void setG(double g) { this.g = g; }
    public void setMandatory(boolean mandatory) { this.mandatory = mandatory; }

    // OTHER METHODS

    // Calculates the euclidean distance between to Nodes with coordinates (X1, Y1) and (X2, Y2)
    public double getEuclideanDistance(Node n2) {
        double n1_pos_x = this.getPos_x();
        double n1_pos_y = this.getPos_y();

        double n2_pos_x = n2.getPos_x();
        double n2_pos_y = n2.getPos_y();

        double n_distance_x = n2_pos_x - n1_pos_x;
        double n_distance_y = n2_pos_y - n1_pos_y;

        double distance = Math.sqrt(Math.pow(n_distance_x, 2) + Math.pow(n_distance_y, 2));

        return distance;
    }

    // Calculates the heuristic for Node object given the Graph g its a part of and the Node object goal
    public double heuristic(Graph g, Node ngoal) {
        double heuristic = this.g + this.getEuclideanDistance(ngoal) + g.getLowestPorts(this);

        return heuristic;
    }
}