
public class testing {

    public static void main(String[] args) {
        TestClass tClass1 = new TestClass();
        TestClass tClass2 = new TestClass();
        tClass1.myInt=1;
        tClass2.myInt=2;
        myFunction(tClass1, tClass2);
        String tClass1String = "tClass1: "+tClass1.toString();
        String tClass2String = "tClass2: "+tClass2.toString();
        System.out.println(tClass1String);
        System.out.println(tClass2String);
        }

    private static void myFunction(TestClass test1, TestClass test2){
        int temp = test1.myInt;
        test1.myInt = test2.myInt;
        test2.myInt = temp;
//        TestClass temp = test2;
//        test2 = test1;
//        test1 = temp;
    }
}

class TestClass{
    public int myInt;
    public Integer myInterger = new Integer(0);

    @Override
    public String toString(){
        String returnVal = myInterger.toString()+" int: "+myInt;
        return returnVal;
    }
}
    