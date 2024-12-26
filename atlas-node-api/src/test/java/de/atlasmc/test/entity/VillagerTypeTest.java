package de.atlasmc.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Villager.VillagerType;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class VillagerTypeTest implements EnumTestCases {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testVillagerTypes() throws Exception {
		EnumTest.testRegistryProtocolEnum(VillagerType.class, "/minecraft/registries/registry_minecraft_villager_type.json");
	}

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(VillagerType.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(VillagerType.class);
	}

	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(VillagerType.class);
	}

}
