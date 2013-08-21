import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.*;

public class GenerateRandomNumbers2 {
    public static void main(String[] args) {
        PrintStream out = System.out;
        try {
            System.setOut(new PrintStream(new File("NUL:")));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        long start = System.currentTimeMillis();
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            String randomInteger;

            for (int i=0; i<1000000; i++) {
                randomInteger = new Integer(random.nextInt()).toString();
                System.out.println(randomInteger);
            }
        } catch (NoSuchAlgorithmException e) {
            System.setOut(out);
            System.err.println(e);
        }
        System.setOut(out);
        long diff = System.currentTimeMillis() - start;
        System.out.println("elapsed time (sec): "+diff/1000);
    }
}
