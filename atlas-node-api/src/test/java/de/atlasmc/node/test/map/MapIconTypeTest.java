package de.atlasmc.node.test.map;

import de.atlasmc.node.map.MapIcon.IconType;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
