package com.company;

import java.util.stream.Stream;

/**
 * Created by igoru on 04-Jul-17.
 */
public interface NumberGenerator {
    Stream<Long> generate(int size);
}
