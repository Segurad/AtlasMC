package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.BossBar.BarStyle;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

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
