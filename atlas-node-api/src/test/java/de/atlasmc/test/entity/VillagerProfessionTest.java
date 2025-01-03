package de.atlasmc.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class VillagerProfessionTest implements EnumTestCases {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testVillagerTypes() throws Exception {
		EnumTest.testRegistryProtocolEnum(VillagerProfession.class, "/minecraft/registries/registry_minecraft_villager_profession.json");
	}

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(VillagerProfession.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(VillagerProfession.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(VillagerProfession.class);
	}

}
