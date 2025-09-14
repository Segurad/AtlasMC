package de.atlasmc.node.test.sound;

import de.atlasmc.node.SoundCategory;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

public class SoundCategoryTest implements EnumTestCases {

	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(SoundCategory.class);
	}

	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(SoundCategory.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
