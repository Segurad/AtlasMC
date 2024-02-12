package de.atlasmc.util.palette;

import java.util.Collection;

import de.atlasmc.io.IOSerializedSizePredictable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.util.VariableValueArray;

public interface Palette<E> extends IOWriteable, IOSerializedSizePredictable {
	
	/**
	 * Returns the values mapped to the entries
	 * @return values
	 */
	VariableValueArray getValues();
	
	GlobalValueProvider<E> getGlobalProvider();
	
	/**
	 * Returns the entry at the given index or null if not present
	 * @param index
	 * @return entry or null
	 */
	E getEntry(int index);
	
	PaletteEntry<E> getEntry(E entry);
	
	/**
	 * Returning a collection containing all entries
	 * @return entries
	 */
	Collection<? extends PaletteEntry<E>> getEntries();
	
	/**
	 * Sets the value at the given index to the given entry.
	 * Returns the value stored or -1 if the entry capacity is reached
	 * @param entry
	 * @param index
	 * @return value or -1
	 */
	int setEntry(E entry, int index);
	
	void setRawEntry(int index, int entryValue);
	
	void optimize();
	
	int getEntryValue(E entry);
	
	/**
	 * Recounts all entries
	 */
	void recount();
	
	/**
	 * Returns the size of the palette.
	 * The size may vary from {@link #getEntryCount()} when not using the global palette.
	 * When using indirect palette the returned value is equal to the highest value of a entry + 1.
	 * When using global palette the returned value is equal to {@link #getEntryCount()}
	 * @return palette size
	 */
	int size();
	
	/**
	 * Returns the number of entries
	 * @return palette size
	 */
	int getEntryCount();
	
	/**
	 * Returns the maximum number of entries
	 * @return count
	 */
	int getMaxEntryCount();

	/**
	 * Returns the value at the given index
	 * @param index
	 * @return value
	 */
	int getEntryValueAt(int index);
	
	int getBitsPerValue();
	
	int getMinBitsPerValue();
	
	/**
	 * Returns the maximum number of bits used to store values.
	 * Always 0 if no limit
	 * @return bits count
	 */
	int getMaxBitsPerValue();

	int getCapacity();

}
