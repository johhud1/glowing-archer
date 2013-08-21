import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MatrixZSolution {
    static boolean DEBUG = true;
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
        System.out.println("test parameters are: "+testParameters);
        String[] tParams = testParameters.split("\\s");
        int numJuncts = Integer.parseInt(tParams[0]);
        int numEdges = Integer.parseInt(tParams[1]);
        int timeSteps = Integer.parseInt(tParams[2]);
        System.out.println("numJuncts: "+numJuncts+" numEdges:"+numEdges+" timeSteps:"+timeSteps);
        float[] numZs = new float[numJuncts];
        Matrix initial = new Matrix(numJuncts);
        for(int i=0; i<numEdges; i++){
            String[] edgeStr = in.readLine().split("\\s");
            int start = Integer.valueOf(edgeStr[0]);
            int end = Integer.valueOf(edgeStr[1]);
            if(start < 0 || start > numJuncts-1 || end < 0 || end >numJuncts -1) System.out.println("ERROR: edge junctions ("+start+", "+end+") weren't in range");
            initial.addEdge(start, end);
        }
        for(int i=0; i<numJuncts; i++){
            String[] numZeds = in.readLine().split("\\s");
            Float numz = Float.valueOf(numZeds[0]);
            numZs[i] = numz;
            if(DEBUG){
                System.out.println("numZeds="+numZeds[0]+" numz="+numz+" and numZs["+i+"]"+numZs[i]);
            }

        }

        int timesToExp = (int)(Math.log(timeSteps)/Math.log(2));
        double remainder = timeSteps - Math.pow(2, timesToExp);
        if(DEBUG){
            initial.printMatrix();
            System.out.println("timesToExp="+timesToExp+" remainder="+remainder);
        }
        Matrix mMatrix = new Matrix(initial);
        while(timesToExp > 0){
            mMatrix = mMatrix.multiply(mMatrix);
            mMatrix.printMatrix();
            timesToExp--;
        }
        while(remainder > 0){
            mMatrix = mMatrix.multiply(initial);
            remainder--;
        }
        mMatrix.printMatrix();

    }
}

class Matrix{
    long[][] mat;

    public Matrix(Matrix m){
        mat = m.mat.clone();
    }

    public Matrix(int size){
        mat = new long[size][size];
    }

    public Matrix multiply(Matrix b){
        if(getSize() != b.getSize()){
            System.out.println("error: can't multiply mismatching matrix sizes");
        }
        Matrix res = new Matrix(b.getSize());
        for(int k=0; k<b.getSize(); k++){
            int sum = 0;
            for(int i=0; i<b.getSize(); i++){
                for(int y=0; y<b.getSize(); y++){
                    sum += mat[y][k]*b.mat[i][y];
                }
                res.mat[i][k] = sum;
                sum =0;
                //System.out.println();
                //res.printMatrix();
            }
        }
        return res;
    }

    public int getSize(){
        return mat.length;
    }

    public void addEdge(int x, int y){
        mat[x][y]=1;
        mat[y][x]=1;
    }


    public double getPositoin(int x, int y){
        return mat[x][y];
    }

    public void printMatrix(){
        for(int i=0; i<getSize(); i++){
            StringBuffer sb = new StringBuffer("[");
            for(int k=0; k<getSize(); k++){
                sb.append(mat[k][i]+",");
            }
            sb.append("]");
            System.out.println(sb.toString());
        }
    }

}


