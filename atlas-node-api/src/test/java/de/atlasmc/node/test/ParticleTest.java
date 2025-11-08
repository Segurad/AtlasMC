package de.atlasmc.node.test;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.world.particle.ParticleType;
import de.atlasmc.test.util.EnumTestUtil;

public class ParticleTest {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testParticles() throws Exception {
		EnumTestUtil.testRegistryProtocolEnum(ParticleType.class, "/minecraft/registries/registry_minecraft_particle_type.json");
	}

}
