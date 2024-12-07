package de.atlasmc.test.sound;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.sound.EnumSound;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class EnumSoundTest implements EnumTestCases {
	
	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(EnumSound.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(EnumSound.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(EnumSound.class);
	}
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testSoundTypes() throws Exception {
		EnumTest.testRegistryProtocolEnum(EnumSound.class, "registry_minecraft_sound_event.json");
	}

}
