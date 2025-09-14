package de.atlasmc.node.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.BossBar.BarColor;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class BossBarBarColorTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(BarColor.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(BarColor.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
