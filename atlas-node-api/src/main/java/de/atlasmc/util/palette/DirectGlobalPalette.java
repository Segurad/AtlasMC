package de.atlasmc.util.palette;

import java.util.Collection;

import static de.atlasmc.io.protocol.ProtocolUtil.*;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class DirectGlobalPalette<E> extends AbstractPalette<E> {

	private int currentMaxValue;
	
	public DirectGlobalPalette(int minBitsPerValue, int capacity, int maxBitsPerValue, GlobalValueProvider<E> provider) {
		super(minBitsPerValue, capacity, maxBitsPerValue, provider);
		currentMaxValue = MathUtil.createBitMask(values.getBitsPerValue());
	}
	
	public DirectGlobalPalette(int minBitsPerValue, int maxBitsPerValue, Palette<E> palette) {
		super(minBitsPerValue, maxBitsPerValue, palette);
	}
	
	@Override
	protected void setEntries(Collection<? extends PaletteEntry<E>> entries) {
		int maxValue = 0;
		for (PaletteEntry<E> entry : entries) {
			E rawEntry = entry.getEntry();
			Entry<E> newEntry = addEntry(rawEntry, entry.value());
			newEntry.paletteValue = globalProvider.value(rawEntry);
			if (newEntry.paletteValue > maxValue)
				maxValue = newEntry.paletteValue;
		}
		currentMaxValue = MathUtil.createBitMask(values.getBitsPerValue());
		ensureBits(maxValue);	
	}
	
	private void ensureBits(int value) {
		if (value > currentMaxValue) {
			values.resize(MathUtil.getRequiredBitCount(value));
			currentMaxValue = MathUtil.createBitMask(values.getBitsPerValue());
		}
	}
	
	@Override
	protected Entry<E> addOrReplaceEntry(E entry, int index, boolean checkIndex) {
		Entry<E> pEntry = super.addOrReplaceEntry(entry, index, checkIndex);
		if (pEntry != null)
			return pEntry;
		int value = globalProvider.value(entry);
		pEntry = addEntry(entry, value);
		ensureBits(value);
		return pEntry;
	}
	
	@Override
	protected int updateValue(Entry<E> pEntry, E entry) {
		int newValue = globalProvider.value(entry);
		ensureBits(newValue);
		return newValue;
	}

	@Override
	public void write(ByteBuf buf) {
		buf.writeByte(values.getBitsPerValue());
		// write data
		final long[] values = this.values.array();
		writeVarInt(values.length, buf);
		for (long value : values) {
			writeVarLong(value, buf);
		}
	}
	
	@Override
	public long getSerializedSize() {
		int size = 1;
		long[] values = this.values.array();
		size += getVarIntLength(values.length);
		size += values.length * 9; // use max number of bytes to represent long values as varlong
		return size;
	}
	
}
