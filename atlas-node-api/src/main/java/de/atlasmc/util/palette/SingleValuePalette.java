package de.atlasmc.util.palette;

import java.util.Collection;
import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.util.VariableValueArray;
import io.netty.buffer.ByteBuf;

public class SingleValuePalette<E> implements Palette<E> {
	
	public static final int NULL_PALETTE_SERIALIZED_SIZE = 1 + AbstractPacket.getVarIntLength(0) * 2;
	
	private final int capacity;
	private BasePaletteEntry<E> entry;
	private Collection<PaletteEntry<E>> collection;
	private final GlobalValueProvider<E> provider;
	
	public SingleValuePalette(int capacity, GlobalValueProvider<E> provider) {
		this(capacity, provider, null);
	}
	
	public SingleValuePalette(int capacity, GlobalValueProvider<E> provider, E entry) {
		this.capacity = capacity;
		this.provider = provider;
		if (entry == null)
			return;
		int value = provider.value(entry);
		this.entry = new BasePaletteEntry<>(value, value, entry);
		this.entry.count = capacity;
	}

	@Override
	public VariableValueArray getValues() {
		return null;
	}

	@Override
	public E getEntry(int index) {
		return entry != null ? entry.entry : null;
	}

	@Override
	public PaletteEntry<E> getEntry(E entry) {
		return this.entry != null && this.entry.entry.equals(entry) ? this.entry : null;
	}

	@Override
	public Collection<PaletteEntry<E>> getEntries() {
		if (collection == null)
			collection = List.of(entry);
		return collection;
	}

	@Override
	public int setEntry(E entry, int index) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (this.entry == null) {
			int value = provider.value(entry);
			this.entry = new BasePaletteEntry<>(value, value, entry);
		}
		return -1;
	}

	@Override
	public void setRawEntry(int index, int entryValue) {}

	@Override
	public void optimize() {}

	@Override
	public int getEntryValue(E entry) {
		return this.entry != null && this.entry.entry.equals(entry) ? this.entry.paletteValue : null;
	}

	@Override
	public void recount() {}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public int getEntryCount() {
		return 1;
	}

	@Override
	public int getMaxEntryCount() {
		return 1;
	}

	@Override
	public int getEntryValueAt(int index) {
		return 0;
	}

	@Override
	public int getBitsPerValue() {
		return 0;
	}

	@Override
	public int getMinBitsPerValue() {
		return 0;
	}

	@Override
	public int getMaxBitsPerValue() {
		return 0;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public void write(ByteBuf buf) {
		AbstractPacket.writeVarInt(entry != null ? entry.paletteValue : 0, buf);
	}

	@Override
	public GlobalValueProvider<E> getGlobalProvider() {
		return provider;
	}

	@Override
	public long getSerializedSize() {
		return NULL_PALETTE_SERIALIZED_SIZE;
	}

}
