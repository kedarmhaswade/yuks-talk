package org.kedar.jcip.yuks;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * <p> Based on JCiP lock-ordering deadlocks. </p>
 */
public final class FastingForever {
    private static final Random r = new Random(1234);
    private final Object jam = new Object();
    private final Object bread = new Object();

    public static void main(String[] args) throws InterruptedException {
        FastingForever f = new FastingForever();
        for (int day = 0; day < 1; day++) {
            f.breakTheFast();
            System.out.println("breakfast done for day: " + day);
        }
    }

    private static void eat() {
        spendTime(1000);
    }

    private static void prepare() {
        spendTime(10);
    }


    private static void spendTime(int n) {
        String s = String.valueOf(r.nextInt(1_000_000));
        for (int i = 0; i < n; i++) {
            BigInteger x = new BigInteger(s).nextProbablePrime();
            System.out.print("");
            s = x.toString();
        }
    }

    private void breakTheFast() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(2);
        new Thread(new Tom(this.bread, this.jam, startSignal, doneSignal), "tom").start();
        new Thread(new Jerry(this.bread, this.jam, startSignal, doneSignal), "jerry").start();
        startSignal.countDown();
        doneSignal.await();
    }

    private static class Jerry implements Runnable {
        private final Object bread;
        private final Object jam;
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Jerry(Object bread, Object jam, CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.bread = bread;
            this.jam = jam;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (jam) {
                prepare();
                synchronized (bread) {
                    eat();
                }
            }
            doneSignal.countDown();
        }
    }

    private static class Tom implements Runnable {
        private final Object bread;
        private final Object jam;
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Tom(Object bread, Object jam, CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.bread = bread;
            this.jam = jam;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (bread) {
                prepare();
                synchronized (jam) {
                    eat();
                }
            }
            doneSignal.countDown();
        }
    }

}
