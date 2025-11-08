package de.atlasmc.node.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.entity.Cat.Type;
import de.atlasmc.test.util.EnumTestUtil;

public class CatTypeTest {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testCatTypes() throws Exception {
		EnumTestUtil.testRegistryProtocolEnum(Type.class, "/minecraft/registries/registry_minecraft_cat_variant.json");
	}
	
}
