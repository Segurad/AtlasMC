package de.atlasmc.test;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.world.particle.ParticleType;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class ParticleTest implements EnumTestCases {
	
	@Test
	@Order(Integer.MAX_VALUE)
	void testParticles() throws Exception {
		EnumTest.testRegistryProtocolEnum(ParticleType.class, "/minecraft/registries/registry_minecraft_particle_type.json");
	}

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(ParticleType.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(ParticleType.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(ParticleType.class);
	}

}
