package de.atlasmc.node.test.entity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.entity.Frog.Variant;
import de.atlasmc.test.util.EnumTestUtil;

public class FrogVariantTest {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testFrogVariant() throws Exception {
		EnumTestUtil.testRegistryProtocolEnum(Variant.class, "/minecraft/registries/registry_minecraft_frog_variant.json");
	}

}
