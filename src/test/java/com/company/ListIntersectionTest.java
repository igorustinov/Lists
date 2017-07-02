package com.company;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by igoru on 02-Jul-17.
 */
public class ListIntersectionTest {
    @org.junit.Test
    public void iterator() throws Exception {

        final List<Long> list = Arrays.asList(2L, 3L, 5L, 6L);
        final Set<Long> set = new HashSet<>(Arrays.asList(200L, 3L, 500L));

        final Iterator<Long> it = new ListIntersection<Long>(list, set).iterator();
        assertTrue(it.hasNext());
        assertTrue(it.next() == 3L);
        assertFalse(it.hasNext());
    }
}