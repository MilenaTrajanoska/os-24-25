import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ExecutionCounter {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = null;
        Integer counter = 0;
        try {
            raf = new RandomAccessFile("./data/raf.out","rw");
            counter = raf.readInt();
            counter++;
            System.out.println(counter);
            raf.seek(0);
            raf.writeInt(counter);
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            counter = 1;
            System.out.println(counter);
            raf.writeInt(counter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
