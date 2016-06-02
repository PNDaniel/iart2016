import java.util.*;
import java.util.List;

public class AStar {

    // Returns the best path given by A* algorithm
    public static ArrayList<Node> getPath(Node nstart, Node ngoal, Graph g) {
        ngoal.setSolution(true);
        Edge e_temp = null;
        Node n_temp = ngoal;
        ArrayList<Node> path = new ArrayList<Node>();
        int count = 0;

        path.add(ngoal);
        while(n_temp.getId() != nstart.getId()) {
            e_temp = g.getEdge(path.get(count).getParent(), n_temp);
            e_temp.setSolution(true);
            n_temp = path.get(count).getParent();
            path.add(n_temp);
            n_temp.setSolution(true);
            count++;
        }
        Collections.reverse(path);
        return path;
    }

    // A* Algorithm - Returns a list of Node objects after the best path has been determined by the A* algorithm
    public static ArrayList<Node> astar(final Graph g, Node nstart, final Node ngoal) {
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
        return getPath(nstart, ngoal, g);
    }

    public static void main(String args[]) {

       /* Node n1 = new Node(0, "n1", 50, 0);
        Node n2 = new Node(1, "n2", 280, 30);
        Node n3 = new Node(2, "n3", 5, 260);
        Node n4 = new Node(3, "n4", 400, 300);
        Node n5 = new Node(4, "n5", 80, 100);
        Node n6 = new Node(5, "n6", 240, 200);

        Edge e1 = new Edge("A1", n1, n2, n1.getEuclideanDistance(n2), 2.00);
        Edge e2 = new Edge("A2", n1, n3, n1.getEuclideanDistance(n3), 2.00);
        Edge e3 = new Edge("A3", n2, n4, n2.getEuclideanDistance(n4), 4.50);
        Edge e4 = new Edge("A4", n4, n6, n4.getEuclideanDistance(n6), 0.50);
        Edge e5 = new Edge("A5", n3, n5, n3.getEuclideanDistance(n5), 1.25);
        Edge e6 = new Edge("A6", n5, n6, n5.getEuclideanDistance(n6), 3.00);
        Edge e7 = new Edge("A7", n5, n6, n5.getEuclideanDistance(n6), 4.00);
        Edge e8 = new Edge("A8", n5, n6, n5.getEuclideanDistance(n6), 1.00);

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
        edges.add(e7);
        edges.add(e8);*/

        Node n1 = new Node(0, "Porto", 0, 0);
        Node n2 = new Node(1, "Lisboa", 250, 5);
        Node n3 = new Node(2, "Atlético de Bilbau", 5, 350);
        Node n4 = new Node(3, "Valência", 750, -50);
        Node n5 = new Node(4, "Paris", 750, 300);
        Node n6 = new Node(5, "Lyon", 900, 200);
        Node n7 = new Node(6, "Zurique", 1000, 250);
        Node n8 = new Node(7, "Roma", 1500, 20);
        Node n9 = new Node(8, "Londres", 250, 650);
        Node n10 = new Node(9, "Bruxelas", 800, 450);
        Node n11 = new Node(10, "Amsterdão", 900, 600);
        Node n12 = new Node(11, "Dortmund", 1000, 500);
        Node n13 = new Node(12, "Berlim", 1300, 800);

        Edge e1 = new Edge("A1", n1, n2, 313, 10.00);
        Edge e2 = new Edge("EN1", n1, n2, 350, 0.00);
        Edge e3 = new Edge("A2", n1, n3, 709, 14.00);
        Edge e4 = new Edge("A3", n2, n3, 863, 18.00);
        Edge e5 = new Edge("A4", n3, n4, 610, 7.00);
        Edge e6 = new Edge("A5", n2, n4, 882, 15.00);
        Edge e7 = new Edge("A6", n4, n5, 1380, 20.00);
        Edge e8 = new Edge("A7", n4, n6, 979, 16.00);
        Edge e9 = new Edge("A8", n5, n6, 462, 11.00);
        Edge e10 = new Edge("A9", n5, n7, 603, 12.00);
        Edge e11 = new Edge("A10", n6, n7, 424, 10.00);
        Edge e12 = new Edge("A11", n7, n8, 851, 14.00);
        Edge e13 = new Edge("A12", n5, n9, 460, 20.00);
        Edge e14 = new Edge("A13", n5, n10, 300, 10.00);
        Edge e15 = new Edge("A14", n8, n10, 1110, 23.00);
        Edge e16 = new Edge("A15", n10, n11, 211, 5.00);
        Edge e17 = new Edge("A16", n10, n12, 278, 6.00);
        Edge e18 = new Edge("A17", n11, n12, 246, 5.50);
        Edge e19 = new Edge("A18", n12, n13, 494, 18.50);
        Edge e20 = new Edge("A19", n11, n13, 656, 17.00);

        n1.setSleep_cost(50);
        n2.setSleep_cost(90);
        n3.setGas_litres(120);
        n4.setSleep_cost(60);
        n5.setSleep_cost(70);
        n6.setSleep_cost(120);
        n7.setSleep_cost(140);
        n8.setSleep_cost(100);
        n9.setSleep_cost(120);
        n10.setSleep_cost(200);
        n11.setSleep_cost(185);
        n12.setSleep_cost(110);
        n13.setSleep_cost(135);

        e1.setGas_station(true);
        e2.setGas_station(false);
        e3.setGas_station(true);
        e4.setGas_station(false);
        e5.setGas_station(true);
        e6.setGas_station(false);
        e7.setGas_station(true);
        e8.setGas_station(false);
        e9.setGas_station(true);
        e10.setGas_station(false);
        e11.setGas_station(true);
        e12.setGas_station(false);
        e13.setGas_station(true);
        e14.setGas_station(false);
        e15.setGas_station(true);
        e16.setGas_station(false);
        e17.setGas_station(true);
        e18.setGas_station(true);
        e19.setGas_station(true);
        e20.setGas_station(true);
        /*Edge e2 = new Edge("A2", n1, n3, n1.getEuclideanDistance(n3), 2.00);
        Edge e3 = new Edge("A3", n2, n4, n2.getEuclideanDistance(n4), 4.50);
        Edge e4 = new Edge("A4", n4, n6, n4.getEuclideanDistance(n6), 0.50);
        Edge e5 = new Edge("A5", n3, n5, n3.getEuclideanDistance(n5), 1.25);
        Edge e6 = new Edge("A6", n5, n6, n5.getEuclideanDistance(n6), 3.00); */

        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);
        nodes.add(n7);
        nodes.add(n8);
        nodes.add(n9);
        nodes.add(n10);
        nodes.add(n11);
        nodes.add(n12);
        nodes.add(n13);

        ArrayList<Edge> edges = new ArrayList<Edge>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);
        edges.add(e11);
        edges.add(e12);
        edges.add(e13);
        edges.add(e14);
        edges.add(e15);
        edges.add(e16);
        edges.add(e17);
        edges.add(e18);
        edges.add(e19);
        edges.add(e20);

        SimpleGraphView sgv = new SimpleGraphView(nodes, edges, false);

        Graph g1 = new Graph(nodes, edges);
        ArrayList<Node> path = astar(g1, nodes.get(Integer.parseInt(args[0])), nodes.get(Integer.parseInt(args[1])));
        //List<Node> path = astar(g1, nodes.get(Integer.parseInt(args[0])), nodes.get(Integer.parseInt(args[1])));

        sgv = new SimpleGraphView(nodes, edges, true);
        for (Node n: path) {
            System.out.println(n.getId());
        }
    }
}