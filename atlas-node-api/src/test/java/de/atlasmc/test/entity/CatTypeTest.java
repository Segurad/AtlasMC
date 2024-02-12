package de.atlasmc.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Cat.Type;
import de.atlastest.util.EnumTest;

public class CatTypeTest {
	
	@Test
	void testCatTypes() throws Exception {
		EnumTest.testCacheAndID(Type.class);
		EnumTest.testRegistryProtocolEnum(Type.class, "registry_minecraft_cat_variant.json");
	}

}
