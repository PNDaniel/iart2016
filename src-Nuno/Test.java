import javax.xml.soap.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Nuno Silva on 01/05/2016.
 */
public class Test {

    public static HashMap<Node, Node> astar(Graph g, Node nstart, final Node ngoal) {
        int numNodes = g.getNumNodes();
        nstart.setCost(0);
        PriorityQueue<Node> open = new PriorityQueue<Node>(numNodes, new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                double f1 = n1.getCost() + n1.getDistance(ngoal);
                double f2 = n2.getCost() + n2.getDistance(ngoal);

                if(f1 < f2)
                    return -1;
                if(f1 > f2)
                    return 1;

                return 0;
            }
        });
        HashMap<Node, Node> path = new HashMap<Node, Node>();
        HashMap<Node, Double> current_cost = new HashMap<Node, Double>();

        open.add(nstart);
        path.put(nstart, nstart);
        current_cost.put(nstart, 0.0);

        while(! (open.isEmpty())) {
            Node ncurrent = open.poll();

            if(ncurrent == ngoal)
                break;

            ArrayList<Node> ncurrent_successors = g.getSuccessors(ncurrent);
            for (Node n: ncurrent_successors) {
                double new_cost = current_cost.get(ncurrent) + ncurrent.getDistance(n);
                if((! (current_cost.containsKey(n))) || (new_cost < current_cost.get(n)))
                {
                    current_cost.put(n, new_cost);
                    n.setCost(new_cost);
                    open.add(n);
                    path.put(ncurrent, n);
                }
            }
        }

        return path;
    }

    public static void main(String args[]) {

        Node n1 = new Node("n1", 0, 0);
        Node n2 = new Node("n2", 0, 10);
        Node n3 = new Node("n3", 5, 15);
        Node n4 = new Node("n4", 0, 20);
        Node n5 = new Node("n5", 8, 18);
        Node n6 = new Node("n6", 10, 20);

        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n1, n3);
        Edge e3 = new Edge(n2, n4);
        Edge e4 = new Edge(n4, n6);
        Edge e5 = new Edge(n3, n5);
        Edge e6 = new Edge(n5, n6);

        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);

        ArrayList<Edge> edges = new ArrayList<Edge>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);

        Graph g1 = new Graph(nodes, edges);

        HashMap<Node, Node> path = astar(g1, n1, n6);

        Set<Node> keys = path.keySet();  //get all keys
        for(Node n: keys)
        {
            System.out.println(n.getId() + " -->" + path.get(n).getId());
        }
    }
}