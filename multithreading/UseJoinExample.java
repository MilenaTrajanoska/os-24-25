package multithreading;

import java.util.ArrayList;
import java.util.List;

public class UseJoinExample {
    public static void main(String[] args) throws InterruptedException {
        List<Count> countThreads = new ArrayList<>();

        for (int i=0; i < 10; i++) {
            Count c = new Count();
            countThreads.add(c);
            c.start();
        }
        
        // main waits for c
        for(Count c: countThreads) {
            c.join();
        }

        for (Count c: countThreads) {
            System.out.println("Result from thread: " + c.threadId() + " " + c.getResult());
        }

        System.out.println("Main done");
    }
}

class Count extends Thread {
    private long result = 0;

    public void run() {
        count();
    }

    public long getResult() {
        return result;
    }

    public void count() {
        long tmp = 0;
        for (tmp = 0; tmp < 10000000; tmp++) {
            result = tmp;
        }
    }
}
