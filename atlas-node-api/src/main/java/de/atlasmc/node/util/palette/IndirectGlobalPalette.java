package de.atlasmc.node.util.palette;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.util.Collection;

import de.atlasmc.node.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class IndirectGlobalPalette<E> extends AbstractIndirectPalette<E> {

	private int globalMaxValue;
	private int globalBits;
	
	public IndirectGlobalPalette(int minBitsPerEntry, int capacity, int maxBitsPerEntry, GlobalValueProvider<E> provider) {
		super(minBitsPerEntry, capacity, maxBitsPerEntry, provider);
		globalMaxValue = MathUtil.createBitMask(values.getBitsPerValue());
		globalBits = values.getBitsPerValue();
	}
	
	public IndirectGlobalPalette(int minBitsPerEntry, int maxBitsPerEntry, Palette<E> palette) {
		super(minBitsPerEntry, maxBitsPerEntry, palette);
	}
	
	@Override
	protected void setEntries(Collection<? extends PaletteEntry<E>> entries) {
		int maxValue = 0;
		for (PaletteEntry<E> entry : entries) {
			E rawEntry = entry.getEntry();
			Entry<E> newEntry = addEntry(rawEntry, entry.value());
			newEntry.paletteValue = highestEntry = lowestEntry++;
			if (newEntry.globalValue > maxValue)
				maxValue = newEntry.globalValue;
		}
		globalBits = values.getBitsPerValue();
		globalMaxValue = MathUtil.createBitMask(globalBits);
		ensureBits(maxValue);	
	}
	
	private void ensureBits(int value) {
		if (value > globalMaxValue) {
			globalBits = MathUtil.getRequiredBitCount(value);
			globalMaxValue = MathUtil.createBitMask(globalBits);
		}
	}
	
	@Override
	protected Entry<E> addOrReplaceEntry(E entry, int index, boolean checkIndex) {
		Entry<E> pEntry = super.addOrReplaceEntry(entry, index, checkIndex);
		if (pEntry == null)
			return null;
		ensureBits(pEntry.globalValue);
		return pEntry;
	}
	
	@Override
	protected int updateValue(Entry<E> pEntry, E entry) {
		int newValue = globalProvider.value(entry);
		ensureBits(newValue);
		return pEntry.paletteValue;
	}

	@Override
	public void write(ByteBuf buf) {
		buf.writeByte(globalBits);
		// write data
		final long[] values = this.values.array();
		int globalValuesPerLong = 64 / globalBits;
		int globalArraySize = MathUtil.upper(this.values.getCapacity() / (double) globalValuesPerLong);
		writeVarInt(globalArraySize, buf);
		final int bitsPerValue = this.values.getBitsPerValue();
		final int mask = MathUtil.createBitMask(bitsPerValue);
		long outValue = 0;
		int outRestBits = 64;
		int outBitsToShift = 0;
		for (long l : values) {
			int restBits = 64;
			while (restBits >= bitsPerValue) {
				// get raw value
				int rawValue = (int) (l & mask);
				l >>= bitsPerValue;
				restBits -= bitsPerValue;
				// handle global value
				if (outRestBits < globalBits) { // write full value
					writeVarLong(outValue, buf);
					outValue = 0;
					outRestBits = 64;
					outBitsToShift = 0;
				}
				int value = valueToEntry.get(rawValue).globalValue; // get global value
				if (value != 0) { // set value to long
					outValue |= (value & globalMaxValue) << outBitsToShift;
				}
				outBitsToShift += globalBits;
			}
		}
		if (outRestBits != 64) { // write rest data if present
			writeVarLong(outValue, buf);
		}
	}

	@Override
	public long getSerializedSize() {
		int size = 1;
		int globalValuesPerLong = 64 / globalBits;
		int globalArraySize = MathUtil.upper(this.values.getCapacity() / (double) globalValuesPerLong);
		size += getVarIntLength(globalArraySize);
		size += globalArraySize * 9; // use max number of bytes to represent long values as varlong
		return size;
	}

}
