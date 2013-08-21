import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DebugGraphics;


public class SolutionZombie {
    static boolean DEBUG = false;
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            System.setIn(new FileInputStream("C:\\Users\\Jack\\workspace\\twitch_challenges\\Zombie-March_testcases\\input00.txt"));
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
        int testcases = Integer.parseInt(sParams);
        //System.out.println("testcases ="+testcases);
        for(int i=0; i<testcases; i++){
            try {
                runTestCase(in);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private static void runTestCase(BufferedReader in) throws IOException{
        String testParameters = in.readLine();
        //System.out.println("test parameters are: "+testParameters);
        String[] tParams = testParameters.split("\\s");
        int numJuncts = Integer.parseInt(tParams[0]);
        int numEdges = Integer.parseInt(tParams[1]);
        int timeSteps = Integer.parseInt(tParams[2]);
        //System.out.println("numJuncts: "+numJuncts+" numEdges:"+numEdges+" timeSteps:"+timeSteps);
        ZombieMap initial = new ZombieMap(numJuncts);
        for(int i=0; i<numEdges; i++){
            String[] edgeStr = in.readLine().split("\\s");
            int start = Integer.valueOf(edgeStr[0]);
            int end = Integer.valueOf(edgeStr[1]);
            if(start < 0 || start > numJuncts-1 || end < 0 || end >numJuncts -1) System.out.println("ERROR: edge junctions ("+start+", "+end+") weren't in range");
            ArrayList<Junction> juncs = initial.mJunctions;
            juncs.get(start).addNeighbor(end);
            juncs.get(end).addNeighbor(start);
            if(DEBUG){
                System.out.println("read line as having ints start: "+start+" and end: "+end);
//                System.out.println("junction:"+start+", neighborset contains(junc="+end+"): "+juncs.get(start).mNeighbors.contains(end));
//                System.out.println("junction:"+end+", neighborset contains(junc="+end+"): "+juncs.get(start).mNeighbors.contains(end));
            }

        }
        for(int i=0; i<numJuncts; i++){
            String[] numZeds = in.readLine().split("\\s");
            Float numz = Float.valueOf(numZeds[0]);
            initial.mJunctions.get(i).mNumZombies = numz;
            if(DEBUG){
                System.out.println("numZeds="+numZeds[0]+" numz="+numz+" and mJunction.get("+i+").mNumZombies="+initial.mJunctions.get(i).mNumZombies);
            }

        }
        ZombieMap curMap = initial;
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<timeSteps; i++){
            if(DEBUG){
                for(int k=0; k<curMap.mJunctions.size(); k++){
                    sb.append(curMap.mJunctions.get(k).mNumZombies+", ");
                }
                System.out.println("zDist TS("+i+"): "+sb.toString());
                sb= new StringBuffer();
            }
            curMap = curMap.computeNextStep();
        }
        Collections.sort(curMap.mJunctions);

        sb = new StringBuffer();
        int k=0;
        for(int i=curMap.mJunctions.size()-1; k<5; i--){
            Float zs = new Float(curMap.mJunctions.get(i).mNumZombies);
            sb.append(Math.round(zs)+" ");
            k++;
        }
        System.out.println(sb);

    }
}

class ZombieMap{
    public ArrayList<Junction> mJunctions;
    public int currentTimeStep;

    public ZombieMap(ArrayList<Junction> junctions, int timeStep){
        mJunctions = new ArrayList<Junction>();
        for(Junction j: junctions){
            Junction newJ = new Junction(j);
            newJ.mNumZombies =0;
            mJunctions.add(newJ);

        }
        currentTimeStep = timeStep;
    }

    public ZombieMap(int num) {
        mJunctions = new ArrayList<Junction>();
        for(int i=0; i<num; i++){
            mJunctions.add(new Junction());
        }
    }

    public ZombieMap computeNextStep(){
        ZombieMap nextStepMap = new ZombieMap(mJunctions, currentTimeStep+1);

        for(int i=0; i<mJunctions.size(); i++){
            mJunctions.get(i).distributeZombies(i, nextStepMap);
        }
        return nextStepMap;
    }
}

class Junction implements Comparable<Junction>{
    static boolean DEBUG = false;
    static int initialSize =100;
    int mNumNeighbors = 0;
    float mNumZombies;
    int[] mNeighbors = new int[initialSize];
    public Junction(Junction j){
        mNeighbors = j.mNeighbors;
        mNumZombies = j.mNumZombies;
        mNumNeighbors = j.mNumNeighbors;

    }

    public Junction() {
        // TODO Auto-generated constructor stub
    }

    public void distributeZombies(int thisJuncNum, ZombieMap nextStepMap) {
        Float numNeighbors = new Float(getNumNeighbors());
        Float numGoing = mNumZombies/numNeighbors;
        if(DEBUG){  System.out.println("mNumZombies="+mNumZombies+" mNumNeighbors="+numNeighbors+" numGoing="+numGoing);}
        for(int i=0; i<this.getNumNeighbors(); i++){
            nextStepMap.mJunctions.get(mNeighbors[i]).mNumZombies += numGoing;
        }

    }

    public int getNumNeighbors(){
        return mNumNeighbors;
    }


    public void addNeighbor(Integer neighbor){
        if(mNumNeighbors >= mNeighbors.length){
            int[] newArray = new int[mNeighbors.length*2];
            for(int i=0; i<mNeighbors.length; i++){
                newArray[i] = mNeighbors[i];
            }
            mNeighbors = newArray;
        }
        mNeighbors[mNumNeighbors] = neighbor;
        mNumNeighbors++;
//        if(mNeighbors.contains(neighbor)){
//           System.out.println("trying to add as neighbor, junction that's already a neighbor :??!! LOOK OUT");
//           return;
//        }
//        mNeighbors.add(neighbor);
//        if(neighbor.mNeighbors.contains(this)){
//            System.out.println("adding reciprocal connection, neighbor already had this junction as neighbor ????! LOOK OUT");
//            return;
//        }
//        neighbor.mNeighbors.add(this);
    }

    @Override
    public int compareTo(Junction o) {
        Float compare = (mNumZombies - o.mNumZombies);
        //System.out.println("compare="+compare+" this.numZombies="+mNumZombies+" o.numZombies="+o.mNumZombies);
        if(compare < 0){
            return -1;
        }
        else if(compare ==0){
            return 0;
        }
        else return 1;
    }
}
