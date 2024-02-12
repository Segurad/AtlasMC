package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.Sound;
import de.atlastest.util.EnumTest;

public class SoundTest {
	
	@Test
	void testVillagerTypes() throws Exception {
		EnumTest.testCacheAndID(Sound.class);
		EnumTest.testRegistryProtocolEnum(Sound.class, "registry_minecraft_sound_event.json");
	}

}
