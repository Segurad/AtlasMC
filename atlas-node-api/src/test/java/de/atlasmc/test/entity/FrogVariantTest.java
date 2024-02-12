package de.atlasmc.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Frog.Variant;
import de.atlastest.util.EnumTest;

public class FrogVariantTest {
	
	@Test
	void testFrogVariant() throws Exception {
		EnumTest.testRegistryProtocolEnum(Variant.class, "registry_minecraft_frog_variant.json");
	}

}
