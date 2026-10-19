package de.atlasmc.test.plugin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.atlasmc.plugin.Dependency;
import de.atlasmc.plugin.Dependency.MatchOperation;
import de.atlasmc.plugin.Dependency.Order;
import de.atlasmc.plugin.Dependency.Type;
import de.atlasmc.plugin.Version;

public class DependencyTest {

	private static final Version V1 = new Version(1, 0, 0);
	private static final Version V2 = new Version(2, 0, 0);
	private static final Version V3 = new Version(3, 0, 0);

	// --- constructors ---

	@Test
	public void testDefaults() {
		Dependency dependency = new Dependency("test");
		assertEquals("test", dependency.name);
		assertEquals(Type.REQUIRED, dependency.type);
		assertEquals(Order.AFTER, dependency.order);
		assertEquals(MatchOperation.ANY, dependency.operation);
		assertNull(dependency.version);
		assertNull(dependency.toVersion);
	}

	@Test
	public void testOperationConstructor() {
		Dependency dependency = new Dependency("test", Type.OPTIONAL, Order.BEFORE, MatchOperation.GREATER_EQUAL, V2);
		assertEquals(Type.OPTIONAL, dependency.type);
		assertEquals(Order.BEFORE, dependency.order);
		assertEquals(MatchOperation.GREATER_EQUAL, dependency.operation);
		assertEquals(V2, dependency.version);
		assertNull(dependency.toVersion);
	}

	@Test
	public void testRangeConstructor() {
		Dependency dependency = new Dependency("test", Type.REQUIRED, V1, V3);
		assertEquals(MatchOperation.RANGE, dependency.operation);
		assertEquals(Order.AFTER, dependency.order);
		assertEquals(V1, dependency.version);
		assertEquals(V3, dependency.toVersion);
	}

	@Test
	public void testAnyIgnoresVersion() {
		Dependency dependency = new Dependency("test", Type.REQUIRED, MatchOperation.ANY, V1);
		assertNull(dependency.version);
		assertNull(dependency.toVersion);
	}

	@Test
	public void testConstructorNulls() {
		assertThrows(NullPointerException.class, () -> new Dependency(null));
		assertThrows(NullPointerException.class, () -> new Dependency("test", null));
		assertThrows(NullPointerException.class, () -> new Dependency("test", Type.REQUIRED, (Order) null));
		assertThrows(NullPointerException.class, () -> new Dependency("test", Type.REQUIRED, MatchOperation.EQUAL, null));
		assertThrows(NullPointerException.class, () -> new Dependency("test", Type.REQUIRED, V1, null));
		assertThrows(NullPointerException.class, () -> new Dependency("test", Type.REQUIRED, (Version) null, V1));
	}

	// --- matches ---

	@Test
	public void testMatchesAny() {
		Dependency dependency = new Dependency("test");
		assertTrue(dependency.matches(Version.ZERO));
		assertTrue(dependency.matches(V1));
		assertTrue(dependency.matches(new Version(999, 999, 999)));
	}

	@Test
	public void testMatchesNull() {
		assertThrows(NullPointerException.class, () -> new Dependency("test").matches(null));
	}

	@Test
	public void testMatchesName() {
		Dependency dependency = new Dependency("test");
		assertTrue(dependency.matches("test", V1));
		assertFalse(dependency.matches("other", V1));
		assertFalse(dependency.matches("Test", V1));
	}

	@Test
	public void testMatchesLower() {
		assertMatches(MatchOperation.LOWER, true, false, false);
	}

	@Test
	public void testMatchesLowerEqual() {
		assertMatches(MatchOperation.LOWER_EQUAL, true, true, false);
	}

	@Test
	public void testMatchesEqual() {
		assertMatches(MatchOperation.EQUAL, false, true, false);
	}

	@Test
	public void testMatchesGreaterEqual() {
		assertMatches(MatchOperation.GREATER_EQUAL, false, true, true);
	}

	@Test
	public void testMatchesGreater() {
		assertMatches(MatchOperation.GREATER, false, false, true);
	}

	@Test
	public void testMatchesPatchLevel() {
		Dependency dependency = new Dependency("test", Type.REQUIRED, MatchOperation.GREATER_EQUAL, new Version(1, 2, 3));
		assertFalse(dependency.matches(new Version(1, 2, 2)));
		assertTrue(dependency.matches(new Version(1, 2, 3)));
		assertTrue(dependency.matches(new Version(1, 2, 4)));
	}

	@Test
	public void testMatchesRange() {
		Dependency dependency = new Dependency("test", Type.REQUIRED, V1, V3);
		assertFalse(dependency.matches(Version.ZERO));
		assertFalse(dependency.matches(new Version(0, 9, 9)));
		assertTrue(dependency.matches(V1)); // inclusive lower bound
		assertTrue(dependency.matches(V2));
		assertTrue(dependency.matches(new Version(2, 9, 9)));
		assertTrue(dependency.matches(V3)); // inclusive upper bound
		assertFalse(dependency.matches(new Version(3, 0, 1)));
	}

	/**
	 * Tests a dependency with the given operation against V2 using V1, V2 and V3
	 */
	private static void assertMatches(MatchOperation operation, boolean lower, boolean equal, boolean greater) {
		Dependency dependency = new Dependency("test", Type.REQUIRED, operation, V2);
		assertEquals(lower, dependency.matches(V1), operation + " " + V2 + " with " + V1);
		assertEquals(equal, dependency.matches(V2), operation + " " + V2 + " with " + V2);
		assertEquals(greater, dependency.matches(V3), operation + " " + V2 + " with " + V3);
	}

	// --- enums ---

	@Test
	public void testOrderIdentifier() {
		for (Order order : Order.values())
			assertEquals(order, Order.getByIdentifier(order.getIdentifier()));
		assertEquals(Order.getDefault(), Order.getByIdentifier(null));
		assertEquals(Order.getDefault(), Order.getByIdentifier("#"));
		assertEquals(Order.AFTER, Order.getDefault());
	}

	@Test
	public void testTypeIdentifier() {
		for (Type type : Type.values())
			assertEquals(type, Type.getByIdentifier(type.getIdentifier()));
		assertEquals(Type.getDefault(), Type.getByIdentifier(null));
		assertEquals(Type.getDefault(), Type.getByIdentifier("#"));
		assertEquals(Type.REQUIRED, Type.getDefault());
	}

	@Test
	public void testMatchOperationIdentifier() {
		for (MatchOperation operation : MatchOperation.values()) {
			if (operation == MatchOperation.RANGE || operation == MatchOperation.ANY)
				continue;
			assertEquals(operation, MatchOperation.getByIdentifier(operation.getIdentifier()), operation.name());
		}
		assertEquals(">", MatchOperation.GREATER.getIdentifier());
		assertEquals(MatchOperation.ANY, MatchOperation.getByIdentifier(null));
		assertEquals(MatchOperation.ANY, MatchOperation.getByIdentifier("~="));
		assertEquals(MatchOperation.ANY, MatchOperation.getDefault());
	}

	// --- parsing ---

	@Test
	public void testParseName() {
		assertDependency(Dependency.of("test-plugin_2"), "test-plugin_2", Order.AFTER, Type.REQUIRED, MatchOperation.ANY, null, null);
	}

	@Test
	public void testParseOrderAndType() {
		assertDependency(Dependency.of("-? test"), "test", Order.BEFORE, Type.OPTIONAL, MatchOperation.ANY, null, null);
		assertDependency(Dependency.of("~! test"), "test", Order.WHATEVER, Type.INCOMPATIPLE, MatchOperation.ANY, null, null);
		assertDependency(Dependency.of("+* test"), "test", Order.AFTER, Type.REQUIRED, MatchOperation.ANY, null, null);
		assertDependency(Dependency.of("?test"), "test", Order.AFTER, Type.OPTIONAL, MatchOperation.ANY, null, null);
		assertDependency(Dependency.of("- test"), "test", Order.BEFORE, Type.REQUIRED, MatchOperation.ANY, null, null);
	}

	@Test
	public void testParseOperation() {
		assertDependency(Dependency.of("test < 2.0.0"), "test", Order.AFTER, Type.REQUIRED, MatchOperation.LOWER, V2, null);
		assertDependency(Dependency.of("test <= 2.0.0"), "test", Order.AFTER, Type.REQUIRED, MatchOperation.LOWER_EQUAL, V2, null);
		assertDependency(Dependency.of("test == v2.0.0"), "test", Order.AFTER, Type.REQUIRED, MatchOperation.EQUAL, V2, null);
		assertDependency(Dependency.of("test >= 2.0.0"), "test", Order.AFTER, Type.REQUIRED, MatchOperation.GREATER_EQUAL, V2, null);
		assertDependency(Dependency.of("test > 2.0.0"), "test", Order.AFTER, Type.REQUIRED, MatchOperation.GREATER, V2, null);
		assertDependency(Dependency.of("-? test >= 2.0.0-beta+build"), "test", Order.BEFORE, Type.OPTIONAL, MatchOperation.GREATER_EQUAL, V2, null);
	}

	@Test
	public void testParseRange() {
		assertDependency(Dependency.of("test 1.0.0 - 3.0.0"), "test", Order.AFTER, Type.REQUIRED, MatchOperation.RANGE, V1, V3);
		assertDependency(Dependency.of("~? test v1.0.0 - v3.0.0"), "test", Order.WHATEVER, Type.OPTIONAL, MatchOperation.RANGE, V1, V3);
	}

	@Test
	public void testParseNull() {
		assertThrows(NullPointerException.class, () -> Dependency.of(null));
	}

	@Test
	public void testParseInvalid() {
		final String[] values = {
			"",
			" ",
			"+",
			"+*",
			"test plugin",
			"test >",
			"test > ",
			"test >2.0.0",
			"test => 2.0.0",
			"test >= 2.0",
			"test >= not.a.version",
			"test 1.0.0 -",
			"test 1.0.0 - 3.0",
			"test 1.0.0-3.0.0",
			"test >= 1.0.0 - 3.0.0",
			"*+ test",
			"test ",
		};
		for (String value : values) {
			assertThrows(IllegalArgumentException.class, () -> Dependency.of(value), "\"" + value + "\"");
		}
	}

	@Test
	public void testToStringRoundTrip() {
		final Dependency[] dependencies = {
			new Dependency("test"),
			new Dependency("test", Type.OPTIONAL, Order.BEFORE),
			new Dependency("test", Type.INCOMPATIPLE, Order.WHATEVER),
			new Dependency("test", Type.REQUIRED, MatchOperation.LOWER, V2),
			new Dependency("test", Type.REQUIRED, MatchOperation.LOWER_EQUAL, V2),
			new Dependency("test", Type.REQUIRED, MatchOperation.EQUAL, V2),
			new Dependency("test", Type.REQUIRED, MatchOperation.GREATER_EQUAL, V2),
			new Dependency("test", Type.REQUIRED, MatchOperation.GREATER, V2),
			new Dependency("test", Type.OPTIONAL, Order.BEFORE, V1, V3),
		};
		for (Dependency expected : dependencies) {
			String value = expected.toString();
			Dependency actual = assertDoesNotThrow(() -> Dependency.of(value), value);
			assertDependency(actual, expected.name, expected.order, expected.type, expected.operation, expected.version, expected.toVersion);
		}
	}

	private static void assertDependency(Dependency dependency, String name, Order order, Type type, MatchOperation operation, Version version, Version toVersion) {
		String msg = dependency.toString();
		assertEquals(name, dependency.name, msg);
		assertEquals(order, dependency.order, msg);
		assertEquals(type, dependency.type, msg);
		assertEquals(operation, dependency.operation, msg);
		assertEquals(version, dependency.version, msg);
		assertEquals(toVersion, dependency.toVersion, msg);
	}

}
