import java.util.Date;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.*;

public class GenerateRandomNumbers1 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try {
            Random random = new Random();
            String randomInteger;

            for (int i=0; i<1000000; i++) {
                randomInteger = new Integer(random.nextInt()).toString();
                System.out.println(randomInteger);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println("elapsed time (sec): "+diff/1000);
    }
}
