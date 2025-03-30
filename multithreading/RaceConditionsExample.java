package multithreading;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RaceConditionsExample {
    public static void main(String[] args) throws InterruptedException {

        UnsafeSequence sequence =  new UnsafeSequence();
        List<IncrementExecutor> threadList = new ArrayList<>();

        for(int i = 0; i < 150; i++) { 
            IncrementExecutor ie = new IncrementExecutor("Thread" + i, sequence);
            threadList.add(ie);
            ie.start();
            // create threads
            // start threads
        }
        
        for (IncrementExecutor ie: threadList) {
            ie.join();
        }
        // wait for threads to finish

        System.out.println(sequence.getValue());
        
        System.out.println("Main done");

    }

}

class IncrementExecutor extends Thread {
    
    private String name;
    private UnsafeSequence sequence;

    public IncrementExecutor(String name, UnsafeSequence sequence) {
        super(name);
        this.name = name;
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                this.sequence.getNext();
            } catch (InterruptedException e) {
                // Restore the interrupted status
                Thread.currentThread().interrupt();
                // Depending on the logic, you might want to break the loop
                break; 
            }
        }
    }
}

class UnsafeSequence {
    private int value;
    Lock lock;
    Semaphore semaphore;

    UnsafeSequence() {
        lock = new ReentrantLock();
        semaphore = new Semaphore(1);
    }

    public int getNext() throws InterruptedException {
        // Not synchronized, race condition
        semaphore.acquire();
        value++;
        System.out.println("Accessed by thread: " + Thread.currentThread().threadId() + " Current value: " + value);
        semaphore.release();
        return value;
    }

    public int getValue() {
        return value;
    }
}
