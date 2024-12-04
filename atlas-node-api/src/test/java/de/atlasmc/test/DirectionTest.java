package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.Direction;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class DirectionTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Direction.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Direction.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
