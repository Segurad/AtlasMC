package de.atlasmc.test.inventory.meta;

import org.junit.jupiter.api.Test;

import de.atlasmc.inventory.InventoryType;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class InventoryTypeTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(InventoryType.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		// TODO custom test
	}

	@Override
	public void testNameMethods() {
		// not required
	}
	
	

}
