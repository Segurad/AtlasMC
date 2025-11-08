package de.atlasmc.node.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.entity.AbstractVillager.VillagerProfession;
import de.atlasmc.test.util.EnumTestUtil;

public class VillagerProfessionTest {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testVillagerTypes() throws Exception {
		EnumTestUtil.testRegistryProtocolEnum(VillagerProfession.class, "/minecraft/registries/registry_minecraft_villager_profession.json");
	}

}
