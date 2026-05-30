package de.atlasmc.node.util.palette;

import java.util.Collection;
import java.util.function.ToIntFunction;

import de.atlasmc.io.StreamSerializedSizePredictable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.node.util.VariableValueArray;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Palettes project objects to a {@link VariableValueArray}.
 * Entries are mapped using {@link Object#hashCode()}.
 * If mutable entries are used and changed in some form that affects {@link Object#hashCode()}. Beforehand a reference to the {@link PaletteEntry} should be acquired.
 * After changing the entry. {@link PaletteEntry#updateEntry()} should be used to update internal references.
 * Otherwise internal mappings may break.
 * @param <E>
 */
public interface Palette<E> extends IOWriteable, StreamSerializedSizePredictable {
	
	/**
	 * Returns the values mapped to the entries
	 * @return values
	 */
	VariableValueArray getValues();
	
	/**
	 * Returns the mapping function that provides global values for entries
	 * @return function
	 */
	@NotNull
	ToIntFunction<E> getGlobalProvider();
	
	/**
	 * Returns the entry at the given index or null if not present
	 * @param index
	 * @return entry or null
	 */
	@Nullable
	E getEntry(int index);
	
	/**
	 * Returns the {@link PaletteEntry} of the global value of the given entry
	 * @param entry
	 * @return palette entry or null
	 */
	@Nullable
	PaletteEntry<E> getPaletteEntry(E entry);
	
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
	
	/**
	 * Sets the a entry value at a the given index.
	 * Returns false if no entry associated with the given value exists
	 * @param index
	 * @param entryValue
	 * @return true if success
	 */
	boolean setRawEntry(int index, int entryValue);
	
	/**
	 * Tries to optimize the palette
	 */
	void optimize();
	
	/**
	 * Returns the value used within the {@link VariableValueArray} of the given entry or -1 if no valid entry
	 * @param entry
	 * @return value or -1
	 */
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
	
	/**
	 * Number of bits used to represent a value within {@link VariableValueArray}
	 * @return bits
	 */
	int getBitsPerValue();
	
	/**
	 * Minimum number of bits used for representing a value
	 * @return bits
	 */
	int getMinBitsPerValue();
	
	/**
	 * Returns the maximum number of bits used to store values.
	 * Always 0 if no limit
	 * @return bits count
	 */
	int getMaxBitsPerValue();

	/**
	 * Capacity of the {@link VariableValueArray}
	 * @return capacity
	 */
	int getCapacity();

}
