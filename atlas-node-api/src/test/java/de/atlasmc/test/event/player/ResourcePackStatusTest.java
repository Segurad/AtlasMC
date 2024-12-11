package de.atlasmc.test.event.player;

import org.junit.jupiter.api.Test;

import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class ResourcePackStatusTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(ResourcePackStatus.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(ResourcePackStatus.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
