import junit.framework.TestFailure;


public class Test {
    public static void main(String[] args){
        System.out.println("goFunc returns: " + goFunc());
    }
    public static int goFunc(){
        try {
            testFunction(4);
            System.out.println("about to return from try block");
            return 4;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println("in finally block");
            return 5;
        }
    }
    public static int testFunction(int a) throws Exception{
        int b = a;
        if(b < 0){
            throw new Exception("input < 0");
        }
        return a;
    }
}
