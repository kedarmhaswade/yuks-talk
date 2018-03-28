package org.kedar.jcip.yuks;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p> Quiz based on JCiP listing 8.1</p>
 * <ol>
 *     <li> Does this class compile/run? What problem do you see?</li>
 *     <li> What is the minimal code chat <i>will</i> cause this program to surely deadlock?</li>
 * </ol>
 */
public class DependentTaskDeadlock {
    private static final Random rand = new Random();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        runIt(exec);
    }

    private static void runIt(ExecutorService exec) throws ExecutionException, InterruptedException {
        Future<Integer> future = exec.submit(new BusyRandomNumberGenerator(exec));
        Integer val = future.get();
        System.out.println("in: " + Thread.currentThread().getName() + " I got: " + val);
        exec.shutdown();
    }

    /** A very busy task that generates and returns a random number */
    private static final class BusyRandomNumberGenerator implements Callable<Integer> {
        private final ExecutorService exec;
        BusyRandomNumberGenerator(ExecutorService exec) {
            this.exec = exec;
        }
        @Override
        public Integer call() throws Exception {
            Future<Integer> x = exec.submit(() -> {
                int num = rand.nextInt(1000);
                System.out.println("in: " + Thread.currentThread().getName() + " I am going to return: " + num);
                return num;
            });
            Integer num = x.get() + 10;
            System.out.println(
                "in: " + Thread.currentThread().getName() + " I am going to return: " + num);
            return num;
        }
    }
}
