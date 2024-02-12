package de.atlasmc.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlastest.util.EnumTest;

public class VillagerProfessionTest {
	
	@Test
	void testVillagerTypes() throws Exception {
		EnumTest.testCacheAndID(VillagerProfession.class);
		EnumTest.testRegistryProtocolEnum(VillagerProfession.class, "registry_minecraft_villager_profession.json");
	}

}
