package de.atlasmc.util.palette;

import java.util.Collection;
import de.atlasmc.util.VariableValueArray;
import io.netty.buffer.ByteBuf;

public class AdaptivePalette<E> implements Palette<E> {
	
	private Palette<E> palette;
	private final int minBitsPerEntry; // used when resizing down as lower bound
	private final int globalBitThreshold; // used to switch to global palette when growing
	
	public AdaptivePalette(int minBitsPerEntry, int capacity, GlobalValueProvider<E> globalPalette, int globalThreshold) {
		this(minBitsPerEntry, capacity, globalPalette, globalThreshold, null);
	}
	
	public AdaptivePalette(int minBitsPerEntry, int capacity, GlobalValueProvider<E> globalPalette, int globalBitThreshold, E entry) {
		this.minBitsPerEntry = minBitsPerEntry;
		this.globalBitThreshold = globalBitThreshold;
		this.palette = new SingleValuePalette<>(capacity, globalPalette, entry);
	}
	
	@Override
	public VariableValueArray getValues() {
		return palette.getValues();
	}
	
	@Override
	public E getEntry(int index) {
		return palette.getEntry(index);
	}
	
	@Override
	public Collection<? extends PaletteEntry<E>> getEntries() {
		return palette.getEntries();
	}
	
	@Override
	public int setEntry(E entry, int index) {
		int id = palette.setEntry(entry, index);
		if (id != -1)
			return id;
		if (palette instanceof SingleValuePalette<E> p) {
			palette = new IndirectPalette<>(minBitsPerEntry, globalBitThreshold, palette);
		} else if (palette instanceof IndirectPalette<E> p) {
			palette = new IndirectGlobalPalette<>(minBitsPerEntry, 0, palette);
		} else {
			throw new IllegalStateException("Unable to switch palette: " + palette.getClass().getName());
		}
		return palette.setEntry(entry, index);
	}
	
	@Override
	public void setRawEntry(int index, int entryValue) {
		palette.setRawEntry(index, entryValue);
	}
	
	@Override
	public void optimize() {
		palette.optimize();
	}
	
	@Override
	public int getEntryValue(E entry) {
		return palette.getEntryValue(entry);
	}
	
	@Override
	public void recount() {
		palette.recount();
	}

	@Override
	public int size() {
		return palette.size();
	}
	
	@Override
	public int getEntryCount() {
		return palette.getEntryCount();
	}

	@Override
	public int getEntryValueAt(int index) {
		return palette.getEntryValueAt(index);
	}

	@Override
	public int getBitsPerValue() {
		return palette.getBitsPerValue();
	}

	@Override
	public int getMinBitsPerValue() {
		return minBitsPerEntry;
	}

	@Override
	public PaletteEntry<E> getEntry(E entry) {
		return palette.getEntry(entry);
	}

	@Override
	public int getMaxEntryCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMaxBitsPerValue() {
		return 0;
	}

	@Override
	public int getCapacity() {
		return palette.getCapacity();
	}

	@Override
	public void write(ByteBuf buf) {
		palette.write(buf);	
	}

	@Override
	public GlobalValueProvider<E> getGlobalProvider() {
		return palette.getGlobalProvider();
	}
	
	public Palette<E> getPalette() {
		return palette;
	}

	@Override
	public long getSerializedSize() {
		return palette.getSerializedSize();
	}

}
