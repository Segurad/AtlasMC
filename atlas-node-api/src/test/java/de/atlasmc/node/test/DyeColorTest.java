package de.atlasmc.node.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.DyeColor;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class DyeColorTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(DyeColor.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(DyeColor.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(DyeColor.class);
	}

}
