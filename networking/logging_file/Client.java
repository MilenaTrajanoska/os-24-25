package networking.logging_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    private int serverPort;
    private String serverName; 
    
    Client(int serverPort, String serverName) {
        this.serverPort = serverPort;
        this.serverName = serverName;
    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader socketReader = null;
        BufferedWriter socketWriter = null;

        try {
           socket = new Socket(InetAddress.getByName(this.serverName), this.serverPort);
           socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            

           socketWriter.write("GET / HTTP/1.1\n");
           socketWriter.write("Host: developer.mozilla.org\n");
           socketWriter.write("User-Agent: OSClient\n");
           socketWriter.write("\n");
           socketWriter.flush();

           String line;
           while (!(line = socketReader.readLine()).isEmpty()) {
            System.out.println("Client received: " + line);
           }

           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (socketReader != null) {
                try {
                    socketReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (socketWriter != null) {
                try {
                    socketWriter.flush();
                    socketWriter.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        String serverName = "localhost";
        Integer serverPort = 8080;

        Client client = new Client(serverPort, serverName);
        client.start();
    }

    
}
