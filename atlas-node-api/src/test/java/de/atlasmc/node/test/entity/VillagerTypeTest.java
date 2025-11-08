package de.atlasmc.node.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.entity.AbstractVillager.VillagerType;
import de.atlasmc.test.util.EnumTestUtil;

public class VillagerTypeTest {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testVillagerTypes() throws Exception {
		EnumTestUtil.testRegistryProtocolEnum(VillagerType.class, "/minecraft/registries/registry_minecraft_villager_type.json");
	}

}
