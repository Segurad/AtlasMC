package de.atlasmc.schematic;

import java.util.Arrays;

public class Section implements SchematicSection {

	private short[] mappings;
	
	public Section(short data) {
		mappings = new short[4096];
	}

	@Override
	public short[] getMappings() {
		return Arrays.copyOf(mappings, 4096);
	}

	@Override
	public short[] getMappings(short[] buffer) {
		return getMappings(buffer, 0);
	}
	
	@Override
	public short[] getMappings(short[] buffer, int offset) {
		for (int i = 0; i < 4096; i++) {
			buffer[i+offset] = mappings[i];
		}
		return buffer;
	}

	@Override
	public void setMappings(short[] mappings) {
		for (int i = 0; i < 4096; i++) {
			this.mappings[i] = mappings[i];
		}
	}

	@Override
	public short getValue(int x, int y, int z) {
		return 0;
	}

	@Override
	public short setValue(short value, int x, int y, int z) {
		return 0;
	}

}
