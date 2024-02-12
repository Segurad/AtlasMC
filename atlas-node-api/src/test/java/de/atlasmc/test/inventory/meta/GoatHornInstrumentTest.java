package de.atlasmc.test.inventory.meta;

import org.junit.jupiter.api.Test;

import de.atlasmc.inventory.meta.GoatHornMeta.Instrument;
import de.atlastest.util.EnumTest;

public class GoatHornInstrumentTest {

	@Test
	void testInstrument() throws Exception {
		EnumTest.testRegistryProtocolEnum(Instrument.class, "registry_minecraft_instrument.json");
	}
	
}
