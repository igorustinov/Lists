package com.company;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by igoru on 02-Jul-17.
 */
public class NumberGenerator {

    public Stream<Long> generate(int size) {
        return new Random().longs(size).boxed();
    }
}
