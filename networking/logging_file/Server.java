package networking.logging_file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final int port;
    private final String filePath;

    public Server(int port, String filePath) {
        this.port = port;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        System.out.println("Server starting...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("SERVER: started!");
            System.out.println("SERVER: waiting for connections...");
    
            while (true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println("SERVER: new client");
                    new Worker(socket, filePath).start();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Integer serverPort = 8080;
    
        Server server = new Server(serverPort, "logfile.txt");
        server.start();
    }


}
