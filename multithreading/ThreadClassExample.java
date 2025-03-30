package multithreading;

public class ThreadClassExample {
  public static void main(String[] args) throws InterruptedException {
    //obj runs in parallel​
    //with the main thread​
    T threadRunnable = new T();
    Thread threadObject = new Thread(threadRunnable);
    threadObject.start();
  
    System.out.println("Hello from main!");
  }

}

class Base {}

class T extends Base implements Runnable {
  public void run() {
    System.out.println("Hello from thread");
  }
}
