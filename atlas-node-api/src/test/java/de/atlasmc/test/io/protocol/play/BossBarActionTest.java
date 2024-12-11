package de.atlasmc.test.io.protocol.play;

import org.junit.jupiter.api.Test;

import de.atlasmc.io.protocol.play.PacketOutBossBar.BossBarAction;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class BossBarActionTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(BossBarAction.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(BossBarAction.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
