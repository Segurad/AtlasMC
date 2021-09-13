package de.atlascore.world.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CoreAnvilRegionHeader {
	
	private final byte[] sectorCounts;
	private final int[] offsets;
	private final int[] timestamps;
	
	public CoreAnvilRegionHeader() {
		sectorCounts = new byte[1024];
		offsets = new int[1024];
		timestamps = new int[1024];
	}
	
	public void loadHeader(InputStream input) throws IOException {
		for (int i = 0; i < 4096; i++) { // read location
			offsets[i] = input.read() << 8 + input.read() << 8 + input.read();
			sectorCounts[i] = (byte) input.read();
		}
		for (int i = 0; i < 4096; i++) { // read timestamps
			timestamps[i] = input.read() << 8 + input.read() << 8 + input.read() << 8 + input.read();
		}
	}
	
	public void writeHeader(OutputStream output) throws IOException {
		for (int i = 0; i < 4096; i++) {
			int offset = offsets[i];
			output.write(offset >> 16);
			output.write(offset >> 8);
			output.write(offset);
			output.write(sectorCounts[i]);
		}
		for (int timestamp : timestamps) {
			output.write(timestamp >> 24);
			output.write(timestamp >> 16);
			output.write(timestamp >> 8);
			output.write(timestamp);
		}
	}

}
