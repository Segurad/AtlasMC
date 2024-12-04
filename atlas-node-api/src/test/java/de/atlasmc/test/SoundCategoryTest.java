package de.atlasmc.test;

import de.atlasmc.SoundCategory;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

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
