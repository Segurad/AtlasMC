package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.Gamemode;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class GamemodeTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Gamemode.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Gamemode.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
