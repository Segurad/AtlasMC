package de.atlasmc.test;

import org.junit.jupiter.api.Test;

import de.atlasmc.Instrument;
import de.atlastest.util.EnumTest;
import de.atlastest.util.EnumTest.EnumTestCases;

public class InstrumentTest implements EnumTestCases {

	@Test
	@Override
	public void testCacheMethods() {
		EnumTest.testCacheMethods(Instrument.class);
	}

	@Override
	public void testIDMethods() {
		// not required
	}

	@Test
	@Override
	public void testNameMethods() {
		EnumTest.testNameMethods(Instrument.class);
	}

}
