package de.atlasmc.node.test.sound;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.test.util.EnumTestUtil;

public class EnumSoundTest {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testSoundTypes() throws Exception {
		EnumTestUtil.testRegistryProtocolEnum(EnumSound.class, "/minecraft/registries/registry_minecraft_sound_event.json");
	}

}
