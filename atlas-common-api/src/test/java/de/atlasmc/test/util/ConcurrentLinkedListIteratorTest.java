package de.atlasmc.test.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.ConcurrentLinkedList;

class ConcurrentLinkedListIteratorTest {

	private ConcurrentLinkedList<String> newList(String... values) {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		for (String value : values) {
			list.add(value);
		}
		return list;
	}

	private List<String> iterate(ConcurrentLinkedList<String> list) {
		List<String> result = new ArrayList<>();
		for (String value : list) {
			result.add(value);
		}
		return result;
	}

	private void assertState(ConcurrentLinkedList<String> list, String... expected) {
		assertEquals(expected.length, list.size());
		assertIterableEquals(List.of(expected), iterate(list));
		if (expected.length == 0) {
			assertNull(list.getHead());
			assertNull(list.getTail());
		} else {
			assertEquals(expected[0], list.getHead());
			assertEquals(expected[expected.length - 1], list.getTail());
		}
	}

	// ------------------------------------------------------------------------
	// Iterator traversal
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Iterator traverses elements in order")
	void testIteratorTraversal() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		Iterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		assertEquals("B", iterator.next());
		assertEquals("C", iterator.next());
		assertEquals("D", iterator.next());
		assertFalse(iterator.hasNext());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list, "A", "B", "C", "D");
	}

	@Test
	@DisplayName("Iterator hasNext before, during and after iteration")
	void testIteratorHasNext() {
		ConcurrentLinkedList<String> list = newList("A", "B");
		Iterator<String> iterator = list.iterator();
		assertTrue(iterator.hasNext());
		assertEquals("A", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("B", iterator.next());
		assertFalse(iterator.hasNext());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list, "A", "B");
	}

	@Test
	@DisplayName("Iterator next past end returns null")
	void testIteratorNextPastEnd() {
		ConcurrentLinkedList<String> list = newList("A");
		Iterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertFalse(iterator.hasNext());
		assertState(list, "A");
	}

	@Test
	@DisplayName("Iterator on empty list")
	void testIteratorOnEmptyList() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertFalse(iterator.hasNext());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list);
	}

	// ------------------------------------------------------------------------
	// get()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Get before movement returns null")
	void testGetBeforeMovement() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertNull(iterator.get());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Get after movement returns current element")
	void testGetAfterMovement() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		assertEquals("A", iterator.get());
		assertEquals("B", iterator.next());
		assertEquals("B", iterator.get());
		assertState(list, "A", "B", "C");
	}

	// ------------------------------------------------------------------------
	// gotoHead()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Goto head positions iterator on first element")
	void testGotoHead() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.gotoHead());
		assertEquals("A", iterator.get());
		assertEquals("B", iterator.next());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Goto head on empty list returns null")
	void testGotoHeadEmpty() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertNull(iterator.gotoHead());
		assertNull(iterator.get());
		assertState(list);
	}

	// ------------------------------------------------------------------------
	// gotoTail()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Goto tail positions iterator on last element")
	void testGotoTail() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("C", iterator.gotoTail());
		assertEquals("C", iterator.get());
		assertFalse(iterator.hasNext());
		assertEquals("B", iterator.previous());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Goto tail on empty list returns null")
	void testGotoTailEmpty() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertNull(iterator.gotoTail());
		assertNull(iterator.get());
		assertState(list);
	}

	// ------------------------------------------------------------------------
	// Previous navigation
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Previous walks backwards")
	void testPrevious() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoTail();
		assertEquals("C", iterator.previous());
		assertEquals("B", iterator.previous());
		assertEquals("A", iterator.previous());
		assertThrows(NoSuchElementException.class, () -> iterator.previous());
		assertState(list, "A", "B", "C", "D");
	}

	@Test
	@DisplayName("Has previous reflects iterator position")
	void testHasPrevious() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertFalse(iterator.hasPrevious());
		iterator.gotoTail();
		assertTrue(iterator.hasPrevious());
		iterator.previous();
		assertTrue(iterator.hasPrevious());
		iterator.previous();
		assertThrows(NoSuchElementException.class, () -> iterator.previous());
		assertFalse(iterator.hasPrevious());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Previous from head returns null")
	void testPreviousFromHead() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoHead();
		assertThrows(NoSuchElementException.class, () -> iterator.previous());
		assertEquals("A", iterator.get());
		assertState(list, "A", "B", "C");
	}

	// ------------------------------------------------------------------------
	// Peek navigation
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Peek next returns next element without moving cursor")
	void testPeekNext() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		assertEquals("B", iterator.peekNext());
		// Cursor must still be on A
		assertEquals("A", iterator.get());
		assertEquals("B", iterator.next());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Peek previous returns previous element without moving cursor")
	void testPeekPrevious() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoTail();
		assertEquals("B", iterator.peekPrevious());
		// Cursor must still be on C
		assertEquals("C", iterator.get());
		assertEquals("B", iterator.previous());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Goto peeked moves cursor to peeked node")
	void testGotoPeeked() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next();
		assertEquals("B", iterator.peekNext());
		iterator.gotoPeeked();
		assertEquals("B", iterator.get());
		assertEquals("C", iterator.next());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Goto peeked without peek has no effect")
	void testGotoPeekedWithoutPeek() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next();
		iterator.gotoPeeked();
		assertEquals("A", iterator.get());
		assertEquals("B", iterator.next());
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Peek next at end returns null")
	void testPeekNextAtEnd() {
		ConcurrentLinkedList<String> list = newList("A");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoTail();
		assertNull(iterator.peekNext());
		assertEquals("A", iterator.get());
		assertState(list, "A");
	}

	@Test
	@DisplayName("Peek previous at head returns null")
	void testPeekPreviousAtHead() {
		ConcurrentLinkedList<String> list = newList("A", "B");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoHead();
		assertNull(iterator.peekPrevious());
		assertEquals("A", iterator.get());
		assertState(list, "A", "B");
	}

	// ------------------------------------------------------------------------
	// Iterator remove()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Iterator removes current element")
	void testIteratorRemoveCurrent() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		iterator.remove();
		assertState(list, "B", "C");
		assertFalse(list.contains("A"));
		assertEquals("B", iterator.next());
	}

	@Test
	@DisplayName("Iterator remove twice only removes once")
	void testIteratorRemoveTwice() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertThrows(IllegalStateException.class, () -> iterator.remove());
		assertEquals(2, list.size());
		assertState(list, "B", "C");
	}

	@Test
	@DisplayName("Iterator remove before next does nothing")
	void testIteratorRemoveBeforeNext() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertDoesNotThrow(iterator::remove);
		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("Iterator remove last element")
	void testIteratorRemoveLastElement() {
		ConcurrentLinkedList<String> list = newList("A");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next();
		iterator.remove();
		assertTrue(list.isEmpty());
		assertNull(list.getHead());
		assertNull(list.getTail());
		assertState(list);
	}

	@Test
	@DisplayName("Iterator continues after removing current")
	void testIteratorContinuesAfterRemove() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		assertEquals("A", iterator.next());
		iterator.remove();
		assertEquals("B", iterator.next());
		assertEquals("C", iterator.next());
		assertEquals("D", iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list, "B", "C", "D");
	}

	// ------------------------------------------------------------------------
	// Iterator add()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Add after current element")
	void testAddAfterCurrent() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next(); // A
		iterator.next(); // B
		iterator.add("X");
		assertState(list, "A", "B", "X", "C");
	}

	@Test
	@DisplayName("Add after tail appends element")
	void testAddAfterTail() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoTail();
		iterator.add("D");
		assertEquals("D", list.getTail());
		assertState(list, "A", "B", "C", "D");
	}

	@Test
	@DisplayName("Add before current element")
	void testAddBeforeCurrent() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		iterator.next(); // A
		iterator.next(); // B
		iterator.addBefore("X");
		assertState(list, "A", "X", "B", "C");
	}

	@Test
	@DisplayName("Add before head inserts at beginning")
	void testAddBeforeHead() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoHead();
		iterator.addBefore("X");
		assertEquals("X", list.getHead());
		assertState(list, "X", "A", "B", "C");
	}

	@Test
	@DisplayName("Add after empty iterator inserts first element")
	void testAddAfterEmptyIterator() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.add("A");
		assertState(list, "A");
	}

	@Test
	@DisplayName("Iterator add null throws")
	void testIteratorAddNullThrows() {
		ConcurrentLinkedList<String> list = newList("A");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next();
		assertThrows(IllegalArgumentException.class, () -> iterator.add(null));
		assertState(list, "A");
	}

	@Test
	@DisplayName("Iterator add after removed current")
	void testIteratorAddAfterRemovedCurrent() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.next(); // A
		iterator.remove();
		iterator.add("X");
		assertState(list, "X", "B", "C");
	}

	// ------------------------------------------------------------------------
	// Iterator robustness
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Iterator survives removal of current element")
	void testIteratorSurvivesCurrentRemoval() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		list.remove("A");
		assertEquals("B", iterator.next());
		assertEquals("C", iterator.next());
		assertEquals("D", iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list, "B", "C", "D");
	}

	@Test
	@DisplayName("Iterator survives removal of future element")
	void testIteratorSurvivesFutureRemoval() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		list.remove("C");
		assertEquals("B", iterator.next());
		assertEquals("D", iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list, "A", "B", "D");
	}

	@Test
	@DisplayName("Iterator survives removal of multiple future elements")
	void testIteratorSurvivesMultipleFutureRemovals() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D", "E");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		list.remove("B");
		list.remove("D");
		assertEquals("C", iterator.next());
		assertEquals("E", iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list, "A", "C", "E");
	}

	@Test
	@DisplayName("Iterator sees inserted element after current position")
	void testIteratorSurvivesInsertion() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		assertEquals("A", iterator.next());
		list.add("D");
		assertEquals("B", iterator.next());
		assertEquals("C", iterator.next());
		assertEquals("D", iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertState(list, "A", "B", "C", "D");
	}

	@Test
	@DisplayName("Iterator handles insertion before current position")
	void testIteratorInsertionBeforeCurrent() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();
		iterator.gotoTail();
		list.addFirst("X");
		assertEquals("B", iterator.previous());
		assertEquals("A", iterator.previous());
		assertState(list, "X", "A", "B", "C");
	}

	@Test
	@DisplayName("Iterator terminates after many modifications")
	void testIteratorTerminatesAfterModifications() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		list.add("D");
		list.remove("B");
		list.addFirst("X");
		list.remove("C");

		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
			assertTrue(count <= list.size() + 2, "Iterator appears to loop forever");
		}
		assertState(list, "X", "A", "D");
	}

	// ------------------------------------------------------------------------
	// Regression tests
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("Iterator remove last element leaves valid list")
	void testIteratorRemoveLastElementRegression() {
		ConcurrentLinkedList<String> list = newList("A", "B");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		iterator.next();
		iterator.next();
		iterator.remove();

		assertState(list, "A");
		assertEquals("A", list.getHead());
		assertEquals("A", list.getTail());
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

		assertTrue(list.isEmpty());
		assertNull(list.getHead());
		assertNull(list.getTail());
		assertState(list);
	}

	@Test
	@DisplayName("Iterator add after remove does not corrupt list")
	void testIteratorAddAfterRemoveRegression() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		iterator.next();
		iterator.remove();
		iterator.add("X");

		assertState(list, "X", "B", "C");
		assertEquals("X", list.getHead());
		assertEquals("C", list.getTail());
	}

	@Test
	@DisplayName("Iterator navigation remains valid after add and remove")
	void testIteratorNavigationAfterMutation() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		iterator.gotoHead();
		iterator.add("X");
		iterator.gotoTail();

		assertEquals("C", iterator.get());
		assertEquals("B", iterator.previous());
		assertEquals("X", iterator.previous());
		assertState(list, "A", "X", "B", "C");
	}

	@Test
	@DisplayName("Iterator repeated navigation regression")
	void testIteratorRepeatedNavigation() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");
		ConcurrentLinkedList.LinkedListIterator<String> iterator = list.iterator();

		for (int i = 0; i < 100; i++) {
			assertEquals("A", iterator.gotoHead());
			assertEquals("D", iterator.gotoTail());
			assertEquals("C", iterator.previous());
			assertEquals("D", iterator.next());
		}
		assertState(list, "A", "B", "C", "D");
	}
}