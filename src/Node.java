import java.util.ArrayList;
import java.math.*;

public class Node {

    private boolean solution = false;
    private int numID;
    private String id; // Identification of Node object
    private int pos_x; // X coordinate of Node object
    private int pos_y; // Y coordinate of Node object
    private double g; // G cost for Node object on A* algorithm
    private Node parent; // Parent of this Node object, used to determine path given by A* algorithm
    private boolean mandatory = false; // Boolean value that determines if visit to this Node object is mandatory
    private double stay_cost = -1; // Cost of staying in a Node object. -1 indicates that it isn't possible to stay in this Node object
    private double driving_hours = 8; // Number of hours available for driving
    private double gas_litres = 60; // Number of gas litres available for driving

    // Constructor method for class Node
    public Node(int numID, String id, int pos_x, int pos_y) {
        this.numID = numID;
        this.id = id;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    // GET METHODS
    public boolean getSolution() {
        return this.solution;
    }
    public int getNumID() {
        return numID;
    }
    public String getId() { return id; }
    public int getPos_x() { return this.pos_x; }
    public int getPos_y() { return this.pos_y; }
    public double getG() { return this.g; }
    public boolean getMandatory() { return this.mandatory; }
    public double getSleep_cost() { return this.stay_cost; }
    public Node getParent() { return this.parent; }
    public double getDriving_hours() { return this.driving_hours; }
    public double getGas_litres() { return this.gas_litres; }
    public boolean getSolution() {return this.solution; }

    // SET METHODS
    public void setSolution(boolean solution) {
        this.solution = solution;
    }
    public void setNumID(int numID) {
        this.numID = numID;
    }
    public void setId(String id) { this.id = id; }
    public void setPos_x(int pos_x) { this.pos_x = pos_x; }
    public void setPos_y(int pos_y) { this.pos_y = pos_y; }
    public void setG(double g) { this.g = g; }
    public void setMandatory(boolean mandatory) { this.mandatory = mandatory; }
    public void setSleep_cost(double stay_cost) { this.stay_cost = stay_cost; }
    public void setParent(Node parent) { this.parent = parent; }
    public void setDriving_hours(double driving_hours) { this.driving_hours = driving_hours; }
    public void setGas_litres(double gas_litres) { this.gas_litres = gas_litres; }
    public void setSolution(boolean solution) { this.solution = solution; }

    // OTHER METHODS

    // Returns the calculation of the euclidean distance between two Nodes with coordinates (X1, Y1) and (X2, Y2)
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

    // Returns the calculation of the heuristic for Node object given the Graph g its a part of and the Node object goal
    public double heuristic(Graph g, Node ngoal) {
        double heuristic = this.g + this.getEuclideanDistance(ngoal);

        return heuristic;
    }
}