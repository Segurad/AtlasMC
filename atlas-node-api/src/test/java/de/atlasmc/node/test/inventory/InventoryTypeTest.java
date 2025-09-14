package de.atlasmc.node.test.inventory;

import org.junit.jupiter.api.Test;

import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.test.util.EnumTest;
import de.atlasmc.test.util.EnumTest.EnumTestCases;

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
