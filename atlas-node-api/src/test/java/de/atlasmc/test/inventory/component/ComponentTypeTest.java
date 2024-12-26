package de.atlasmc.test.inventory.component;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.inventory.component.ComponentType;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class ComponentTypeTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(ComponentType.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(ComponentType.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(ComponentType.class);
	}
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testComponentTypes() throws Exception {
		EnumTest.testRegistryProtocolEnum(ComponentType.class, "/minecraft/registries/registry_minecraft_data_component_type.json");
	}

}
