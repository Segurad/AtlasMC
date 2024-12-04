package de.atlasmc.test.inventory.meta;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.atlasmc.inventory.meta.GoatHornMeta.Instrument;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class GoatHornInstrumentTest implements EnumTestCases {

	@Test
	@Order(Integer.MAX_VALUE)
	void testInstrument() throws Exception {
		EnumTest.testRegistryProtocolEnum(Instrument.class, "registry_minecraft_instrument.json");
	}

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Instrument.class);
	}

	@Test
	@Override
	public void testIDMethods() {
		EnumTest.testIDMethods(Instrument.class);
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Instrument.class);
	}
	
}
