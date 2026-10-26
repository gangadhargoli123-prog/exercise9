// ==========================================
// METHOD 1: By Extending the Thread Class
// ==========================================

class GoodMorningThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("Good Morning");
            try {
                Thread.sleep(1000); // 1 Second Delay
            } catch (InterruptedException e) {
                System.err.println("Good Morning thread interrupted.");
                break;
            }
        }
    }
}

class HelloThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("Hello");
            try {
                Thread.sleep(2000); // 2 Seconds Delay
            } catch (InterruptedException e) {
                System.err.println("Hello thread interrupted.");
                break;
            }
        }
    }
}

class WelcomeThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("Welcome");
            try {
                Thread.sleep(3000); // 3 Seconds Delay
            } catch (InterruptedException e) {
                System.err.println("Welcome thread interrupted.");
                break;
            }
        }
    }
}

// ==========================================
// METHOD 2: By Implementing the Runnable Interface
// ==========================================

class GoodMorningRunnable implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Good Morning");
            try {
                Thread.sleep(1000); // 1 Second Delay
            } catch (InterruptedException e) {
                System.err.println("Good Morning Runnable interrupted.");
                break;
            }
        }
    }
}

class HelloRunnable implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Hello");
            try {
                Thread.sleep(2000); // 2 Seconds Delay
            } catch (InterruptedException e) {
                System.err.println("Hello Runnable interrupted.");
                break;
            }
        }
    }
}

class WelcomeRunnable implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Welcome");
            try {
                Thread.sleep(3000); // 3 Seconds Delay
            } catch (InterruptedException e) {
                System.err.println("Welcome Runnable interrupted.");
                break;
            }
        }
    }
}

// ==========================================
// MAIN CLASS
// ==========================================
public class Main {
    public static void main(String[] args) {
        
        System.out.println("Starting Threads Concurrent Execution...\n");

        // ----------------------------------------------------
        // EXECUTION - METHOD 1: Extending Thread Class
        // ----------------------------------------------------
        GoodMorningThread t1 = new GoodMorningThread();
        HelloThread t2 = new HelloThread();
        WelcomeThread t3 = new WelcomeThread();
        
        // Start Method 1 Threads
        t1.start();
        t2.start();
        t3.start();

        // ----------------------------------------------------
        // EXECUTION - METHOD 2: Implementing Runnable Interface
        // ----------------------------------------------------
        /* 
        Thread r1 = new Thread(new GoodMorningRunnable());
        Thread r2 = new Thread(new HelloRunnable());
        Thread r3 = new Thread(new WelcomeRunnable());
        
        // Start Method 2 Threads
        r1.start();
        r2.start();
        r3.start();
        */
    }
}
