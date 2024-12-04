package de.atlasmc.test.chat;

import org.junit.jupiter.api.Test;

import de.atlasmc.chat.ChatColor;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class ChatColorTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(ChatColor.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(ChatColor.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(ChatColor.class);
	}

}
