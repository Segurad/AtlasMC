package de.atlasmc.node.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.entity.Entity.Pose;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class EntityPoseTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Pose.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Pose.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
