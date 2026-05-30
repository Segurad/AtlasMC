package de.atlasmc.node.util.palette;

import java.util.Collection;
import java.util.Objects;
import java.util.function.ToIntFunction;

import de.atlasmc.node.util.VariableValueArray;
import de.atlasmc.util.annotation.Nullable;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public abstract class AbstractPalette<E> implements Palette<E> {
	
	/**
	 * Stores entries by the {@link Objects#hashCode()}
	 */
	protected final Int2ObjectMap<Entry<E>> entryMap;
	/**
	 * Stores entries by using a mapping id
	 */
	protected final Int2ObjectMap<Entry<E>> valueToEntry;
	protected final VariableValueArray values;
	protected final int minBitsPerEntry;
	protected final int maxBitsPerEntry;
	protected final ToIntFunction<E> globalProvider;
	
	/**
	 * Creates a new instance of {@link AbstractPalette}
	 * @param minBitsPerEntry
	 * @param maxBitsPerEntry
	 * @param palette to use as template. The entries must be copied by a child implementation of this class.
	 */
	protected AbstractPalette(int minBitsPerEntry, int maxBitsPerEntry, Palette<E> palette) {
		this.globalProvider = palette.getGlobalProvider();
		this.valueToEntry = new Int2ObjectOpenHashMap<>();
		this.entryMap = new Int2ObjectOpenHashMap<>();
		this.minBitsPerEntry = minBitsPerEntry;
		this.maxBitsPerEntry = maxBitsPerEntry;
		if (palette.getBitsPerValue() > 0) {
			this.values = new VariableValueArray(palette.getValues());
		} else {
			this.values = new VariableValueArray(minBitsPerEntry, palette.getCapacity());
		}
		setEntries(palette.getEntries());
		remap();
	}
	
	/**
	 * Creates a new instance of {@link AbstractPalette}
	 * @param minBitsPerEntry
	 * @param capacity
	 * @param maxBitsPerEntry
	 */
	protected AbstractPalette(int minBitsPerEntry, int capacity, int maxBitsPerEntry, ToIntFunction<E> provider) {
		this.globalProvider = Objects.requireNonNull(provider, "provider");
		this.valueToEntry = new Int2ObjectOpenHashMap<>();
		this.entryMap = new Int2ObjectOpenHashMap<>();
		this.minBitsPerEntry = minBitsPerEntry;
		/**
		 * Stores the maximum number of bits used to represent a entry.
		 * May be 0 for no limit
		 */
		this.maxBitsPerEntry = maxBitsPerEntry;
		this.values = new VariableValueArray(minBitsPerEntry, capacity);
	}
	
	/**
	 * Replaces all values in {@link #values} with the current values of {@link PaletteEntry#paletteValue}.
	 * After values {@link #valueToEntry} will be repopulated with {@link PaletteEntry#paletteValue}
	 */
	protected void remap() {
		final int capacity = values.getCapacity();
		for (int i = 0; i < capacity; i++) {
			int val = values.get(i);
			Entry<E> entry = valueToEntry.get(val);
			values.set(i, entry.paletteValue);
		}
		valueToEntry.clear();
		for (Entry<E> entry : entryMap.values()) {
			valueToEntry.put(entry.paletteValue, entry);
		}
	}
	
	/**
	 * Sets all given entries to this palette assuming no entries are registered
	 * @param entries to set
	 */
	protected abstract void setEntries(Collection<? extends PaletteEntry<E>> entries);
	
	/**
	 * Tries to get an existing {@link PaletteEntry} with the given entry.<br>
	 * If there is no present entry the entry at the given will be checked if {@link PaletteEntry#count()} is 1.<br>
	 * If it is 1 the present entry will be replaced.<br>
	 * Otherwise the default implementation returns null.<br>
	 * Child implementations should add a new entry if possible and return it
	 * @param entry
	 * @param index
	 * @return palette entry or null
	 */
	@Nullable
	protected Entry<E> addOrReplaceEntry(E entry, int index) {
		Entry<E> pEntry = entryMap.get(entry.hashCode()); 
		if (pEntry != null) 
			return pEntry; // no entry to be added return existing
		// checking for entry with count of 1 to be replaced by the new entry
		pEntry = valueToEntry.get(values.get(index));
		if (pEntry.count == 1) { // if the entry at the index has a count of 1 to replace it a
			pEntry.updateEntry(entry);
			return pEntry;
		}
		return null;
	}
	
	/**
	 * Decreases the count of the old value and increases the count of the new value
	 * Returns true if the count of the old value reaches 0 and was removed
	 * @param oldEntry
	 * @param newEntry
	 * @return true if old value was removed
	 */
	protected boolean replaceEntry(Entry<E> oldEntry, Entry<E> newEntry) {
		oldEntry.count--;
		newEntry.count++;
		if (oldEntry.count > 0)
			return false;
		entryMap.remove(oldEntry.hash);
		valueToEntry.remove(oldEntry.paletteValue);
		return true;
	}
	
	protected Entry<E> addEntry(E entry, int value) {
		Entry<E> pEntry = new Entry<>(this, value, globalProvider.applyAsInt(entry), entry);
		valueToEntry.put(value, pEntry);
		entryMap.put(pEntry.hash, pEntry);
		return pEntry;
	}

	@Override
	public VariableValueArray getValues() {
		return values;
	}

	@Override
	public E getEntry(int index) {
		int entryIndex = values.get(index);
		Entry<E> entry = valueToEntry.get(entryIndex);
		return entry != null ? entry.entry : null;
	}
	
	@Override
	public PaletteEntry<E> getPaletteEntry(E entry) {
		return entryMap.get(entry.hashCode());
	}

	@Override
	public Collection<Entry<E>> getEntries() {
		return entryMap.values();
	}

	@Override
	public int setEntry(E entry, int index) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		Entry<E> pEntry = addOrReplaceEntry(entry, index);
		if (pEntry == null)
			return -1;
		int entryValue = pEntry.paletteValue;
		int oldValue = values.replace(index, entryValue);
		replaceEntry(valueToEntry.get(oldValue), pEntry);
		return entryValue;
	}

	@Override
	public boolean setRawEntry(int index, int entryValue) {
		var newEntry = valueToEntry.get(entryValue);
		if (newEntry == null)
			return false;
		int oldValue = values.replace(index, entryValue);
		if (oldValue == entryValue)
			return true;
		replaceEntry(valueToEntry.get(oldValue), newEntry);
		return true;
	}

	@Override
	public void optimize() {
		// override as needed
	}

	@Override
	public int getEntryValue(E entry) {
		var pEntry = entryMap.get(entry.hashCode());
		return pEntry == null ? -1 : pEntry.paletteValue;
	}

	@Override
	public void recount() {
		for (Entry<E> entry : entryMap.values())
			entry.count = 0;
		final int size = values.getCapacity();
		for (int i = 0; i < size; i++)
			valueToEntry.get(values.get(i)).count++;
	}

	@Override
	public int size() {
		return entryMap.size();
	}

	@Override
	public int getEntryCount() {
		return entryMap.size();
	}

	@Override
	public int getEntryValueAt(int index) {
		return values.get(index);
	}

	@Override
	public int getBitsPerValue() {
		return values.getBitsPerValue();
	}

	@Override
	public int getMinBitsPerValue() {
		return minBitsPerEntry;
	}

	@Override
	public int getMaxBitsPerValue() {
		return maxBitsPerEntry;
	}

	@Override
	public int getMaxEntryCount() {
		return maxBitsPerEntry == 0 ? Integer.MAX_VALUE : 1 << maxBitsPerEntry;
	}

	@Override
	public int getCapacity() {
		return values.getCapacity();
	}
	
	/**
	 * Called when updating entry. 
	 * Used to update the value of the given entry.
	 * By default this method will return {@link Entry#paletteValue}
	 * @param pEntry the {@link PaletteEntry}
	 * @param entry the entry updated (by be the same as {@link Entry#entry})
	 * @return new value
	 */
	protected int updateValue(Entry<E> pEntry, E entry) {
		return pEntry.paletteValue;
	}
	
	protected static final class Entry<E> extends DefaultPaletteEntry<E> {
		
		private int hash;
		private final AbstractPalette<E> palette;
		
		protected Entry(AbstractPalette<E> palette, int paletteValue, int globalValue, E entry) {
			super(paletteValue, globalValue, entry);
			this.palette = palette;
		}
		
		@Override
		public E updateEntry(E entry) {
			var oldHash = hash;
			hash = entry.hashCode();
			if (oldHash != hash) { // update entry map
				palette.entryMap.remove(oldHash);
				palette.entryMap.put(hash, this);
			}
			E oldEntry = this.entry;
			this.entry = entry;
			int oldValue = paletteValue;
			paletteValue = palette.updateValue(this, entry);
			if (oldValue != paletteValue) {
				palette.valueToEntry.remove(oldValue);
				palette.valueToEntry.put(paletteValue, this);
			}
			return oldEntry;
		}
		
		@Override
		public boolean equals(Object obj) {
			return entry != null ? entry.equals(obj) : obj == null;
		}
		
		@Override
		public int hashCode() {
			if (hash == 0)
				hash = entry.hashCode();
			return hash;
		}
		
	}
	
	@Override
	public ToIntFunction<E> getGlobalProvider() {
		return globalProvider;
	}

}
