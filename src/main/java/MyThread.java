import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;


public class MyThread implements Runnable {

    /**
     * This map synchronously stores scan results.
     */
    public static final ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    private final String host;
    private final int port;

    MyThread(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method describes the actions of the thread
     * There is a connection to the socket and depending on the time of the received response, we decide whether it is open or closed
     */
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 30);
            socket.close();
            System.out.println(host + ":" + port + " is open ");
            concurrentHashMap.put(host + ":" + port, "open");
        } catch (Exception ex) {
            System.out.println(host + ":" + port + " is closed ");
            concurrentHashMap.put(host + ":" + port, "closed");
        }
    }
}