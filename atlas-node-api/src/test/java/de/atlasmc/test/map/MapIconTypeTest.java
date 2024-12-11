package de.atlasmc.test.map;

import de.atlasmc.map.MapIcon.IconType;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class MapIconTypeTest implements EnumTestCases {

	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(IconType.class);
	}

	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(IconType.class);
	}

	@Override
	public void testNameMethods() {
		// not required
	}

}
