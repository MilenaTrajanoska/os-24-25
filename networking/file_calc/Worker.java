package networking.file_calc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
    private Socket socket;
    private String aggregationFile;
    private String counterFile;
    Lock fileLock;
    public Worker(Socket socket, String aggregationFile, String counterFile) {
        this.socket = socket;
        this.aggregationFile = aggregationFile;
        this.counterFile = counterFile; // total lines coutnedf
        fileLock = new ReentrantLock();
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(
                socket.getOutputStream()
            )
        );
            BufferedWriter aggregateWriter = new BufferedWriter(
                new FileWriter(
                    aggregationFile, true
                )
            );
            RandomAccessFile countRandomAccessFile = new RandomAccessFile(new File(counterFile), "rw");
            System.out.println("here");
            String line = reader.readLine();

            System.out.println(line);
            
            if (!line.equals("Hello")) {
                writer.write("Close\n");
                writer.flush();
                writer.close();
                reader.close();
                aggregateWriter.close();
                countRandomAccessFile.close();
                return;
            }

            writer.write("Send file\n");
            writer.flush();

            long totalPoints = 0;
            long totalLines = 0;

            reader.readLine();
            while(!(line = reader.readLine()).isEmpty()) {
                totalPoints += Integer.parseInt(line.split(",")[3]);
                totalLines++;
            }

            aggregateWriter.write(String.format("Client %s, Total points: %s\n", socket.getInetAddress().getHostAddress(), totalPoints));

            fileLock.lock();
            long lineCount = 0;
            try {
                lineCount = countRandomAccessFile.readLong(); // fileCurrentClientCounter would be 0 if no int
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            countRandomAccessFile.seek(0); // set to the first character in the file
            countRandomAccessFile.writeLong(lineCount + totalLines); // write the new value
            countRandomAccessFile.close();
            fileLock.unlock();

            writer.write("Total points:" + totalPoints);
            writer.newLine();

            writer.flush();
            writer.close();

            reader.close();
            aggregateWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
