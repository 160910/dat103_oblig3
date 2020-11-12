package diningphilosophers;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhilosophersProblem {

    static int n = 5;
    static Philosopher[] philosophers = new Philosopher[n];
    static Chopstick[] chopsticks = new Chopstick[n];

    static class Chopstick {
        public Semaphore mutex = new Semaphore(1);

        void grab() {
            try {
                mutex.acquire();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        void release() {
            mutex.release();
        }

    }

    enum Activity {
        EATING, THINKING
    }

    static class Philosopher extends Thread {
        public int number;
        Activity activity;
        public Chopstick leftChop;
        public Chopstick rightChop;

        Philosopher(int num, Chopstick left, Chopstick right) {
            number = num; leftChop = left; rightChop = right;
        }

        public Activity getActivity () {
            return activity;
        }

        public void run() {
            System.out.println("Hi! I'm philosopher #" + number);
            while (true) {
                think();
                // checks if both chopsticks are available
                if (philosophers[(number-1)%n].getActivity() != Activity.EATING && philosophers[(number+1)%n].getActivity() != Activity.EATING) {
                    leftChop.grab();
                    System.out.println("Philosopher #" + number + " grabs left chopstick.");
                    rightChop.grab();
                    System.out.println("Philosopher #" + number + " grabs right chopstick.");
                    eat();
                    leftChop.release();
                    System.out.println("Philosopher #" + number + " releases left chopstick.");
                    rightChop.release();
                    System.out.println("Philosopher #" + number + " releases right chopstick.");
                }
            }
        }

        public void eat() {
            try {
                activity = Activity.EATING;
                int eatTime = ThreadLocalRandom.current().nextInt(0, 1000);
                System.out.println("Philosopher #" + number + " eats for " + eatTime + "ms.");
                Thread.sleep(eatTime);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        public void think() {
            try {
                activity = Activity.THINKING;
                int thinkTime = ThreadLocalRandom.current().nextInt(0,1000);
                System.out.println("Philosopher #" + number + " thinks for " + thinkTime + "ms.");
                Thread.sleep(thinkTime);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    public static void main (String[] args) {
        for (int i = 0; i < n; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < n; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%n]);
            philosophers[i].start();
        }
    }

}

