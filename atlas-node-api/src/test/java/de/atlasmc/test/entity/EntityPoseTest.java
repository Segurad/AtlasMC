package de.atlasmc.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.entity.Entity.Pose;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

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
