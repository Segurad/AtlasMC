package de.atlasmc.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Cat.Type;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class CatTypeTest implements EnumTestCases {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testCatTypes() throws Exception {
		EnumTest.testRegistryProtocolEnum(Type.class, "registry_minecraft_cat_variant.json");
	}

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Type.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Type.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Type.class);
	}

}
