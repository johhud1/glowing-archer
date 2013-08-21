

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;




public class CatvsDogSolution {
    private static class Graph {
        short[][] capacity; // capacity graph. capacity[u][v]=capacity from node
                            // u to v
                            // capacity == -1 indicates they aren't neighbors

        // store all votes in this hashmap, with key strings. RC1 (meaning vote
        // remove C1) or KD2 (meaning keep d2)
        // check for edges when adding nodes by making the string key that would
        // conflict (if additional node has KC1, make RC1)
        // and add edges between every element of conflictVotes.get("RC1") and
        // the new node.
        HashMap<String, ArrayList<Node>> conflictVotes = new HashMap<String, ArrayList<Node>>();
        Node[] graph;


        public Graph(int cats, int dogs, int voters) {
            capacity = new short[voters][voters];
            for (int i = 0; i < voters; i++) {
                for (int k = 0; k < voters; k++) {
                    capacity[i][k] = -1;
                }
            }
            graph = new Node[voters];
        }


        public void addNode(Node n) {
            graph[n.ID] = n;
            String kString = "K" + n.keepString;
            String rString = "R" + n.removeString;
            ArrayList<Node> cNodes = conflictVotes.get(kString);
            if (cNodes != null) {// if the arraylist is there, add this node to
                                 // the set of votes
                cNodes.add(n);
            } else {// if their are no nodes for this string, add the arraylist,
                    // with its first element
                cNodes = new ArrayList<Node>();
                cNodes.add(n);
                conflictVotes.put(kString, cNodes);
            }
            cNodes = conflictVotes.get(rString);
            if (cNodes != null) {
                cNodes.add(n);
            } else {
                cNodes = new ArrayList<Node>();
                cNodes.add(n);
                conflictVotes.put(rString, cNodes);
            }
            // finds conflicts
            Integer[] neighbors = findConflicts(n);
            // adds conflicting votes as neighbors and capacity
            addNeighborCapacity(n.ID, neighbors);

        }


        public Integer[] findConflicts(Node n) {
            String conflictString1 = "K" + n.removeString;
            String conflictString2 = "R" + n.keepString;
            ArrayList<Node> res = conflictVotes.get(conflictString1);
            ArrayList<Node> res2 = conflictVotes.get(conflictString2);
            if (res != null && res2 != null) {
                Set<Integer> resSet = new HashSet<Integer>();
                int i;
                for (i = 0; i < res.size(); i++) {
                    resSet.add(res.get(i).ID);
                }
                for (int k = 0; k < res2.size(); k++) {
                    resSet.add(res2.get(k).ID);
                }
                return resSet.toArray(new Integer[resSet.size()]);
            } else if (res != null) {
                Set<Integer> resSet = new HashSet<Integer>();
                for (int i = 0; i < res.size(); i++) {
                    resSet.add(res.get(i).ID);
                }
                return resSet.toArray(new Integer[resSet.size()]);
            } else if (res2 != null) {
                Set<Integer> resSet = new HashSet<Integer>();
                for (int i = 0; i < res2.size(); i++) {
                    resSet.add(res2.get(i).ID);// ires[i] = res2.get(i).ID;
                }
                return resSet.toArray(new Integer[resSet.size()]);
            }
            return new Integer[0];
        }


        private void addNeighborCapacity(int k, Integer[] neighbors) {
            if (neighbors != null) {
                for (int i = 0; i < neighbors.length; i++) {
                    capacity[k][neighbors[i]] = 1;
                    capacity[neighbors[i]][k] = 1;
                }
            }
        }


        public void connectAll(Node source, ArrayList<Node> nodes) {
            graph[source.ID] = source;
            for (int i = 0; i < nodes.size(); i++) {
                capacity[source.ID][nodes.get(i).ID] = 1;
                capacity[nodes.get(i).ID][source.ID] = 1;
            }

        }
    }

    private static class Node {
        int ID;
        String keepString;
        String removeString;

        int prevId = -1;


        public Node(int id) {
            ID = id;
        }


        public Node(int id, String kString, String rString) {
            ID = id;
            keepString = kString;
            removeString = rString;
        }
    }


    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);
        int testCases = in.nextInt();
        for (int i = 0; i < testCases; i++) {
            int cats = in.nextInt();
            int dogs = in.nextInt();
            int voters = in.nextInt();
            Graph graph = new Graph(cats, dogs, voters + 2); // need 2 extra
                                                             // nodes for source
                                                             // and sink for max
                                                             // flow alg.
            ArrayList<Node> catVoters = new ArrayList<Node>();
            ArrayList<Node> dogVoters = new ArrayList<Node>();
            for (int k = 0; k < voters; k++) {
                String voterKeep = in.next();
                String voterRemove = in.next();
                //System.out.println("voter: " + k + " voted keep: " + voterKeep + " remove: "
                //                   + voterRemove);
                Node n = new Node(k, voterKeep, voterRemove);
                if (voterKeep.charAt(0) == 'C') {
                    catVoters.add(n);
                } else {
                    dogVoters.add(n);
                }
                graph.addNode(n);
            }
            // at this point bipartite graph should be constructed
            // add source and sink nodes/edges and prep to find maximal matching
            Node source = new Node(voters);
            Node sink = new Node(voters + 1);
            graph.connectAll(source, catVoters);
            graph.connectAll(sink, dogVoters);
            boolean success = findAugmentAdjustFlow(graph, source, sink);
            while (success) {
                success = findAugmentAdjustFlow(graph, source, sink);
            }
            int numMatched = 0;
            success = removeAugPath(graph, sink);
            while(success){
                numMatched ++;
                success = removeAugPath(graph, sink);
            }
            System.out.println(voters - numMatched);
        }
    }


    public static boolean findAugmentAdjustFlow(Graph graph, Node source, Node sink) {
        //System.out.println("findAugment: starting at: " + source.ID + " target: " + sink.ID);
        int sinkId = sink.ID;
        Queue<Integer> toExplore = new ArrayDeque<Integer>();
        HashMap<Integer, Node> explored = new HashMap<Integer, Node>();
        toExplore.add(source.ID);
        int prevId = source.ID;
        while (!toExplore.isEmpty()) {
            int cur = toExplore.remove();
            //System.out.println("inspecting: " + cur);
            if (cur == sinkId) {
                // we got to the sink! return true
                //System.out.println("this is the target!: " + sink.ID);
                Node curNode = graph.graph[cur];
                while (curNode.prevId != -1) {
                    //System.out.println("prev Id: " + curNode.prevId);
                    graph.capacity[curNode.prevId][curNode.ID] -= 1;
                    graph.capacity[curNode.ID][curNode.prevId] += 1;
                    curNode = explored.get(curNode.prevId);
                }
                return true;
            }
            if (explored.containsKey(cur)) {
                //System.out.println("already explored this: " + cur);
                continue;// skip nodes we've already visited
            }
            // if we haven't explored this node, set prevId, add to explored and
            // push neighbors onto queue
            //System.out.println("curently exploring: " + cur);
            // graph.graph[cur].prevId = prevId;
            explored.put(cur, graph.graph[cur]);
            for (int i = 0; i < graph.capacity[cur].length; i++) {
                if (graph.capacity[cur][i] > 0 && !explored.containsKey(i)) {// add
                                                                             // neighbors
                                                                             // with
                                                                             // positive
                                                                             // capacity
                                                                             // on
                                                                             // the
                                                                             // edge
                    //System.out.println("adding positive capacity neighbor: " + i
                    //                   + " and setting prevID: " + cur);
                    toExplore.add(i);
                    graph.graph[i].prevId = cur;
                }
            }
        }
        //System.out.println("no paths found");
        return false; // no paths found
    }


    public static boolean removeAugPath(Graph g, Node sink) { // returns list of node IDs
                                                     // in augmenting path, and
                                                     // modifies graph so that
                                                     // path won't be found by
                                                     // this function in the
                                                     // future. returns null if
                                                     // there are no augmenting
                                                     // flow paths
       // int[] path = new int[4]; // paths should never be longer than 3
       // path[0] = sink.ID;
        int curID = sink.ID;
        for (int k=1; k < 4; k++) {
            boolean c = false;
            for (int i = 0; i < g.capacity[curID].length; i++) {
                if (g.capacity[curID][i] == 2) {
                    //System.out.println("found edge with cap==2 (k="+k+")"+", " + curID + " - " + i);
                  //  path[k] = i;
                    g.capacity[curID][i] = 1;
                    g.capacity[i][curID] = 1;
                    curID = i;
                    c = true;
                    break;
                }
            }
            if (c == false) {
                //System.out.println("didn't find path for edge number: "+k+ " returning false");
                return false; }
        }
        return true;
    }


    public static void findMaxMatching(Graph g) {

    }

/*
    @Test
    public void catVsDogSol2Test() {
        Node n = new Node(0, "C1", "D1");
        Node n1 = new Node(1, "D1", "C1");
        Graph g = new Graph(2, 2, 3);
        g.addNode(n1);
        assertEquals(-1, g.capacity[n.ID][n1.ID]);
        assertEquals(-1, g.capacity[1][0]);
        g.addNode(n);
        Integer[] conflictIds = g.findConflicts(n1);
        assertEquals(1, conflictIds.length);
        g.addNeighborCapacity(n1.ID, conflictIds);
        assertEquals(1, g.capacity[n.ID][n1.ID]);
        assertEquals(1, g.capacity[n1.ID][n.ID]);
        assertEquals(1, g.capacity[0][1]);
        assertEquals(-1, g.capacity[n.ID][n.ID]);
        Node n2 = new Node(2, "C2", "D2");
        g.addNode(n2);
        assertEquals(1, g.findConflicts(n1).length);
        assertEquals(0, g.findConflicts(n2).length);
        assertEquals(-1, g.capacity[n2.ID][n1.ID]);

    }


    @Test
    public void augmentPathTest() {
        Node n0 = new Node(0, "C1", "D1");
        Node n1 = new Node(1, "C1", "D1");
        Node n2 = new Node(2, "C2", "D2");
        Node n3 = new Node(3, "D2", "C1");
        ArrayList<Node> cats = new ArrayList<Node>();
        cats.add(n0);
        cats.add(n1);
        cats.add(n2);
        ArrayList<Node> dogs = new ArrayList<Node>();
        dogs.add(n3);
        Graph g = new Graph(2, 2, 6);
        g.addNode(n0);
        g.addNode(n3);
        g.addNode(n2);
        g.addNode(n1);
        Node source = new Node(4);
        Node sink = new Node(5);
        g.connectAll(source, cats);
        g.connectAll(sink, dogs);
        boolean success = findAugmentAdjustFlow(g, source, sink);
        while (success) {
            success = findAugmentAdjustFlow(g, source, sink);
        }
        boolean findPath= removeAugPath(g, sink);
        assertEquals(true, findPath);
        findPath = removeAugPath(g, sink);
        assertEquals(false, findPath);
    }*/
}
