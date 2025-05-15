package networking.file_calc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private final int port;
    private final String aggregationFile;
    private final String counterFile;

    public Server(int port, String aggregationFile, String counterFile) {
        this.port = port;
        this.aggregationFile = aggregationFile;
        this.counterFile = counterFile;
    }

    public void run() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Waiting for connection...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection");
                new Worker(socket, aggregationFile, counterFile).start();

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server(5005, "aggregation.txt", "counter.bin");
        server.start();
    }
    
}
