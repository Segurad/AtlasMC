package de.atlasmc.node.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.Direction;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
