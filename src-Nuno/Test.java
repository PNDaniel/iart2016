import javax.xml.soap.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Nuno Silva on 01/05/2016.
 */
public class Test {

    public static LinkedHashMap<Node, Node> astar(final Graph g, Node nstart, final Node ngoal) {
        int numNodes = g.getNumNodes();
        nstart.setG(0);
        PriorityQueue<Node> open = new PriorityQueue<Node>(numNodes, new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                double f1 = n1.getG() + (n1.getEuclideanDistance(ngoal) + g.getLowestPorts(n1));
                n1.f = f1;
                double f2 = n2.getG() + (n2.getEuclideanDistance(ngoal) + g.getLowestPorts(n2));
                n2.f = f2;

                if(f1 < f2)
                    return -1;
                if(f1 > f2)
                    return 1;

                return 0;
            }
        });
        LinkedHashMap<Node, Node> path = new LinkedHashMap<Node, Node>();
        LinkedHashMap<Node, Double> current_g = new LinkedHashMap<Node, Double>();

        open.add(nstart);
        path.put(nstart, nstart);
        current_g.put(nstart, 0.0);

        while(! (open.isEmpty())) {
            Node ncurrent = open.poll();

            if(ncurrent == ngoal)
                break;

            ArrayList<Node> ncurrent_successors = g.getSuccessors(ncurrent);
            for (Node n: ncurrent_successors) {
                Edge e = g.getEdge(ncurrent, n);
                double ports_cost = e.getPorts() / 0.5;
                double new_g = current_g.get(ncurrent) + e.getDistance() + ports_cost;
                if(! current_g.containsKey(n) || new_g < current_g.get(n))
                {
                    current_g.put(n, new_g);
                    n.setG(new_g);
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

        Edge e1 = new Edge(n1, n2, n1.getEuclideanDistance(n2), 2.00);
        Edge e2 = new Edge(n1, n3, n1.getEuclideanDistance(n3), 2.00);
        Edge e3 = new Edge(n2, n4, n2.getEuclideanDistance(n4), 4.50);
        Edge e4 = new Edge(n4, n6, n4.getEuclideanDistance(n6), 0.50);
        Edge e5 = new Edge(n3, n5, n3.getEuclideanDistance(n5), 1.25);
        Edge e6 = new Edge(n5, n6, n5.getEuclideanDistance(n6), 3.00);

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

        LinkedHashMap<Node, Node> path = astar(g1, n1, n6);

        Set<Node> keys = path.keySet();
        for(Node n: keys)
        {
            System.out.println(n.getId() + " -->" + path.get(n).getId());
        }
    }
}