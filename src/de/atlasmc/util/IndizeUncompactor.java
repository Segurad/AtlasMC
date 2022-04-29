package de.atlasmc.util;

import java.util.function.LongConsumer;

public class IndizeUncompactor implements LongConsumer {
	
	private short[] indizes;
	private byte indizesPerLong;
	private byte bits;
	private short mask;
	private short index;
	
	public IndizeUncompactor(short[] indizes, int bits) {
		setTarget(indizes, bits);
	}
	
	public void setTarget(short[] indizes, int bits) {
		this.indizes = indizes;
		this.index = 0;
		this.indizesPerLong = (byte) (64 / bits);
		this.bits = (byte) bits;
		this.mask = 0xF;
		if (bits > 4)
			for (int i = 4; i < bits; i++)
				mask = (short) (mask << 1 | 0x1);
	}

	@Override
	public void accept(long value) {
		for (int i = 0; i < indizesPerLong && index < indizes.length; i++, index++) {
			indizes[index] = (short) (value & mask);
			value >>= bits;
		}
	}

}
