package de.atlasmc.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Frog.Variant;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class FrogVariantTest implements EnumTestCases {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testFrogVariant() throws Exception {
		EnumTest.testRegistryProtocolEnum(Variant.class, "registry_minecraft_frog_variant.json");
	}

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Variant.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Variant.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Variant.class);
	}

}
