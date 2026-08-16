package de.atlasmc.test.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.ConcurrentLinkedList;

class ConcurrentLinkedListCollectionTest {

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

	private void assertState(ConcurrentLinkedList<String> list, String... expected) {

		assertEquals(expected.length, list.size());

		assertIterableEquals(List.of(expected), iterate(list));

		assertEquals(list.size(), list.toArray().length);

		assertEquals(list.size() == 0, list.isEmpty());

		if (expected.length == 0) {
			assertNull(list.getHead());
			assertNull(list.getTail());
		} else {
			assertEquals(expected[0], list.getHead());
			assertEquals(expected[expected.length - 1], list.getTail());
		}
	}

	// ------------------------------------------------------------------------
	// toArray()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("toArray on empty list")
	void testToArrayEmpty() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();

		Object[] array = list.toArray();

		assertNotNull(array);
		assertEquals(0, array.length);

		assertState(list);
	}

	@Test
	@DisplayName("toArray single element")
	void testToArraySingle() {
		ConcurrentLinkedList<String> list = newList("A");

		Object[] array = list.toArray();

		assertArrayEquals(new Object[] { "A" }, array);

		assertState(list, "A");
	}

	@Test
	@DisplayName("toArray multiple elements")
	void testToArrayMultiple() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		Object[] array = list.toArray();

		assertArrayEquals(new Object[] { "A", "B", "C" }, array);

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("toArray preserves iteration order")
	void testToArrayOrder() {
		ConcurrentLinkedList<String> list = newList("1", "2", "3", "4", "5");

		assertArrayEquals(new Object[] { "1", "2", "3", "4", "5" }, list.toArray());

		assertState(list, "1", "2", "3", "4", "5");
	}

	@Test
	@DisplayName("toArray after removals")
	void testToArrayAfterRemove() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");

		list.remove("B");
		list.remove("D");

		assertArrayEquals(new Object[] { "A", "C" }, list.toArray());

		assertState(list, "A", "C");
	}

	@Test
	@DisplayName("toArray after clear")
	void testToArrayAfterClear() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		list.clear();

		assertArrayEquals(new Object[] {}, list.toArray());

		assertState(list);
	}

	// ------------------------------------------------------------------------
	// toArray(T[])
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("toArray with exact sized array reuses array")
	void testExactSizeArray() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		String[] target = new String[3];

		String[] result = list.toArray(target);

		assertSame(target, result);

		assertArrayEquals(new String[] { "A", "B", "C" }, result);

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("toArray with larger array fills remaining values with null")
	void testLargerArray() {
		ConcurrentLinkedList<String> list = newList("A", "B");

		String[] target = new String[] { "X", "X", "X", "X" };

		String[] result = list.toArray(target);

		assertSame(target, result);

		assertEquals("A", result[0]);
		assertEquals("B", result[1]);

		assertNull(result[2]); // test if the element after the lists element was set null
		
		assertEquals("X", result[3]);

		assertState(list, "A", "B");
	}

	@Test
	@DisplayName("toArray with smaller array allocates new array")
	void testSmallerArray() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		String[] target = new String[1];

		String[] result = list.toArray(target);

		assertNotSame(target, result);

		assertArrayEquals(new String[] { "A", "B", "C" }, result);

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("toArray typed empty array")
	void testTypedArrayEmpty() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();

		String[] result = list.toArray(new String[0]);

		assertNotNull(result);

		assertEquals(0, result.length);

		assertState(list);
	}

	@Test
	@DisplayName("toArray typed array after mutation")
	void testTypedArrayAfterMutation() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		list.remove("B");
		list.add("D");

		String[] result = list.toArray(new String[0]);

		assertArrayEquals(new String[] { "A", "C", "D" }, result);

		assertState(list, "A", "C", "D");
	}

	// ------------------------------------------------------------------------
	// containsAll()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("containsAll empty collection returns true")
	void testContainsAllEmptyCollection() {
		ConcurrentLinkedList<String> list = newList("A", "B");

		assertTrue(list.containsAll(List.of()));

		assertState(list, "A", "B");
	}

	@Test
	@DisplayName("containsAll with present elements returns true")
	void testContainsAllPresent() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertTrue(list.containsAll(List.of("A", "C")));

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("containsAll with missing element returns false")
	void testContainsAllMissing() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertFalse(list.containsAll(List.of("A", "X")));

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("containsAll with duplicate requested elements")
	void testContainsAllDuplicates() {
		ConcurrentLinkedList<String> list = newList("A", "B");

		assertTrue(list.containsAll(List.of("A", "A", "B")));

		assertState(list, "A", "B");
	}

	@Test
	@DisplayName("containsAll after removal")
	void testContainsAllAfterRemove() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		list.remove("B");

		assertTrue(list.containsAll(List.of("A", "C")));

		assertFalse(list.containsAll(List.of("A", "B")));

		assertState(list, "A", "C");
	}

	// ------------------------------------------------------------------------
	// addAll()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("addAll with empty collection returns false")
	void testAddAllEmpty() {
		ConcurrentLinkedList<String> list = newList("A", "B");

		assertFalse(list.addAll(List.of()));

		assertState(list, "A", "B");
	}

	@Test
	@DisplayName("addAll with multiple elements")
	void testAddAllMultiple() {
		ConcurrentLinkedList<String> list = newList("A");

		assertTrue(list.addAll(List.of("B", "C", "D")));

		assertState(list, "A", "B", "C", "D");
	}

	@Test
	@DisplayName("addAll preserves collection order")
	void testAddAllOrder() {
		ConcurrentLinkedList<String> list = new ConcurrentLinkedList<>();

		List<String> source = List.of("1", "2", "3", "4");

		assertTrue(list.addAll(source));

		assertIterableEquals(source, iterate(list));

		assertState(list, "1", "2", "3", "4");
	}

	@Test
	@DisplayName("addAll with multiple calls appends")
	void testAddAllMultipleCalls() {
		ConcurrentLinkedList<String> list = newList("A");

		assertTrue(list.addAll(List.of("B", "C")));

		assertTrue(list.addAll(List.of("D", "E")));

		assertState(list, "A", "B", "C", "D", "E");
	}

	@Test
	@DisplayName("addAll after clear")
	void testAddAllAfterClear() {
		ConcurrentLinkedList<String> list = newList("A", "B");

		list.clear();

		assertTrue(list.addAll(List.of("C", "D")));

		assertState(list, "C", "D");
	}

	@Test
	@DisplayName("addAll keeps head and tail correct")
	void testAddAllHeadTail() {
		ConcurrentLinkedList<String> list = newList("A");

		list.addAll(List.of("B", "C"));

		assertEquals("A", list.getHead());
		assertEquals("C", list.getTail());

		assertState(list, "A", "B", "C");
	}

	// ------------------------------------------------------------------------
	// removeAll()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("removeAll with empty collection returns false")
	void testRemoveAllEmpty() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertFalse(list.removeAll(List.of()));

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("removeAll removes matching elements")
	void testRemoveAllSomeMatching() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");

		assertTrue(list.removeAll(List.of("B", "D")));

		assertState(list, "A", "C");
	}

	@Test
	@DisplayName("removeAll with no matches returns false")
	void testRemoveAllNoMatching() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertFalse(list.removeAll(List.of("X", "Y")));

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("removeAll removes head")
	void testRemoveAllHead() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertTrue(list.removeAll(List.of("A")));

		assertEquals("B", list.getHead());

		assertState(list, "B", "C");
	}

	@Test
	@DisplayName("removeAll removes tail")
	void testRemoveAllTail() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertTrue(list.removeAll(List.of("C")));

		assertEquals("B", list.getTail());

		assertState(list, "A", "B");
	}

	@Test
	@DisplayName("removeAll removes everything")
	void testRemoveAllEverything() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertTrue(list.removeAll(List.of("A", "B", "C")));

		assertState(list);
	}

	@Test
	@DisplayName("removeAll with duplicates")
	void testRemoveAllDuplicates() {
		ConcurrentLinkedList<String> list = newList("A", "B", "B", "C");

		assertTrue(list.removeAll(List.of("B")));

		assertState(list, "A", "C");
	}

	// ------------------------------------------------------------------------
	// retainAll()
	// ------------------------------------------------------------------------

	@Test
	@DisplayName("retainAll keeps everything")
	void testRetainAllKeepEverything() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertFalse(list.retainAll(List.of("A", "B", "C")));

		assertState(list, "A", "B", "C");
	}

	@Test
	@DisplayName("retainAll removes some elements")
	void testRetainAllRemoveSome() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");

		assertTrue(list.retainAll(List.of("A", "C")));

		assertState(list, "A", "C");
	}

	@Test
	@DisplayName("retainAll removes everything")
	void testRetainAllRemoveEverything() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertTrue(list.retainAll(List.of()));

		assertState(list);
	}

	@Test
	@DisplayName("retainAll keeps head and tail correct")
	void testRetainAllHeadTail() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");

		assertTrue(list.retainAll(List.of("B", "C")));

		assertEquals("B", list.getHead());
		assertEquals("C", list.getTail());

		assertState(list, "B", "C");
	}

	@Test
	@DisplayName("retainAll after removals")
	void testRetainAllAfterRemove() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C", "D");

		list.remove("B");

		assertTrue(list.retainAll(List.of("A", "D")));

		assertState(list, "A", "D");
	}

	@Test
	@DisplayName("Collection operations maintain size consistency")
	void testCollectionOperationsSizeConsistency() {
		ConcurrentLinkedList<String> list = newList("A", "B", "C");

		assertEquals(list.size(), list.toArray().length);

		list.addAll(List.of("D", "E"));

		assertEquals(list.size(), list.toArray().length);

		list.removeAll(List.of("B", "D"));

		assertEquals(list.size(), list.toArray().length);

		list.retainAll(List.of("A", "E"));

		assertEquals(list.size(), list.toArray().length);

		assertState(list, "A", "E");
	}

	@Test
	@DisplayName("Collection operations preserve iteration order")
	void testCollectionOperationsOrder() {
		ConcurrentLinkedList<String> list = newList("A", "B");

		list.addAll(List.of("C", "D"));

		list.removeAll(List.of("B"));

		list.retainAll(List.of("A", "C", "D"));

		assertState(list, "A", "C", "D");
	}

}