package de.atlasmc.node.test.world;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.world.WorldEvent;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
