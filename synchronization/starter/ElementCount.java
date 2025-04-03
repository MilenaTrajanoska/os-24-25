package synchronization.starter;

import java.util.Arrays;
import java.util.Random;

public class ElementCount {

    static long count = 0;
    static long max = 0;

    static final BoundedRandomGenerator random = new BoundedRandomGenerator();
    private static final int ARRAY_LENGTH = 10000;
    private static final int NUM_THREADS = 10;

    private static final int SEARCH_TARGET = 5;  // This is the target element we are looking for

    // TODO: Define sychronization elements and initialize

    // DO NOT CHANGE
    public static int[] getSubArray(int[] array, int start, int end) {
        return Arrays.copyOfRange(array, start, end);
    }

    public static void main(String[] args) throws InterruptedException {

        int[] arr = ArrayGenerator.generate(ARRAY_LENGTH, SEARCH_TARGET);

        // TODO: Make the CountThread class a thread and start 10 instances
        // Each instance should take a subarray from the original array with equal length

        

        // all threads done
        

        // check max count

        // DO NOT CHANGE

        System.out.println("The number of total counted elements is: " + count);
        System.out.println("The generated number of elements is: " + ArrayGenerator.elementCount);

        // TODO: The max thread should print the number of occurences

    }

// TO DO: Make the SearchThread class a thread
// You can add methods or attributes if necessary
    static class CountThread  {

        private int[] arr;
        private int target;


        public CountThread(int[] arr, int target) {
            this.arr = arr;
            this.target = target;
        }

        public void countElements() {
            for (int num: this.arr) {
                if (num == target) {
                    count++;
                }
            }
        }

        public void countElementsParallel() throws InterruptedException {
            // TO DO: Implement and run the parallel counting method from the thread

        }

        public void run() {
            try {
                countElementsParallel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //************ DO NOT CHANGE ************//
    static class BoundedRandomGenerator {
        static final Random random = new Random();
        static final int RANDOM_BOUND = 100;

        public int nextInt() {
            return random.nextInt(RANDOM_BOUND);
        }

    }

    static class ArrayGenerator {

        static int elementCount;

        static int[] generate(int length, int target) {
            int[] array = new int[length];

            for (int i = 0; i < length; i++) {
                int element = ElementCount.random.nextInt();

                if (element == target) {
                    elementCount++;
                }

                array[i] = element;
            }

            return array;
        }
    }


}

