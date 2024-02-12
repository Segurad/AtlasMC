package de.atlasmc.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Villager.VillagerType;
import de.atlastest.util.EnumTest;

public class VillagerTypeTest {
	
	@Test
	void testVillagerTypes() throws Exception {
		EnumTest.testCacheAndID(VillagerType.class);
		EnumTest.testRegistryProtocolEnum(VillagerType.class, "registry_minecraft_villager_type.json");
	}

}
