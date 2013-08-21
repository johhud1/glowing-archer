import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;




public class LuckyNumberSol {
    static final int MAX_NUM_LENGTH = 18;
    static final int NUMSEGS = 6;
    final static int SEGMENT_SIZE = MAX_NUM_LENGTH/NUMSEGS;


    private static int[] sums = new int[1000000];
    private static int[] expSums = new int[1000000];


    // 18 digits maximum;

    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Scanner in = new Scanner(System.in);
        int lines = in.nextInt();
        int[][] prob = new int[lines][2];
        boolean[] primes = computePrimes(81 * 18);
        for (int i = 0; i < lines; i++) {
            long start = in.nextLong();
            long end = in.nextLong();
            ArrayList<Long> solutions = findLucky(start, end, primes);
            // for(int k=0; k< solutions.length; k++){
            // System.out.print(solutions[k]+" ");
            // System.out.println();
            // }
            System.out.println(solutions.size());

        }
    }


    // computes all primes up to max, stores result in boolean[max]
    private static boolean[] computePrimes(int max) {
        // System.out.println("computing primes up to:"+max);
        byte[] kP = new byte[max]; // known Primes 0 indicates prime, 1
                                   // not-prime.
        kP[1] = 1;
        int testing = 2;
        while (testing < max) {
            for (int k = testing + testing; k < max; k += testing) {
                // System.out.println("setting "+k+" as not prime");
                kP[k] = 1;
            }
            testing++;
            while (testing < max && kP[testing] == 1) {
                testing++;
            }
        }
        boolean[] primes = new boolean[max];
        for (int i = 0; i < max; i++) {
            if (kP[i] == 0) {
                primes[i] = true;
            }
        }
        return primes;
    }

/*
    private static ArrayList<Long> findLucky(long start, long end, boolean[] primes) {
        System.out.println("finding lucky numbers in range:"+start+" - "+end+" total nmbr to check:"+(end-start));

        ArrayList<Long> sols = new ArrayList<Long>();
        for (long i = start; i < end; i++) {
            int[] segments = segmentize(i);
            int[][] digits = new int[NUMSEGS][6];
            int leading0s = 0;
            int k = 0;
            int seg = 0;
            long temp = i;
            while (temp > 0) {// split the number into 3 segment of length 6
                digits[seg][k] = (int) (temp % 10);
                if (digits[seg][k] == 0) {
                    leading0s++;
                }
                temp = temp / 10;
                k++;
                if (k == SEGMENT_SIZE) { // DEPENDANT ON NUMSEGS
                    k = 0;
                    seg++;
                }
            }
            int sum = 0;
            for (int z = 0; z < NUMSEGS; z++) {
                //System.out.println("segments["+z+"]="+segments[z]);
                if (sums[segments[z]] == 0) {
                    int segsum = 0;
                    for (int m = 0; m < digits[z].length; m++) {
                        //System.out.println("digits[z="+z+"][m="+m+"]="+digits[z][m]);
                        segsum += digits[z][m];
                    }
                    sums[segments[z]] = segsum;
                    sum += segsum;
                    //System.out.println("sum not found in array");
                } else {
                    sum += sums[segments[z]];
                    //System.out.println("found sum in array");
                }
            }
            if (!primes[sum]) {
               // System.out.println("num: "+i+" with sum: "+sum+"is not prime, continueing");
                continue;
            }
            //System.out.println("num: "+i+" with sum: "+sum+" is prime");
            sum = 0;
            for (int z = 0; z < NUMSEGS; z++) {
                if (expSums[segments[z]] == 0) {
                    int segsum = 0;
                    for (int m = 0; m < digits[z].length; m++) {
                        segsum += Math.pow(digits[z][m], 2);
                    }
                    sums[segments[z]] = segsum;
                    sum += segsum;
                    //System.out.println("sum not found in array");
                } else {
                    sum += expSums[segments[z]];
                    //System.out.println("found sum in array");
                }
            }
            if (!primes[sum]) {
                //System.out.println("num: "+i+" with sum: "+sum+"is not prime, continueing");
                continue;
            }
            //System.out.println("num: "+i+" with sum: "+sum+"is prime");
            // they are both primes!
            //System.out.println("found a solution:"+i);
            sols.add(i);

        }
        return sols;
    }

    private static int[] segmentize(long num) {
        //System.out.println("segmentizing num:"+num);
        int[] segs = new int[NUMSEGS];
        for (int i = 0; i < NUMSEGS; i++) {
             // TODO:
                                                                    // HARDCODED
                                                                    // MAGIC
                                                                    // NUMBER
                                                                    // (6) ALSO
                                                                    // DEPENDANT
                                                                    // ON
                                                                    // NUMSEGS
                                                                    // AND MAX
                                                                    // NUMBER
                                                                    // LENGTH
            segs[i] = (int) (num % (Math.pow(10, SEGMENT_SIZE)));
            num = (int) (num/(Math.pow(10, SEGMENT_SIZE)));
            //System.out.println("segment(" + i + "):" + segs[i]);
        }
        return segs;
    }*/
}
