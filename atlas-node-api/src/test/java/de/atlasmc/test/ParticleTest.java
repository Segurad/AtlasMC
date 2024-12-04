package de.atlasmc.test;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.Particle;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class ParticleTest implements EnumTestCases {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testParticles() throws Exception {
		EnumTest.testRegistryProtocolEnum(Particle.class, "registry_minecraft_particle_type.json");
	}

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Particle.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Particle.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Particle.class);
	}

}
