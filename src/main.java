import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class main {

    public static void main(String[] args){

        try {
            Socket s = new Socket("98.206.142.154", 8080);
            //s.bind(null);
            //s.connect(new InetSocketAddress("98.206.142.154", 8080), 10000);
            s.getOutputStream().write("testing".getBytes());
            System.out.println("done-success");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
