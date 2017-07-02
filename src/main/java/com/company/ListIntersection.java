package com.company;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by igoru on 02-Jul-17.
 */
public class ListIntersection<T> implements Iterable<T> {

    private final List<T> first;
    private final Set<T> second;

    public ListIntersection(List<T> first, Set<T> second) {
        this.first = first;
        this.second = new HashSet<>(second);
    }

    public Iterator<T> iterator() {
        return first.stream().filter(second::contains).iterator();
    }
}
