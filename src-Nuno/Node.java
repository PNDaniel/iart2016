import java.util.ArrayList;
import java.math.*;

/**
 * Created by Nuno Silva on 01/05/2016.
 */
public class Node {

    private String id; // Identification of Node object
    private int pos_x; // X coordinate of Node object
    private int pos_y; // Y coordinate of Node object
    private double cost; // Path cost for Node object

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

    public double getCost() { return this.cost; }

    // SET METHODS


    public void setId(String id) { this.id = id; }

    public void setPos_x(int pos_x) { this.pos_x = pos_x; }

    public void setPos_y(int pos_y) { this.pos_y = pos_y; }

    public void setCost(double cost) { this.cost = cost; }

    // OTHER METHODS

    // Calculates the distance between to Nodes with coordinates (X1, Y1) and (X2, Y2)
    public double getDistance(Node n2) {
        double n1_pos_x = this.getPos_x();
        double n1_pos_y = this.getPos_y();

        double n2_pos_x = n2.getPos_x();
        double n2_pos_y = n2.getPos_y();

        double n_distance_x = n2_pos_x - n1_pos_x;
        double n_distance_y = n2_pos_y - n1_pos_y;

        double distance = Math.sqrt(Math.pow(n_distance_x, 2) + Math.pow(n_distance_y, 2));

        return distance;
    }
}