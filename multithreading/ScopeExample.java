package multithreading;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class IntegerWrapper {
    private int value = 0;
    public void increment() { this.value++;}
    public int getVal() { return value;}
}

class ExampleThread extends Thread {
    // would get a reference to scome object which becomes shared
    private IntegerWrapper wrapper;  // can be shared

    // visible by this thread only and is not shared
    // no need for protection
    private int threadLocalField = 0;

    static Semaphore semaphore = new Semaphore(1);

    // can be access from other threads and should be protected when used
    public int threadPublishedField = 0;  // can be shared

    public ExampleThread(int init, IntegerWrapper iw) {
        // init is a primitive variable and thus it is not shared
        threadLocalField = init;
        // this object can be shared since iw is a reference
        wrapper = iw;
    }

    public void run() {
        privateFieldIncrement();
        publicFieldIncrement();

        try {
            wrapperIncrement();    
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void forceSwitch(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) { /* DO NOTHING */}
    }

    private void privateFieldIncrement() {
        // only this thread can access this field
        threadLocalField++;
        // this variable is only visible in this method (not shared)
        int localVar = threadLocalField;
        forceSwitch(3);
        // check for race condition, will it ever occur?
        if (localVar != threadLocalField) {
            System.err.printf("private-mismatch-%d\n", threadId());
        } else {
            System.out.printf("[private-%d] %d\n", threadId(), threadLocalField);
        }
    }

    // For the public field, it's enough to use the monitor of the object, or a lock
    public synchronized void publicFieldIncrement() {
        // increment the public field and store it to localVar
        int localVar = ++threadPublishedField;
        forceSwitch(100);
        // check for race condition, will it ever occur?
        if (localVar != threadPublishedField) {
            System.err.printf("public-mismatch-%d\n", threadId());
        } else {
            System.out.printf("[public-%d] %d\n", threadId(), threadPublishedField);
        }
    }

    public void wrapperIncrement() throws InterruptedException {
        // increment the shared variable
        synchronized(wrapper) {  // use the monitor of the wrapper object because it's shared
        // or use a static lock defined in the main class 
        wrapper.increment();
        int localVar = wrapper.getVal();
        forceSwitch(3);
        // check for race condition, it will be common
        if (localVar != wrapper.getVal()) {
            System.err.printf("wrapper-mismatch-%d\n", threadId());
        } else {
            System.out.printf("[wrapper-%d] %d\n", threadId(), wrapper.getVal());
        }
    }
    }
    
}

public class ScopeExample {
    
    public static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        List<ExampleThread> threads = new ArrayList<>();
        IntegerWrapper sharedWrapper = new IntegerWrapper();

        for (int i = 0; i < 100; i++) {
            ExampleThread t = new ExampleThread(0, sharedWrapper);
            threads.add(t);
        }

        for(Thread t : threads) {
            t.start();
        }

        for(ExampleThread t : threads) { // modify thread variables
            /* The private fields are not accessible,
             and thus protected by design */
            t.publicFieldIncrement();
            synchronized(sharedWrapper){
            sharedWrapper.increment();
            }
        }
    }
}
