package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.BossBar.BarColor;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

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
