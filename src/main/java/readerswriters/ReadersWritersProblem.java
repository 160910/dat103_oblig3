package readerswriters;

import java.util.concurrent.Semaphore;

public class ReadersWritersProblem {

    // common to both reader and writer processes
    // functions as a mutual exclusion semaphore for the writers
    static Semaphore rw_mutex = new Semaphore(1);

    // used to ensure mutual exclusion when the variable read_count is updated
    static Semaphore mutex = new Semaphore(1);

    // keeps track of how many processes are currently reading the object
    static int read_count = 0;


    public static void write() throws InterruptedException {
        while (true) {
            rw_mutex.acquire();

            // writing

            rw_mutex.release();
        }
    }

    public static void read() throws InterruptedException {
        while (true) {
            mutex.acquire();
            read_count++;
            if (read_count == 1) {
                rw_mutex.acquire();
            }
            mutex.release();

            // reading

            mutex.acquire();
            read_count--;
            if (read_count == 0) {
                rw_mutex.release();
            }
            mutex.release();
        }
    }

}
