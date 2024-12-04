package de.atlasmc.test.world;

import org.junit.jupiter.api.Test;

import de.atlasmc.world.WorldEvent;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class WorldEventTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(WorldEvent.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(WorldEvent.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
