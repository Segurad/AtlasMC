package de.atlasmc.test;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.Sound;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class SoundTest implements EnumTestCases {
	
	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Sound.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Sound.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Sound.class);
	}
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testSoundTypes() throws Exception {
		EnumTest.testRegistryProtocolEnum(Sound.class, "registry_minecraft_sound_event.json");
	}

}
