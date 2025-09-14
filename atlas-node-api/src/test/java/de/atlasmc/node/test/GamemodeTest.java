package de.atlasmc.node.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.Gamemode;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
