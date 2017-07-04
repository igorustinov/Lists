package com.company;

import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by igoru on 02-Jul-17.
 */
public class RandomNumberGenerator implements NumberGenerator {

    @Override
    public Stream<Long> generate(int size) {
        //return LongStream.range(0, size).boxed();
        return new Random().longs(size).boxed();
    }
}
