package com.company;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by igoru on 02-Jul-17.
 */
public class ListIntersectionBuilder {

    private int sizeOne;
    private int sizeTwo;
    private ListToUseForHashSet listToUseForHashset;
    private RandomNumberGenerator numberGenerator = new RandomNumberGenerator();

    public ListIntersectionBuilder sizeOne(int sizeOne) {
        this.sizeOne = sizeOne;
        return this;
    }

    public ListIntersectionBuilder sizeTwo(int sizeTwo) {
        this.sizeTwo = sizeTwo;
        return this;
    }

    public ListIntersectionBuilder listToUseForHashset(ListToUseForHashSet useForHashset) {
        this.listToUseForHashset = useForHashset;
        return this;
    }
    
    public ListIntersection<Long> build() {
        Stream<Long> first = numberGenerator.generate(sizeOne);
        Stream<Long> second = numberGenerator.generate(sizeTwo);

        if (listToUseForHashset == null || listToUseForHashset == ListToUseForHashSet.DEFAULT) {
            listToUseForHashset = sizeOne > sizeTwo ? ListToUseForHashSet.SECOND : ListToUseForHashSet.FIRST;
        }

        List<Long> list;
        Set<Long> set;

        if (listToUseForHashset == ListToUseForHashSet.FIRST) {
            set = first.collect(Collectors.toSet());
            list = second.collect(Collectors.toList());
        } else {
            set = second.collect(Collectors.toSet());
            list = first.collect(Collectors.toList());
        }

        return new ListIntersection<>(list, set);
    }

    private List<Long> getList(int size) {
        return new Random().longs(size).boxed().collect(Collectors.toList());
    }

    public enum ListToUseForHashSet {
        FIRST, SECOND, DEFAULT
    }

    public void setNumberGenerator(RandomNumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }
}
