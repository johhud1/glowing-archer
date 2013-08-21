import java.util.Scanner;


public class CountScorecards {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int cases = in.nextInt();
        while(cases > 0){
            int players = in.nextInt();
            int[] scores = new int[players];
            for(int i=0; i<players; i++){
                scores[i] = in.nextInt();
            }
            System.out.println(solveCountSC(players, scores));
        }
    }

    private static int solveCountSC(int players, int[] scores) {
        int sum=0;
        //each score must be <= players-1, and sum of scores <= (players*(players-1))/2
        for(int i=0; i<scores.length; i++){
            sum+= scores[i];
            if(scores[i]>players-1){
                System.out.println("score greater than players-1: "+(players-1)+" scores["+i+"]="+scores[i]);
                return 0;
            }
        }
        if(sum > (players*(players-1))/2){
            System.out.println("sum of scores not valid ("+sum+")");
            return 0;
        } else if(sum == (players*(players-1))/2){
            System.out.println("scores all determined");
            return 1;
        }

        //at this point, should have more than 1 valid potential scorecards
        //apply integer partition algorithm with largest number=(players-1), sum=(players*(players-1)/2)-(sum of already determined scores)
        return 0;
        // TODO Auto-generated method stub

    }
}
