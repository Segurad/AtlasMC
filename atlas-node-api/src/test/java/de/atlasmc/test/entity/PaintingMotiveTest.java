package de.atlasmc.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Painting.Motive;
import de.atlastest.util.EnumTest;

public class PaintingMotiveTest {
	
	@Test
	void testMotiveTypes() throws Exception {
		EnumTest.testRegistryProtocolEnum(Motive.class, "registry_minecraft_painting_variant.json");
	}

}
