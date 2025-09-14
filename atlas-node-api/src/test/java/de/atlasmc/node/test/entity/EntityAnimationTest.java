package de.atlasmc.node.test.entity;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.entity.Entity.Animation;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class EntityAnimationTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Animation.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Animation.class);
	}

	@Override
	public void testNameMethods() {
		// not request
	}

}
