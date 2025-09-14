package de.atlasmc.node.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.Axis;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class AxisTest implements EnumTestCases {
	
	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Axis.class);
	}

	@Override
	public void testIDMethods() {
		// not required
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Axis.class);
	}

}
