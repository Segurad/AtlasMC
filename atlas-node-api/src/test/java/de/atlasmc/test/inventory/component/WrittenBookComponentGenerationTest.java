package de.atlasmc.test.inventory.component;

import org.junit.jupiter.api.Test;

import de.atlasmc.inventory.component.WrittenBookContentComponent.Generation;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class WrittenBookComponentGenerationTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Generation.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Generation.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
