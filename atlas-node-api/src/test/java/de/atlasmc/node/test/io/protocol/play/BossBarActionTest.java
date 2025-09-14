package de.atlasmc.node.test.io.protocol.play;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.io.protocol.play.PacketOutBossBar.BossBarAction;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
