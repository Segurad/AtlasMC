package de.atlasmc.test.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.ConcurrentLinkedList;

class ConcurrentLinkedListBasicTest {

    private ConcurrentLinkedList<String> newList() {
        return new ConcurrentLinkedList<>();
    }

    private ConcurrentLinkedList<String> newList(String... values) {
        ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
        for (String value : values) {
            list.add(value);
        }
        return list;
    }

    private List<String> iterate(ConcurrentLinkedList<String> list) {
        List<String> values = new ArrayList<>();
        for (String value : list) {
            values.add(value);
        }
        return values;
    }

    /**
     * Verifies the global invariants defined by the specification.
     */
    private void assertInvariants(ConcurrentLinkedList<String> list) {
        List<String> iterated = iterate(list);

        // size equals number of iterated elements
        assertEquals(iterated.size(), list.size());

        // size equals array length
        assertEquals(list.size(), list.toArray().length);

        // isEmpty iff size == 0
        assertEquals(list.size() == 0, list.isEmpty());

        if (iterated.isEmpty()) {
            assertNull(list.getHead());
            assertNull(list.getTail());
        } else {
            assertEquals(iterated.get(0), list.getHead());
            assertEquals(iterated.get(iterated.size() - 1), list.getTail());
        }

        // every iterated element is contained
        for (String s : iterated) {
            assertTrue(list.contains(s));
        }

        // iterator terminates
        Iterator<String> it = list.iterator();
        int count = 0;
        while (it.hasNext()) {
            assertNotNull(it.next());
            count++;
            assertTrue(count <= list.size(),
                    "Iterator appears to loop forever.");
        }

        assertEquals(list.size(), count);
    }

    // ------------------------------------------------------------------------
    // Empty List
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("New list is empty")
    void testNewListIsEmpty() {
        ConcurrentLinkedList<String> list = newList();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertFalse(list.contains("A"));

        ConcurrentLinkedList.LinkedListIterator<String> it = list.iterator();

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());

        assertEquals(0, list.toArray().length);

        assertInvariants(list);
    }

    @Test
    @DisplayName("Clear empty list")
    void testClearEmptyList() {
        ConcurrentLinkedList<String> list = newList();

        assertDoesNotThrow(list::clear);

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertFalse(list.iterator().hasNext());

        assertInvariants(list);
    }

    // ------------------------------------------------------------------------
    // add()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Add single element")
    void testAddSingleElement() {
        ConcurrentLinkedList<String> list = newList();

        assertTrue(list.add("A"));

        assertEquals(1, list.size());
        assertFalse(list.isEmpty());

        assertEquals("A", list.getHead());
        assertEquals("A", list.getTail());

        assertTrue(list.contains("A"));

        Iterator<String> it = list.iterator();

        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Add multiple elements")
    void testAddMultipleElements() {
        ConcurrentLinkedList<String> list = newList();

        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        assertEquals(4, list.size());

        assertEquals("A", list.getHead());
        assertEquals("D", list.getTail());

        assertIterableEquals(
                List.of("A", "B", "C", "D"),
                iterate(list));

        assertArrayEquals(
                new Object[]{"A", "B", "C", "D"},
                list.toArray());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Add many elements")
    void testAddManyElements() {
        ConcurrentLinkedList<String> list = newList();

        final int amount = 1000;

        for (int i = 0; i < amount; i++) {
            list.add("E" + i);
        }

        assertEquals(amount, list.size());

        assertEquals("E0", list.getHead());
        assertEquals("E999", list.getTail());

        int index = 0;
        for (String value : list) {
            assertEquals("E" + index++, value);
        }

        assertEquals(amount, index);

        assertInvariants(list);
    }

    @Test
    @DisplayName("Add null throws IllegalArgumentException")
    void testAddNullThrows() {
        ConcurrentLinkedList<String> list = newList();

        assertThrows(IllegalArgumentException.class,
                () -> list.add(null));

        assertTrue(list.isEmpty());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Multiple sequential adds maintain insertion order")
    void testSequentialAddsMaintainOrder() {
        ConcurrentLinkedList<String> list = newList();

        for (char c = 'A'; c <= 'Z'; c++) {
            list.add(String.valueOf(c));
        }

        char expected = 'A';

        for (String value : list) {
            assertEquals(String.valueOf(expected++), value);
        }

        assertEquals('Z' + 1, expected);

        assertEquals("A", list.getHead());
        assertEquals("Z", list.getTail());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Iterator traverses added elements exactly once")
    void testIteratorAfterAdds() {
        ConcurrentLinkedList<String> list = newList(
                "A", "B", "C", "D", "E");

        Iterator<String> iterator = list.iterator();

        List<String> visited = new ArrayList<>();

        while (iterator.hasNext()) {
            visited.add(iterator.next());
        }

        assertEquals(List.of("A", "B", "C", "D", "E"), visited);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Head and tail remain stable during append operations")
    void testHeadTailDuringAppend() {
        ConcurrentLinkedList<String> list = newList();

        list.add("A");
        assertEquals("A", list.getHead());
        assertEquals("A", list.getTail());

        list.add("B");
        assertEquals("A", list.getHead());
        assertEquals("B", list.getTail());

        list.add("C");
        assertEquals("A", list.getHead());
        assertEquals("C", list.getTail());

        list.add("D");
        assertEquals("A", list.getHead());
        assertEquals("D", list.getTail());

        assertInvariants(list);
    }
    
    @Test
    @DisplayName("Add first on empty list")
    void testAddFirstOnEmptyList() {
        ConcurrentLinkedList<String> list = newList();

        list.addFirst("A");

        assertEquals(1, list.size());
        assertFalse(list.isEmpty());

        assertEquals("A", list.getHead());
        assertEquals("A", list.getTail());

        assertIterableEquals(List.of("A"), iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Add first on non-empty list")
    void testAddFirstOnNonEmptyList() {
        ConcurrentLinkedList<String> list = newList("B", "C");

        list.addFirst("A");

        assertEquals(3, list.size());

        assertEquals("A", list.getHead());
        assertEquals("C", list.getTail());

        assertIterableEquals(
                List.of("A", "B", "C"),
                iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Repeated addFirst maintains reverse insertion order")
    void testRepeatedAddFirst() {
        ConcurrentLinkedList<String> list = newList();

        list.addFirst("C");
        list.addFirst("B");
        list.addFirst("A");

        assertEquals(3, list.size());

        assertEquals("A", list.getHead());
        assertEquals("C", list.getTail());

        assertIterableEquals(
                List.of("A", "B", "C"),
                iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Mix addFirst and add")
    void testMixedAddAndAddFirst() {
        ConcurrentLinkedList<String> list = newList();

        list.add("B");
        list.add("C");
        list.addFirst("A");
        list.add("D");
        list.addFirst("0");

        assertIterableEquals(
                List.of("0", "A", "B", "C", "D"),
                iterate(list));

        assertEquals("0", list.getHead());
        assertEquals("D", list.getTail());
        assertEquals(5, list.size());

        assertInvariants(list);
    }

    @Test
    @DisplayName("addFirst(null) throws IllegalArgumentException")
    void testAddFirstNullThrows() {
        ConcurrentLinkedList<String> list = newList();

        assertThrows(
                IllegalArgumentException.class,
                () -> list.addFirst(null));

        assertTrue(list.isEmpty());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Many addFirst operations")
    void testManyAddFirstOperations() {
        ConcurrentLinkedList<String> list = newList();

        for (int i = 0; i < 100; i++) {
            list.addFirst("E" + i);
        }

        assertEquals(100, list.size());

        assertEquals("E99", list.getHead());
        assertEquals("E0", list.getTail());

        int expected = 99;
        for (String value : list) {
            assertEquals("E" + expected--, value);
        }

        assertEquals(-1, expected);

        assertInvariants(list);
    }

    // ------------------------------------------------------------------------
    // contains()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Contains existing element")
    void testContainsExisting() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertTrue(list.contains("C"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains missing element")
    void testContainsMissing() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        assertFalse(list.contains("D"));
        assertFalse(list.contains("X"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains null returns false")
    void testContainsNull() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        assertFalse(list.contains(null));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains after addFirst")
    void testContainsAfterAddFirst() {
        ConcurrentLinkedList<String> list =
                newList("B", "C");

        list.addFirst("A");

        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertTrue(list.contains("C"));

        assertFalse(list.contains("D"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains duplicate elements")
    void testContainsDuplicateElements() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "B", "C");

        assertTrue(list.contains("B"));
        assertTrue(list.contains("A"));
        assertTrue(list.contains("C"));
        assertFalse(list.contains("D"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains on empty list")
    void testContainsOnEmptyList() {
        ConcurrentLinkedList<String> list = newList();

        assertFalse(list.contains("A"));
        assertFalse(list.contains("Anything"));
        assertFalse(list.contains(null));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains every iterated element")
    void testContainsEveryIteratedElement() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D", "E");

        for (String value : list) {
            assertTrue(list.contains(value));
        }

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains remains stable under repeated calls")
    void testContainsRepeatedCalls() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        for (int i = 0; i < 1000; i++) {
            assertTrue(list.contains("A"));
            assertTrue(list.contains("B"));
            assertTrue(list.contains("C"));

            assertFalse(list.contains("X"));
            assertFalse(list.contains("Y"));
            assertFalse(list.contains(null));
        }

        assertInvariants(list);
    }
    
    // ------------------------------------------------------------------------
    // remove()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Remove head element")
    void testRemoveHead() {
        ConcurrentLinkedList<String> list = newList("A", "B", "C");

        assertTrue(list.remove("A"));

        assertEquals(2, list.size());
        assertEquals("B", list.getHead());
        assertEquals("C", list.getTail());

        assertIterableEquals(List.of("B", "C"), iterate(list));

        assertFalse(list.contains("A"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove tail element")
    void testRemoveTail() {
        ConcurrentLinkedList<String> list = newList("A", "B", "C");

        assertTrue(list.remove("C"));

        assertEquals(2, list.size());
        assertEquals("A", list.getHead());
        assertEquals("B", list.getTail());

        assertIterableEquals(List.of("A", "B"), iterate(list));

        assertFalse(list.contains("C"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove middle element")
    void testRemoveMiddle() {
        ConcurrentLinkedList<String> list = newList("A", "B", "C");

        assertTrue(list.remove("B"));

        assertEquals(2, list.size());

        assertEquals("A", list.getHead());
        assertEquals("C", list.getTail());

        assertIterableEquals(List.of("A", "C"), iterate(list));

        assertFalse(list.contains("B"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove only element")
    void testRemoveOnlyElement() {
        ConcurrentLinkedList<String> list = newList("A");

        assertTrue(list.remove("A"));

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertFalse(list.iterator().hasNext());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove missing element")
    void testRemoveMissing() {
        ConcurrentLinkedList<String> list = newList("A", "B", "C");

        assertFalse(list.remove("D"));

        assertEquals(3, list.size());

        assertIterableEquals(
                List.of("A", "B", "C"),
                iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove null returns false")
    void testRemoveNull() {
        ConcurrentLinkedList<String> list = newList("A", "B", "C");

        assertFalse(list.remove(null));

        assertEquals(3, list.size());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove first duplicate only")
    void testRemoveDuplicate() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "B", "C");

        assertTrue(list.remove("B"));

        assertEquals(3, list.size());

        assertIterableEquals(
                List.of("A", "B", "C"),
                iterate(list));

        int count = 0;
        for (String value : list) {
            if ("B".equals(value)) {
                count++;
            }
        }

        assertEquals(1, count);

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove all duplicates")
    void testRemoveAllDuplicates() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "B", "B", "C");

        while (list.remove("B")) {
            // remove until none remain
        }

        assertFalse(list.contains("B"));

        assertEquals(2, list.size());

        assertIterableEquals(
                List.of("A", "C"),
                iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove repeatedly until empty")
    void testRepeatedRemoveUntilEmpty() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D");

        assertTrue(list.remove("A"));
        assertInvariants(list);

        assertTrue(list.remove("B"));
        assertInvariants(list);

        assertTrue(list.remove("C"));
        assertInvariants(list);

        assertTrue(list.remove("D"));

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove head multiple times")
    void testRepeatedHeadRemoval() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D", "E");

        String[] expectedHeads = {"B", "C", "D", "E", null};

        for (String expected : expectedHeads) {
            String currentHead = list.getHead();

            if (currentHead != null) {
                assertTrue(list.remove(currentHead));
            }

            assertEquals(expected, list.getHead());
            assertInvariants(list);
        }

        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Remove tail multiple times")
    void testRepeatedTailRemoval() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D", "E");

        String[] expectedTails = {"D", "C", "B", "A", null};

        for (String expected : expectedTails) {
            String currentTail = list.getTail();

            if (currentTail != null) {
                assertTrue(list.remove(currentTail));
            }

            assertEquals(expected, list.getTail());
            assertInvariants(list);
        }

        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Remove every element from middle outward")
    void testRemoveMixedSequence() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D", "E");

        assertTrue(list.remove("C"));
        assertTrue(list.remove("A"));
        assertTrue(list.remove("E"));
        assertTrue(list.remove("B"));
        assertTrue(list.remove("D"));

        assertTrue(list.isEmpty());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Removing already removed element returns false")
    void testRemoveAlreadyRemoved() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        assertTrue(list.remove("B"));
        assertFalse(list.remove("B"));

        assertEquals(2, list.size());

        assertIterableEquals(
                List.of("A", "C"),
                iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Contains after remove")
    void testContainsAfterRemove() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        assertTrue(list.remove("B"));

        assertFalse(list.contains("B"));
        assertTrue(list.contains("A"));
        assertTrue(list.contains("C"));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Remove preserves iteration order")
    void testRemovePreservesOrder() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D", "E");

        list.remove("B");
        list.remove("D");

        assertIterableEquals(
                List.of("A", "C", "E"),
                iterate(list));

        assertEquals("A", list.getHead());
        assertEquals("E", list.getTail());

        assertInvariants(list);
    }
    
    // ------------------------------------------------------------------------
    // clear()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Clear populated list")
    void testClear() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D");

        list.clear();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertFalse(list.contains("A"));
        assertFalse(list.contains("B"));
        assertFalse(list.contains("C"));
        assertFalse(list.contains("D"));

        assertFalse(list.iterator().hasNext());
        assertEquals(0, list.toArray().length);

        assertInvariants(list);
    }

    @Test
    @DisplayName("Clear single element list")
    void testClearSingleElement() {
        ConcurrentLinkedList<String> list = newList("A");

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Clear already empty list")
    void testClearAlreadyEmpty() {
        ConcurrentLinkedList<String> list = newList();

        assertDoesNotThrow(list::clear);

        assertTrue(list.isEmpty());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Repeated clear")
    void testRepeatedClear() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        for (int i = 0; i < 100; i++) {
            assertDoesNotThrow(list::clear);

            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
            assertNull(list.getHead());
            assertNull(list.getTail());

            assertInvariants(list);
        }
    }

    @Test
    @DisplayName("Add after clear")
    void testAddAfterClear() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        list.clear();
        list.add("D");

        assertEquals(1, list.size());

        assertEquals("D", list.getHead());
        assertEquals("D", list.getTail());

        assertIterableEquals(
                List.of("D"),
                iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("AddFirst after clear")
    void testAddFirstAfterClear() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        list.clear();
        list.addFirst("X");

        assertEquals(1, list.size());

        assertEquals("X", list.getHead());
        assertEquals("X", list.getTail());

        assertIterableEquals(
                List.of("X"),
                iterate(list));

        assertInvariants(list);
    }

    @Test
    @DisplayName("Clear after multiple removals")
    void testClearAfterRemovals() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D");

        list.remove("B");
        list.remove("D");

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertInvariants(list);
    }

    // ------------------------------------------------------------------------
    // size()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Size after add")
    void testSizeAfterAdd() {
        ConcurrentLinkedList<String> list = newList();

        assertEquals(0, list.size());

        list.add("A");
        assertEquals(1, list.size());

        list.add("B");
        assertEquals(2, list.size());

        list.add("C");
        assertEquals(3, list.size());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Size after addFirst")
    void testSizeAfterAddFirst() {
        ConcurrentLinkedList<String> list = newList();

        list.addFirst("A");
        assertEquals(1, list.size());

        list.addFirst("B");
        assertEquals(2, list.size());

        list.addFirst("C");
        assertEquals(3, list.size());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Size after remove")
    void testSizeAfterRemove() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C");

        assertEquals(3, list.size());

        list.remove("B");
        assertEquals(2, list.size());

        list.remove("A");
        assertEquals(1, list.size());

        list.remove("C");
        assertEquals(0, list.size());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Size after clear")
    void testSizeAfterClear() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D");

        assertEquals(4, list.size());

        list.clear();

        assertEquals(0, list.size());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Size during alternating operations")
    void testSizeAlternatingOperations() {
        ConcurrentLinkedList<String> list = newList();

        list.add("A");
        assertEquals(1, list.size());

        list.remove("A");
        assertEquals(0, list.size());

        list.addFirst("B");
        assertEquals(1, list.size());

        list.add("C");
        assertEquals(2, list.size());

        list.remove("B");
        assertEquals(1, list.size());

        list.clear();
        assertEquals(0, list.size());

        assertInvariants(list);
    }

    @Test
    @DisplayName("Size equals iterator count")
    void testSizeEqualsIterationCount() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D", "E");

        int count = 0;

        for (@SuppressWarnings("unused") String ignored : list) {
            count++;
        }

        assertEquals(list.size(), count);

        assertInvariants(list);
    }

    @Test
    @DisplayName("Size equals array length")
    void testSizeEqualsArrayLength() {
        ConcurrentLinkedList<String> list =
                newList("A", "B", "C", "D");

        assertEquals(
                list.size(),
                list.toArray().length);

        list.remove("B");

        assertEquals(
                list.size(),
                list.toArray().length);

        list.add("E");

        assertEquals(
                list.size(),
                list.toArray().length);

        list.clear();

        assertEquals(
                list.size(),
                list.toArray().length);

        assertInvariants(list);
    }

    @Test
    @DisplayName("Alternate add/remove regression")
    void testAlternateAddRemove() {
        ConcurrentLinkedList<String> list = newList();

        for (char c = 'A'; c <= 'Z'; c++) {
            String value = String.valueOf(c);

            list.add(value);
            assertEquals(1, list.size());

            assertTrue(list.remove(value));
            assertEquals(0, list.size());

            assertTrue(list.isEmpty());

            assertNull(list.getHead());
            assertNull(list.getTail());

            assertInvariants(list);
        }
    }

    @Test
    @DisplayName("Head and tail regression after structural changes")
    void testHeadTailRegression() {
        ConcurrentLinkedList<String> list = newList();

        list.add("A");
        assertEquals("A", list.getHead());
        assertEquals("A", list.getTail());

        list.add("B");
        assertEquals("A", list.getHead());
        assertEquals("B", list.getTail());

        list.addFirst("0");
        assertEquals("0", list.getHead());
        assertEquals("B", list.getTail());

        list.remove("0");
        assertEquals("A", list.getHead());
        assertEquals("B", list.getTail());

        list.remove("B");
        assertEquals("A", list.getHead());
        assertEquals("A", list.getTail());

        list.clear();

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertInvariants(list);
    }
    
}