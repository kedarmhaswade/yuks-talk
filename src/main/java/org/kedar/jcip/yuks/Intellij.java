/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.kedar.jcip.yuks;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Intellij {
    public static void main(String[] args) {
        if (args.length > 0) { // very cool: Cmd+Alt T
            out.println("Intellij.main");
        }
        out.println("args = [" + Arrays.toString(args) + "]");
        List<String> names = Arrays.asList("Larry", "Moe", "Curly");
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

        }
        for (int j = 0; j < 1; j++) {

        }
        for (String name : names) { //iter live template

        }
        try {
            for (int i = 0; i < args.length; i++) {

                String arg = args[i];


            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    private static void calculate() {
        long time = currentTimeMillis();
        try {
            long total = 0;
            for (int i = 0; i < 1000; i++) {
                total += i;
            }
        } finally {
            time = currentTimeMillis() - time;
            out.println("time elapsed = " + time + " ms, or: " + MILLISECONDS.toSeconds(time));
        }
    }
    private static void foo(String... args) {
        if (args.length > 0) {
            System.out.println("hello");
        }
        //ritar!
        for (int i = args.length - 1; i >= 0; i--) {
            String arg = args[i];
            System.out.println(arg);
        }
    }
}
