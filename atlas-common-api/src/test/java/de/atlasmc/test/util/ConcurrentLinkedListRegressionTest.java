package de.atlasmc.test.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.ConcurrentLinkedList;

class ConcurrentLinkedListRegressionTest {

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

	private void assertValidState(ConcurrentLinkedList<String> list, String... expected) {
		assertEquals(expected.length, list.size());
		assertIterableEquals(List.of(expected), iterate(list));
		assertEquals(list.size(), list.toArray().length);
		assertEquals(list.isEmpty(), list.size() == 0);

		if (expected.length == 0) {
			assertNull(list.getHead());
			assertNull(list.getTail());
		} else {
			assertEquals(expected[0], list.getHead());
			assertEquals(expected[expected.length - 1], list.getTail());
		}
	}

	private void assertHeadTailMatchesIteration(ConcurrentLinkedList<String> list) {
		List<String> values = iterate(list);
		if (values.isEmpty()) {
			assertNull(list.getHead());
			assertNull(list.getTail());
		} else {
			assertEquals(values.get(0), list.getHead());
			assertEquals(values.get(values.size() - 1), list.getTail());
		}
	}

	// ------------------------------------------------------------------------
	// Head / Tail regression
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Head and tail after add operations")
	void testHeadTailAfterAdd() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();

		list.add("A");
		assertEquals("A", list.getHead());
		assertEquals("A", list.getTail());
		list.add("B");
		list.add("C");
		assertEquals("A", list.getHead());
		assertEquals("C", list.getTail());
		assertHeadTailMatchesIteration(list);
		assertValidState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Head and tail after addFirst")
	void testHeadTailAfterAddFirst() {
		ConcurrentLinkedList<String> list = newList("B", "C");
		list.addFirst("A");
		assertEquals("A", list.getHead());
		assertEquals("C", list.getTail());
		assertValidState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Head and tail after removing head")
	void testHeadTailAfterRemoveHead() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		list.remove("A");
		assertEquals("B", list.getHead());
		assertEquals("C", list.getTail());
		assertValidState(list, "B", "C");
	}

	@Test
	@DisplayName("Head and tail after removing tail")
	void testHeadTailAfterRemoveTail() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		list.remove("C");
		assertEquals("A", list.getHead());
		assertEquals("B", list.getTail());
		assertValidState(list, "A", "B");
	}

	@Test
	@DisplayName("Head and tail after removing middle")
	void testHeadTailAfterRemoveMiddle() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		list.remove("B");
		assertEquals("A", list.getHead());
		assertEquals("C", list.getTail());
		assertValidState(list, "A", "C");
	}

	@Test
	@DisplayName("Repeated head removal")
	void testRepeatedHeadRemoval() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D", "E");
		while (!list.isEmpty()) {
			String oldHead = list.getHead();
			assertTrue(list.remove(oldHead));
			assertHeadTailMatchesIteration(list);
		}
		assertNull(list.getHead());
		assertNull(list.getTail());
		assertValidState(list);
	}

	@Test
	@DisplayName("Repeated tail removal")
	void testRepeatedTailRemoval() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D", "E");
		while (!list.isEmpty()) {
			String oldTail = list.getTail();
			assertTrue(list.remove(oldTail));
			assertHeadTailMatchesIteration(list);
		}
		assertNull(list.getHead());
		assertNull(list.getTail());
		assertValidState(list);
	}

	// ------------------------------------------------------------------------
	// Alternate add/remove regression
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Alternate add and remove does not corrupt list")
	void testAlternateAddRemove() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		for (char c = 'A'; c <= 'Z'; c++) {
			String value = String.valueOf(c);
			assertTrue(list.add(value));
			assertTrue(list.contains(value));
			assertTrue(list.remove(value));
			assertFalse(list.contains(value));
			assertEquals(0, list.size());
			assertNull(list.getHead());
			assertNull(list.getTail());
			assertValidState(list);
		}
	}

	@Test
	@DisplayName("Mixed structural modifications keep valid state")
	void testMixedStructuralOperations() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		list.add("A");
		assertHeadTailMatchesIteration(list);
		list.addFirst("B");
		assertHeadTailMatchesIteration(list);
		list.add("C");
		assertHeadTailMatchesIteration(list);
		list.remove("B");
		assertHeadTailMatchesIteration(list);
		list.remove("C");
		assertHeadTailMatchesIteration(list);
		list.add("D");
		assertHeadTailMatchesIteration(list);
		list.clear();
		assertHeadTailMatchesIteration(list);
		assertValidState(list);
	}

	private void assertState(ConcurrentLinkedList<String> list, String... expected) {
		assertEquals(expected.length, list.size());
		assertIterableEquals(List.of(expected), iterate(list));
		assertEquals(list.size(), list.toArray().length);
		assertEquals(list.isEmpty(), list.size() == 0);
		if (expected.length == 0) {
			assertNull(list.getHead());
			assertNull(list.getTail());
		} else {
			assertEquals(expected[0], list.getHead());
			assertEquals(expected[expected.length - 1], list.getTail());
		}
	}

	// ------------------------------------------------------------------------
	// Iterator regression
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Iterator remove last element leaves empty list")
	void testIteratorRemoveLastElement() {
		ConcurrentLinkedList<String> list = newList("A");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		iterator.remove();
		assertTrue(list.isEmpty());
		assertNull(list.getHead());
		assertNull(list.getTail());
		assertState(list);
	}

	@Test
	@DisplayName("Iterator remove all elements")
	void testIteratorRemoveAllElements() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		assertTrue(list.isEmpty(), String.join(", ", list));
		assertState(list);
	}

	@Test
	@DisplayName("Iterator add after removed current")
	void testIteratorAddAfterRemovedCurrent() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next();
		iterator.remove();
		iterator.add("X");
		assertState(list, "X", "B", "C");
	}

	@Test
	@DisplayName("Previous from head returns null")
	void testPreviousFromHead() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoHead();
		assertThrows(NoSuchElementException.class, () -> iterator.previous());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Next on empty list returns null")
	void testNextOnEmptyList() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertFalse(iterator.hasNext());
		assertState(list);
	}

	@Test
	@DisplayName("Repeated iterator traversal does not corrupt list")
	void testRepeatedIteratorTraversal() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		for (int i = 0; i < 100; i++) {
			List<String> values = iterate(list);
			assertEquals(List.of("A", "B", "C", "D"), values);
		}
		assertState(list, "A", "B", "C", "D");
	}

	@Test
	@DisplayName("Iterator survives full forward traversal")
	void testIteratorFullForwardTraversal() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		assertEquals("B", iterator.next());
		assertEquals("C", iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertFalse(iterator.hasNext());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Iterator backward traversal regression")
	void testIteratorBackwardTraversal() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		iterator.gotoTail();

		assertEquals("D", iterator.get());
		assertEquals("C", iterator.previous());
		assertEquals("B", iterator.previous());
		assertEquals("A", iterator.previous());

		assertThrows(NoSuchElementException.class, () -> iterator.previous());
		assertState(list, "A", "B", "C", "D");
	}

	// ------------------------------------------------------------------------
	// Clear regression
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Repeated clear keeps list valid")
	void testRepeatedClear() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		for (int i = 0; i < 100; i++) {
			list.clear();
			assertTrue(list.isEmpty());
			assertNull(list.getHead());
			assertNull(list.getTail());
			assertState(list);
			list.add("X");
			assertState(list, "X");
		}
	}

	@Test
	@DisplayName("Clear after many operations")
	void testClearAfterManyOperations() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		for (int i = 0; i < 1000; i++) {
			list.add(String.valueOf(i));
		}

		for (int i = 0; i < 500; i++) {
			list.remove(String.valueOf(i));
		}

		list.clear();
		assertState(list);
	}

	// ------------------------------------------------------------------------
	// Global invariants
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Size equals iteration count")
	void testSizeEqualsIterationCount() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		assertEquals(list.size(), iterate(list).size());
		list.remove("B");
		assertEquals(list.size(), iterate(list).size());
		list.add("E");
		assertEquals(list.size(), iterate(list).size());
	}

	@Test
	@DisplayName("Size equals array length")
	void testSizeEqualsArrayLength() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		assertEquals(list.size(), list.toArray().length);
		list.remove("B");
		assertEquals(list.size(), list.toArray().length);
		list.clear();
		assertEquals(list.size(), list.toArray().length);
	}

	@Test
	@DisplayName("Every iterated element is contained")
	void testEveryIteratedElementContained() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		for (String value : list) {
			assertTrue(list.contains(value));
		}
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Removed elements are not returned by iterator")
	void testRemovedElementsNotReturned() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		list.remove("B");
		assertFalse(iterate(list).contains("B"));
		assertState(list, "A", "C");
	}

	@Test
	@DisplayName("List remains usable after complex sequence")
	void testListRemainsUsableAfterComplexSequence() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		list.add("A");
		list.addFirst("B");
		list.add("C");
		list.remove("B");
		list.addAll(List.of("D", "E"));
		list.removeAll(List.of("A", "D"));
		list.retainAll(List.of("C", "E"));
		assertState(list, "C", "E");
		list.clear();
		list.add("FINAL");
		assertState(list, "FINAL");
	}
}