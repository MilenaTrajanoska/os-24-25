import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    int port;
    String logFilePath;
    
    public Server(int port, String logFilePath) {
        this.logFilePath = logFilePath;
        this.port = port;
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Socket created");
        while (true) {
            //wait
            System.out.println("Waiting for connection...");
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection");
                new Worker(socket, logFilePath).start();
                System.out.println("Worker started...");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        String filePath = System.getenv("LOG_FILE_PATH");
        if (filePath == null) {
            throw new RuntimeException("Enter env variable");
        }

        Server server = new Server(7391,filePath);
        server.start();
        
    }
}
