import java.util.ArrayDeque;
import java.util.ArrayList;



public class EdgeFinder {

    public static int MAXSIZE = 100;

    private class Node {
        public Position position;
        public boolean color;
        public boolean visited;
        public ArrayList<Node> neighbors = new ArrayList<Node>();


        public Node(Position p, boolean c, Node[] ns) {
            position = p;
            color = c;
            neighbors.add(ns);
            visited = false;
        }


        public void addNeighbor(Node n) {
            neighbors.add(n);
            n.neighbors.add(this);
        }
    }

    class Position implements Comparable {
        public int x;
        public int y;


        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public boolean equal(Position other) {
            return ((x == other.x) && (y == other.y));
        }


        @Override
        public int compareTo(Object arg0) {
            return 0;
        }
    }


    public static void main() {
        byte[][] unparsedGraph = new byte[MAXSIZE][MAXSIZE];
        unparsedGraph = getInput();
        Node mStartPoint = buildGraphStructure(unparsedGraph, startPos);
        findEdge(mStartPoint);
    }


    private static Position findEdge(Node start) {
        ArrayDeque<Node> q = new ArrayDeque<Node>();
        for (int i = 0; i < 4; i++) {
            q.add(start.neighbors.get(i));
        }
        while (!q.isEmpty()) {
            Node cur = q.pop();
            cur.visited = true;
            Node edge = getEdge(cur);
            if (edge != null) { return edge.position; }
            for (int i = 0; i < 4; i++) {
                if(!cur.neighbors.get(i).visited){
                    q.add(cur.neighbors.get(i));
                }
            }
        }
        return null;
    }


    private static Node getEdge(Node n) {
        if (isEdge(n.position)) {
            return n;
        } else {
            return null;
        }
    }


    private static boolean isEdge(Position p) {
        return ((p.x == 0 || p.x == MAXSIZE) || (p.y == 0 || p.y == MAXSIZE));
    }


    private static byte[][] getInput() {
        // TODO: implement this or something
        return null;
    }


    private Node buildGraphStructure(byte[][] unparsedGraph, Position startPos) {
        Node retnode = null;
        Node[][] nodes = new Node[MAXSIZE][MAXSIZE];
        for (int i = 0; i < MAXSIZE; i++) {
            for (int k = 0; k < MAXSIZE; k++) {
                nodes[i][k] = new Node(new Position(i, k), unparsedGraph[i][k] == 1, null);
                if (nodes[i][k].position.equal(startPos)) {
                    retnode = nodes[i][k];
                }
                if (k != 0) {
                    nodes[i][k].addNeighbor(nodes[i][k - 1]);
                }
                if (i != 0) {
                    nodes[i][k].addNeighbor(nodes[i - 1][k]);
                }
            }
        }
        return retnode;
    }

}
