package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.Particle;
import de.atlastest.util.EnumTest;

public class ParticleTest {
	
	@Test
	void testParticles() throws Exception {
		EnumTest.testCacheAndID(Particle.class);
		EnumTest.testRegistryProtocolEnum(Particle.class, "registry_minecraft_particle_type.json");
	}

}
