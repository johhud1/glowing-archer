import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class EvenTreeSolution {
    private static boolean DEBUG = false;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            System.setIn(new FileInputStream("C:\\Users\\Jack\\workspace\\twitch_challenges\\Even-Tree_testcases\\input01.txt"));
        } catch (FileNotFoundException e1) {
            System.out.println("file Not found");
            e1.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String sParams;
        try {
            sParams = in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        String[] tParams = sParams.split("\\s");
        int vertices = Integer.parseInt(tParams[0]);
        int edges = Integer.parseInt(tParams[1]);
        Tree tree = new Tree(vertices);
        String edge;
        try {
            while( (edge = in.readLine())!= null){
                String[] edgeNums = edge.split("\\s");
                int e1 = Integer.parseInt(edgeNums[0]);
                int e2 = Integer.parseInt(edgeNums[1]);
                if(DEBUG){
                    System.out.println("e1="+e1+" e2="+e2);
                }
                tree.addEdge(e1, e2);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int numSplits =0;
        for(Node node : tree.graph){
            if(node == null) continue;
            HashMap<Integer, Integer> neighborCounts = new HashMap<Integer, Integer>();
            for(int neighbor : node.neighbors){
                neighborCounts.put(neighbor, tree.calculateCount(neighbor, node.id));
                if(DEBUG){
                    System.out.println("starting at node:"+node.id+" got count="+neighborCounts.get(neighbor)+" for neighbor:"+neighbor);
                }
            }
            int numEvens =0;
            for(int i: neighborCounts.values()){
                if((i % 2) == 0){
                    numEvens++;
                }
                if(DEBUG){
                    System.out.println("iterating through neighborCounts. count="+i+" numevens="+numEvens);
                }
            }
            if(numEvens != 0 && (numEvens == node.getNumNeighbors()-1)){
                int i=0;
                while(i < node.neighbors.size()){
                    int neighbor = node.neighbors.get(i);
                    if( (neighborCounts.get(neighbor) % 2) == 0){
                        tree.split(node.id, neighbor);
                        numSplits++;
                    }
                    else{
                        i++;
                    }
                    if(DEBUG){
                        System.out.println("iterating through neighborCounts to split. neighbor="+neighbor+" neighborCounts="+neighborCounts.get(neighbor)+" numSplits="+numSplits);
                    }
                }
            }
        }
        System.out.println(numSplits);
    }
}
class Tree{
    Node[] graph;
    public Tree(int numNodes){
        graph = new Node[numNodes+1];
        for(int i=1; i<numNodes+1; i++){
            graph[i] = new Node(i, numNodes);
        }
    }

    public int calculateCount(int id, int cameFrom) {
        int sum =0;
        for(int neighbor: graph[id].neighbors){
            if(neighbor == cameFrom){
                continue;
            }
            sum += this.calculateCount(neighbor, id);
        }
        return sum +=1;
    }

    public void addEdge(int start, int end){
        graph[start].addNeighbor(end);
        graph[end].addNeighbor(start);
    }
    public void split(int start, int end){
        graph[start].removeNeighbor(end);
        graph[end].removeNeighbor(start);
    }
}

class Node{
    ArrayList<Integer> neighbors;
    int id;
    public Node(int id, int numNodes){
        neighbors = new ArrayList<Integer>(numNodes);
        this.id = id;
    }
    public void addNeighbor(int id){
        neighbors.add(new Integer(id));
    }
    public void removeNeighbor(int id){
        neighbors.remove(new Integer(id));
    }
    public int getNumNeighbors(){
        return neighbors.size();
    }
}
