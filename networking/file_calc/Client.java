package networking.file_calc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {

    private String address;
    private int port;
    private String filePath;

    public Client(String address, int port, String filePath) {
        this.address = address;
        this.port = port;
        this.filePath = filePath;
    }

    public void run() {
        Socket socket = null;
        BufferedReader fileReader = null;
        BufferedReader socketReader = null;
        BufferedWriter writer = null;
        try {
            socket = new Socket(InetAddress.getByName(this.address), port);
            fileReader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.write("Hello\n");
            writer.flush();

            String line;

            line = socketReader.readLine();

            if (line.equals("Send file")) {

                while ((line = fileReader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.newLine();
                writer.flush();

                line = socketReader.readLine();

                System.out.println(line);
            }
            else {
                System.out.println("Error in messages");
            } 
            
            fileReader.close();
            socketReader.close();
            writer.flush();
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 5005, "networking/file_calc/data/points3.csv");
        client.start();
    }
    
}
