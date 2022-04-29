package de.atlasmc.util;

import java.util.function.LongSupplier;

import de.atlasmc.world.ChunkSection;

/**
 * Implementation of {@link LongSupplier} for compacting {@link ChunkSection} palette index arrays to longs
 */
public class IndizeCompactor implements LongSupplier {

	private short[] indizes;
	private byte bits;
	private byte indizesPerLong;
	private short index;
	private short numberOfLongs;
	
	public IndizeCompactor(short[] indizes, int paletteSize) {
		setData(indizes, paletteSize);
	}
	
	public void setData(short[] indizes, int paletteSize) {
		this.index = (short) (indizes != null ? indizes.length-1 : 0);
		this.indizes = indizes;
		this.bits = (byte) MathUtil.getBitsPerBlock(paletteSize);
		this.indizesPerLong = (byte) (64 / bits);
		this.numberOfLongs = (short) ((4096*bits)>>6);
	}
	
	@Override
	public long getAsLong() {
		if (index >= 4096)
			throw new IllegalStateException("End of compacted indizes reached!");
		long value = 0;
		for (int i = indizesPerLong-1; i >= 0; i--) {
			if (index + i >= 4096)
				continue;
			value <<= bits; // shift to create space for next index 
			value |= indizes[index+i]; // add next index
		}
		index += indizesPerLong;
		return value;
	}

	public int numberOfLongs() {
		return numberOfLongs;
	}
	
}