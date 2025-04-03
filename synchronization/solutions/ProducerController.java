package synchronization.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ProducerController {

    public static int NUM_RUN = 50;

    static Semaphore accessBuffer;
    static Lock lock;
    static int numControllers = 0;
    static Semaphore canCheck;

    public static void init() {
        accessBuffer = new Semaphore(1);
        lock = new ReentrantLock();
        canCheck = new Semaphore(10);
    }

    public static class Buffer {

        private boolean producing = false;
        private int checkingCount = 0;

        public void produce() {
            producing = true;
            if (checkingCount > 0) {
                throw new RuntimeException("Can't produce if controllers checking");
            }

            System.out.println("Producer is producing...");

            producing = false;
        }

        public synchronized void check() {
            checkingCount++;

            if (producing) {
                throw new RuntimeException("Can't check if producer is producing");
            }

            if (checkingCount > 10) {
                throw new RuntimeException(
                        "No more than 10 checks can be in progress simultaneously"
                );
            }

            System.out.println("Controller is checking...");

            checkingCount --;
        }
    }

    public static class Producer extends Thread {
        private final Buffer buffer;

        public Producer(Buffer b) {
            this.buffer = b;
        }

        public void execute() throws InterruptedException {
            accessBuffer.acquire();
            buffer.produce();
            accessBuffer.release();

        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Controller extends Thread {

        private final Buffer buffer;

        public Controller(Buffer buffer) {
            this.buffer = buffer;
        }

        public void execute() throws InterruptedException {

            lock.lock();
            if (numControllers == 0) {
                accessBuffer.acquire();
            }

            numControllers++;
            lock.unlock();

            canCheck.acquire();
            buffer.check();

            lock.lock();
            numControllers--;
            canCheck.release();

            if (numControllers == 0) {
                accessBuffer.release();
            }
            lock.unlock();

        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer p = new Producer(buffer);
        List<Controller> controllers = new ArrayList<>();
        init();
        for (int i = 0; i < 100; i++) {
            controllers.add(new Controller(buffer));
        }
        p.start();
        for (int i = 0; i < 100; i++) {
            controllers.get(i).start();
        }

    }

}
