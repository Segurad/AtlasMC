package de.atlasmc.node.test.event.player;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
