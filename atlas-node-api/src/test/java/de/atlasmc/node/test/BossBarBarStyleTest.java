package de.atlasmc.node.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.BossBar.BarStyle;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class BossBarBarStyleTest implements EnumTestCases {
	
	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(BarStyle.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(BarStyle.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
