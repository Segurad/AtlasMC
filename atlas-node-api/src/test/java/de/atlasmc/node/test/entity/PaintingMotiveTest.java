package de.atlasmc.node.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.entity.Painting.Motive;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class PaintingMotiveTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Motive.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Motive.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Motive.class);
	}

}
