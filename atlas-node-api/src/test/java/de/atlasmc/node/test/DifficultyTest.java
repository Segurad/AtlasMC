package de.atlasmc.node.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.Difficulty;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class DifficultyTest implements EnumTestCases {
	
	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Difficulty.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Difficulty.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
