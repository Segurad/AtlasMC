package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.DyeColor;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

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
