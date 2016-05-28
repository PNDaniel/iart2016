import java.util.*;
import java.util.List;

public class AStar {

    // Returns the best path given by A* algorithm
    public static List<Node> getPath(Node nstart, Node ngoal) {
        Node n_temp = ngoal;
        List<Node> path = new ArrayList<Node>();
        int count = 0;

        path.add(ngoal);
        while(n_temp.getId() != nstart.getId()) {
            n_temp = path.get(count).getParent();
            path.add(n_temp);
            count++;
        }
        Collections.reverse(path);
        return path;
    }

    // A* Algorithm - Returns a list of Node objects after the best path has been determined by the A* algorithm
    public static List<Node> astar(final Graph g, Node nstart, final Node ngoal) {
        int numNodes = g.getNumNodes();
        nstart.setG(0);
        PriorityQueue<Node> open = new PriorityQueue<Node>(numNodes, new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                double f1 = n1.heuristic(g, ngoal);
                double f2 = n2.heuristic(g, ngoal);

                if(f1 < f2)
                    return -1;
                if(f1 > f2)
                    return 1;

                return 0;
            }
        });
        LinkedHashMap<Node, Double> current_g = new LinkedHashMap<Node, Double>();

        open.add(nstart);
        nstart.setParent(nstart);
        current_g.put(nstart, 0.0);

        while(! (open.isEmpty())) {
            Node ncurrent = open.poll();

            if(ncurrent == ngoal)
                break;

            if(ncurrent.getParent().getDriving_hours() <= 3 && ncurrent.getSleep_cost() != -1)
            {
                System.out.println("Rest in " + ncurrent.getId() + " for 8 hours.");
                ncurrent.setDriving_hours(8);
            }

            Edge ep = g.getEdge(ncurrent.getParent(), ncurrent);

            if(ncurrent.getParent().getGas_litres() <= 20 && ep.getGas_station())
            {
                System.out.println("Filled up gas in " + ep.getId() + " for 60 litres.");
                ncurrent.setGas_litres(60);
            }


            ArrayList<Node> ncurrent_successors = g.getSuccessors(ncurrent);
            for (Node n: ncurrent_successors) {
                double new_g;
                Edge e = g.getEdge(ncurrent, n);
                double ports_cost = e.getPorts() / 0.1;
                double sleep_cost = n.getSleep_cost() / 5;
                double driving_hours = ncurrent.getDriving_hours();
                double gas_litres = ncurrent.getGas_litres();

                if(driving_hours <= 3 && sleep_cost == -0.2)
                    new_g = 50 + current_g.get(ncurrent) + e.getDistance() + ports_cost;
                else
                    new_g = current_g.get(ncurrent) + e.getDistance() + ports_cost + sleep_cost;

                if(gas_litres <= 20) {
                    if(e.getGas_station())
                        new_g = current_g.get(ncurrent) + e.getDistance() + ports_cost;
                    else
                        new_g = 50 + current_g.get(ncurrent) + e.getDistance() + ports_cost;
                }

                if(n.getMandatory())
                    new_g = 0;

                if(! current_g.containsKey(n) || new_g < current_g.get(n))
                {
                    n.setDriving_hours(driving_hours - (e.getDistance() / 100));
                    n.setGas_litres(((e.getDistance() * 8) / 100));
                    current_g.put(n, new_g);
                    n.setG(new_g);
                    open.add(n);
                    n.setParent(ncurrent);
                }
            }
        }
        return getPath(nstart, ngoal);
    }

    public static void main(String args[]) {

        Node n1 = new Node("n1", 0, 0);
        Node n2 = new Node("n2", 0, 10);
        Node n3 = new Node("n3", 5, 15);
        Node n4 = new Node("n4", 0, 20);
        Node n5 = new Node("n5", 8, 18);
        Node n6 = new Node("n6", 10, 20);

        Edge e1 = new Edge("A1", n1, n2, n1.getEuclideanDistance(n2), 2.00);
        Edge e2 = new Edge("A2", n1, n3, n1.getEuclideanDistance(n3), 2.00);
        Edge e3 = new Edge("A3", n2, n4, n2.getEuclideanDistance(n4), 4.50);
        Edge e4 = new Edge("A4", n4, n6, n4.getEuclideanDistance(n6), 0.50);
        Edge e5 = new Edge("A5", n3, n5, n3.getEuclideanDistance(n5), 1.25);
        Edge e6 = new Edge("A6", n5, n6, n5.getEuclideanDistance(n6), 3.00);

        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(n1);
        nodes.add(n3);
        nodes.add(n2);
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

        WeightedGraph wg = new WeightedGraph(nodes, edges);

        Graph g1 = new Graph(nodes, edges);

        List<Node> path = astar(g1, n1, n6);

        for (Node n: path) {
            System.out.println(n.getId());
        }
    }
}