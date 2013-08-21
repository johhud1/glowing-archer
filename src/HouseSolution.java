import java.util.HashMap;

import javax.swing.JFrame;


public class HouseSolution {
    private static final boolean DEBUG = true;
    public static void main(String[] args){

        HashMap<Double, Double> data = new HashMap<Double, Double>();
        double ratio = 2;
        double offset = 5;
        generate2CircleSolutions(data, ratio, offset);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HouseSolutionGrapher hsg = new HouseSolutionGrapher();
        hsg.data = data;
        f.add(hsg);
        f.setSize(1000,1000);
        f.setLocation(50,50);
        f.setVisible(true);

    }

    private static void generate2CircleSolutions(HashMap<Double, Double> data, double ratio, double offset){
        double r2 = (offset)/(1.0+ratio);
        if(DEBUG){
            System.out.println("Generate2CircleSolutions(ratio="+ratio+", offset="+offset+" calculated initial r2 = "+r2);
        }
        for(; r2<100; r2+=.1){
//            double x1sqrd = Math.pow(offset, 2);
//            double r2sqrd = Math.pow(r2, 2);
//            double parens = (1.0-Math.pow(ratio, 2));
//            double divisor = 2*offset;
//            System.out.println("x1sqrd="+x1sqrd+" r2sqrd="+r2sqrd+" parens="+parens+" divisor="+divisor+" eq= (x1sqrd-r2sqrd*(parens))/divisor");
            double x = (Math.pow(offset, 2) - Math.pow(r2, 2)*(1.0-Math.pow(ratio, 2)))/(2.0*offset);
            double y = Math.sqrt(Math.pow(r2*ratio, 2) - Math.pow( ((Math.pow(offset, 2) - Math.pow(r2, 2)*(1-Math.pow(ratio, 2) ))/(2.0*offset)), 2));
            if(DEBUG){
                double diff = Math.pow(r2, 2) - (Math.pow(x-offset, 2) + Math.pow(y, 2));
                System.out.println("r2="+r2+" x="+x+" y="+y+" diff between r2^2 and ( (x-x1)^2+y^2) = "+diff);
            }
            data.put(x, y);
        }

    }
}
