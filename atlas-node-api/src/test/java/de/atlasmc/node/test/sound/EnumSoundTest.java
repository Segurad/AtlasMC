package de.atlasmc.node.test.sound;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
		EnumTest.testRegistryProtocolEnum(EnumSound.class, "/minecraft/registries/registry_minecraft_sound_event.json");
	}

}
