package multithreading;
public class RunnableInterfaceExample {
    public static void main(String[] args) {
      Runnable obj = new T2();
      Thread tobj = new Thread(obj);
      tobj.start(); 
      // tobj runs in parallel​
      // with the main thread​
      System.out.println("Hello from main!");
    }
  }
  
  class Base {}
  
  class T2 extends Base implements Runnable {
    public void run() {
      // thread's main logic​
      System.out.println("Hello from T2!");
    }
  }