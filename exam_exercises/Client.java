
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {

    String address;
    int port;
    String filePath;
   

    public Client(String address, int port, String filePath) {
        this.address = address;
        this.port = port;
        this.filePath = filePath;
    }

    public void run() {
        try {
            Socket socket = new Socket(InetAddress.getByName(address), port);
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    socket.getOutputStream()
                )
            );
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()
                )
            );
            BufferedReader fileReader = new BufferedReader(
                new FileReader(
                    filePath
                )
            );

            writer.write("HANDSHAKE\n");
            writer.flush();

            String line = reader.readLine();

            if (line.contains("Logged In")) {

                String numberLine;
                while ((numberLine = fileReader.readLine()) != null) {
                    writer.write(numberLine + "\n");
                }
                writer.write("STOP\n");
                writer.flush();

                line = reader.readLine();
                System.out.println("Total is: " + line);
                line = reader.readLine();
                System.out.println(line);

                fileReader.close();
                writer.close();
                reader.close();
                socket.close();
                
            }
            else {
                System.out.println(line);
                fileReader.close();
                reader.close();
                writer.close();
                socket.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //127.0.0.1
    }

    public static void main(String[] args) {
        String numberFilePath = System.getenv("NUMBERS_FILE_PATH");
        if (numberFilePath==null) {
            throw new RuntimeException("Enter env variable");
        }

        // address should be localhost if not running with docker
        Client client = new Client("server", 7391, numberFilePath);
        client.start();
    }
    
}
