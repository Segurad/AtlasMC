package de.atlasmc.test.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.ConcurrentLinkedList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentLinkedListTest {

    private ConcurrentLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new ConcurrentLinkedList<>();
    }

    @Test
    void testAdd() {
        assertTrue(list.add(1));
        assertEquals(1, list.size());
        assertEquals(1, list.getHead());
        assertEquals(1, list.getTail());
    }

    @Test
    void testAddFirst() {
        list.add(2);
        list.addFirst(1);
        assertEquals(2, list.size());
        assertEquals(1, list.getHead());
        assertEquals(2, list.getTail());
    }

    @Test
    void testRemove() {
        list.add(1);
        list.add(2);
        assertTrue(list.remove(1));
        assertEquals(1, list.size());
        assertEquals(2, list.getHead());
        assertEquals(2, list.getTail());
    }

    @Test
    void testClear() {
        list.add(1);
        list.add(2);
        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test
    void testContains() {
        list.add(1);
        list.add(2);
        assertTrue(list.contains(1));
        assertFalse(list.contains(3));
    }

    @Test
    void testAddAll() {
        List<Integer> elements = Arrays.asList(1, 2, 3);
        assertTrue(list.addAll(elements));
        assertEquals(3, list.size());
        assertEquals(1, list.getHead());
        assertEquals(3, list.getTail());
    }

    @Test
    void testRemoveAll() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.removeAll(Arrays.asList(1, 3)));
        assertEquals(1, list.size());
        assertEquals(2, list.getHead());
    }

    @Test
    void testRetainAll() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.retainAll(Collections.singleton(2)));
        assertEquals(1, list.size());
        assertEquals(2, list.getHead());
    }

    @Test
    void testToArray() {
        list.add(1);
        list.add(2);
        list.add(3);
        Object[] array = list.toArray();
        assertArrayEquals(new Object[]{1, 2, 3}, array);
    }

    @Test
    void testIterator() {
        list.add(1);
        list.add(2);
        ConcurrentLinkedList.LinkedListIterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        iterator.add(3);
        assertEquals(3, list.size());
        assertTrue(list.contains(3));
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            final int value = i;
            executor.execute(() -> list.add(value));
        }
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));

        assertEquals(1000, list.size());
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> list.add(null));
    }

    @Test
    void testRemoveFromEmptyList() {
        assertFalse(list.remove(1));
    }

    @Test
    void testIteratorEdgeCases() {
        ConcurrentLinkedList.LinkedListIterator<Integer> iterator = list.iterator();
        assertNull(iterator.get());
        assertNull(iterator.previous());
        assertNull(iterator.next());
    }
}
