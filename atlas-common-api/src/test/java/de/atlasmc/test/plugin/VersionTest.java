package de.atlasmc.test.plugin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.atlasmc.plugin.Version;

public class VersionTest {

	@Test
	public void testParse() {
		Version version = new Version("1.2.3");
		assertEquals(1, version.major);
		assertEquals(2, version.minor);
		assertEquals(3, version.patch);
	}

	@Test
	public void testParsePrefixed() {
		Version version = new Version("v10.20.30");
		assertEquals(10, version.major);
		assertEquals(20, version.minor);
		assertEquals(30, version.patch);
	}

	@Test
	public void testParsePrereleaseAndBuild() {
		final String[] values = {
			"1.0.0-alpha",
			"1.0.0-alpha.1",
			"1.0.0-0.3.7",
			"1.0.0-x.7.z.92",
			"1.0.0+20130313144700",
			"1.0.0-beta+exp.sha.5114f85",
			"v1.0.0-rc.1+build.1"
		};
		for (String value : values) {
			Version version = assertDoesNotThrow(() -> new Version(value), value);
			assertEquals(1, version.major, value);
			assertEquals(0, version.minor, value);
			assertEquals(0, version.patch, value);
		}
	}

	@Test
	public void testParseInvalid() {
		final String[] values = {
			"",
			"1",
			"1.2",
			"1.2.3.4",
			"01.2.3",
			"1.02.3",
			"1.2.03",
			"a.b.c",
			"-1.2.3",
			"V1.2.3",
			" 1.2.3",
			"1.2.3 ",
			"1.2.3-",
			"1.2.3+",
			"1.2.3-01",
			"99999999999.0.0"
		};
		for (String value : values) {
			assertThrows(IllegalArgumentException.class, () -> new Version(value), "\"" + value + "\"");
		}
	}

	@Test
	public void testParseNull() {
		assertThrows(NullPointerException.class, () -> new Version(null));
	}

	@Test
	public void testNegativeComponents() {
		assertThrows(IllegalArgumentException.class, () -> new Version(-1, 0, 0));
		assertThrows(IllegalArgumentException.class, () -> new Version(0, -1, 0));
		assertThrows(IllegalArgumentException.class, () -> new Version(0, 0, -1));
	}

	@Test
	public void testZero() {
		assertEquals(0, Version.ZERO.major);
		assertEquals(0, Version.ZERO.minor);
		assertEquals(0, Version.ZERO.patch);
		assertEquals(Version.ZERO, new Version("0.0.0"));
	}

	@Test
	public void testToString() {
		assertEquals("v1.2.3", new Version(1, 2, 3).toString());
		// parsed versions keep their raw representation
		assertEquals("1.2.3", new Version("1.2.3").toString());
		assertEquals("v1.2.3-beta+build", new Version("v1.2.3-beta+build").toString());
	}

	@Test
	public void testToStringRoundTrip() {
		Version version = new Version(4, 5, 6);
		assertEquals(version, new Version(version.toString()));
	}

	@Test
	public void testEqualsAndHashCode() {
		Version a = new Version(1, 2, 3);
		Version b = new Version("v1.2.3");
		Version c = new Version("1.2.3-beta+build");
		assertEquals(a, b);
		assertEquals(a, c);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a.hashCode(), c.hashCode());
		assertNotEquals(a, new Version(1, 2, 4));
		assertNotEquals(a, new Version(1, 3, 3));
		assertNotEquals(a, new Version(2, 2, 3));
		assertNotEquals(a, null);
		assertNotEquals(a, "1.2.3");
	}

	@Test
	public void testCompareEqual() {
		assertEquals(0, new Version(1, 2, 3).compareTo(new Version("1.2.3")));
		assertEquals(0, Version.ZERO.compareTo(Version.ZERO));
	}

	@Test
	public void testCompareMajor() {
		assertCompare(new Version(1, 9, 9), new Version(2, 0, 0));
	}

	@Test
	public void testCompareMinor() {
		assertCompare(new Version(1, 1, 9), new Version(1, 2, 0));
	}

	@Test
	public void testComparePatch() {
		assertCompare(new Version(1, 2, 3), new Version(1, 2, 4));
	}

	@Test
	public void testCompareNumeric() {
		// components must be compared numerically not lexicographically
		assertCompare(new Version("1.2.3"), new Version("1.10.0"));
		assertCompare(new Version("9.0.0"), new Version("10.0.0"));
	}

	/**
	 * Asserts that lower < higher in both directions
	 */
	private static void assertCompare(Version lower, Version higher) {
		assertEquals(-1, lower.compareTo(higher), lower + " < " + higher);
		assertEquals(1, higher.compareTo(lower), higher + " > " + lower);
	}

}
