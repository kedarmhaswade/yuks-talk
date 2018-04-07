package org.kedar.jcip.yuks;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;

/**
 * <p> An unclear alien method call may cause deadlock; JCiP 10.1.3</p>
 */
public final class AlienConnection {

    public static void main(String[] args) {
        final Registry registry = new Registry();
        List<Item> items = asList(new Item("Surak", registry), new Item("Spock", registry));
        Runnable wt = () -> items.forEach(Item::doWork);
        Runnable pt = () -> {
            for (int i = 0; i < 1000; i++) {
                registry.print();
            }
        };
        Thread worker = new Thread(wt, "worker");
        worker.setDaemon(false);
        worker.start();
        Thread printer = new Thread(pt, "printer");
        printer.setDaemon(false);
        printer.start();
    }

    final static class Item {
        private final String name;
        private final Registry registry;

        Item(String name, Registry registry) {
            this.name = name;
            this.registry = registry;
        }

        synchronized void doWork() {
            registry.add(this);
            BigInteger bigInteger = BigInteger.probablePrime(100, new Random(currentTimeMillis()));
            for (int i = 0; i < 2; i++) {
                System.out.println("worked next prime: " + (bigInteger = bigInteger.nextProbablePrime()));
            }
            registry.remove(this);
        }

        synchronized String getName() {
            return name;
        }
    }

    final static class Registry {
        //@GuardedBy this
        private final Set<Item> items = new HashSet<>();

        synchronized void remove(Item item) {
            items.remove(item); // return value ignored
        }

        synchronized void add(Item item) {
            items.add(item); // return value ignored!
        }

        synchronized void print() {
            System.out.println("ts: " + currentTimeMillis() + ": num elements = " + items.size());
            items.forEach(a -> System.out.println(a.getName()));
        }
    }
}
