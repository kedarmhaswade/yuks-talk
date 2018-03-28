package org.kedar.jcip.yuks;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * <p> What does this program print?</p>
 */
public class ExceptionallyNamedException {
    public static void main(String[] args) {
        List<String> stooges = new ArrayList<>(asList("Larry", "Moe", "Jo", "Curly"));
        for (String stooge : stooges) {
            if ("Jo".equals(stooge)) {
                stooges.remove(stooge);
            }
        }
        System.out.println(stooges);
    }
}
