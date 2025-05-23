import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Worker extends Thread {
    Socket socket;
    String logFile;
    static long total = 0; 
    static Lock lock = new ReentrantLock();

    public Worker(Socket socket, String logFile) {
        this.socket = socket;
        this.logFile = logFile;
    }

    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedWriter fileWriter = null;

        try {
            reader = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()
                )
            );
            writer = new BufferedWriter(
                new OutputStreamWriter(
                    socket.getOutputStream()
                )
            );

            fileWriter = new BufferedWriter(
                new FileWriter(
                    logFile, true
                )
            );

            String line = reader.readLine();

            if (line.equals("HANDSHAKE")) {
                writer.write("Logged In " + socket.getRemoteSocketAddress() + "\n");
                writer.flush();

                int localSum = 0;
                StringBuilder stringBuilder = new StringBuilder();
                while(!(line = reader.readLine()).equals("STOP")) {
                    int number = Integer.parseInt(line); 
                    localSum += number;

                    stringBuilder.append(
                        String.format(
                            "[%d] %s %s\n", number, LocalDateTime.now(), socket.getRemoteSocketAddress())
                    );
                }
                fileWriter.write(stringBuilder.toString());
                fileWriter.flush();

                //lock
                lock.lock();
                total += localSum;
                //unlock
                writer.write(total + "\n");
                lock.unlock();

                writer.write("Logged Out\n");
                writer.flush();

                fileWriter.close();
                writer.close();
                reader.close();
                socket.close();

            }
            else {
                writer.write("Could not connect\n");
                writer.flush();
                writer.close();
                fileWriter.close();
                reader.close();
                socket.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
