package de.atlasmc.test.io.protocol.play;

import org.junit.jupiter.api.Test;

import de.atlasmc.io.protocol.play.PacketOutGameEvent.GameEventType;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class GameEventTypeTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(GameEventType.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(GameEventType.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
