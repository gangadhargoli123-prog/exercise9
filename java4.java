import java.util.LinkedList;

// Step 1: Define a PC (Producer-Consumer) class
class PC {
    // Shared buffer implemented as a LinkedList
    private final LinkedList<Integer> buffer = new LinkedList<>();
    // Fixed capacity of the buffer as per constraints
    private final int capacity = 2;

    // Step 2: Implement the produce() method
    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            // Enter a synchronized block on this instance
            synchronized (this) {
                // If the buffer is full, release the lock and wait
                while (buffer.size() == capacity) {
                    wait();
                }

                // Add an item to the buffer
                buffer.add(value);
                System.out.println("Producer produced-" + value);
                value++;

                // Notify the waiting consumer thread that an item is available
                notify();

                // Sleep to simulate production delay and make output visible step-by-step
                Thread.sleep(1000);
            }
        }
    }

    // Step 3: Implement the consume() method
    public void consume() throws InterruptedException {
        while (true) {
            // Enter a synchronized block on this instance
            synchronized (this) {
                // If the buffer is empty, release the lock and wait
                while (buffer.size() == 0) {
                    wait();
                }

                // Remove the first item from the buffer
                int val = buffer.removeFirst();
                System.out.println("Consumer consumed-" + val);

                // Notify the waiting producer thread that space is available
                notify();

                // Sleep to simulate consumption delay
                Thread.sleep(1000);
            }
        }
    }
}

// MAIN CLASS
public class Main {
    public static void main(String[] args) {
        // Step 4: Create an instance of PC
        PC pc = new PC();

        // Create the Producer thread
        Thread producerThread = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                System.err.println("Producer thread interrupted.");
            }
        });

        // Create the Consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                System.err.println("Consumer thread interrupted.");
            }
        });

        // Start both threads
        producerThread.start();
        consumerThread.start();

        // Join both threads to wait for their termination (runs indefinitely)
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted.");
        }
    }
}
