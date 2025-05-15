package networking.logging_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worker extends Thread {

    private Socket socket;
    private String logFilePath;

    public Worker(Socket socket, String logFilePath) {
        this.socket = socket;
        this.logFilePath = logFilePath;
    }
    
    public void run() {
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            WebRequest request = WebRequest.of(reader);
            System.out.println("Method: " + request.method + " URL: " + request.url);

            saveLog(socket.getInetAddress().getHostAddress(), request.method, request.url);

            writer.write("HTTP/1.1 200 OK\n");
            writer.write("Content-Type: text/html\n\n");

            writer.write("Hello "+ request.headers.get("User-Agent") + "! <br/>");
            writer.write("You requested: "+request.method + " " + request.url + " by using HTTP version "+request.version+"\n");
            writer.write("\n");
            writer.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }

    public void saveLog(String address, String method, String url) throws IOException {
        BufferedWriter fileBufferedWriter = new BufferedWriter(new FileWriter(logFilePath, true));
        fileBufferedWriter.write(String.format("Address: %s Method: %s Requested URL: %s", address, method, url));
        fileBufferedWriter.newLine();
        fileBufferedWriter.flush();
        fileBufferedWriter.close();
    }
}

class WebRequest {

    final String method;
    final String url;
    final String version;
    final Map<String,String> headers;

    private WebRequest(String method, String url, String version, Map<String, String> headers) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.headers = headers;
    }

    public static WebRequest of(BufferedReader reader) throws IOException {
        List<String> input = new ArrayList<>();
        
        String line;
        while (!(line = reader.readLine()).isEmpty()) {
            input.add(line);
        }
        
        String[] args = input.get(0).split(" ");
        String method = args[0];
        String url = args[1];
        String version = args[2];

        Map<String, String> headers = new HashMap<>();
        for (int i=1; i< input.size(); i++) {
            String[] pair = input.get(i).split(": ");
            headers.put(pair[0], pair[1]);
        }

        return new WebRequest(method, url, version, headers);
    }
    
    
}
