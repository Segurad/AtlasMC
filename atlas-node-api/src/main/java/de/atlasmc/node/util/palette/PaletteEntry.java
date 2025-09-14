package de.atlasmc.node.util.palette;

/**
 * Class that stores additional informations of a entry.
 * @param <E>
 */
public interface PaletteEntry<E> {
	
	/**
	 * Returns the count of this entry
	 * @return count
	 */
	int count();
	
	/**
	 * Returns the entry
	 * @return entry
	 */
	E getEntry();
	
	/**
	 * Will update the entry in the mappings.
	 * Should be used when entry was changed in a way that will affect {@link Object#hashCode()}
	 * @return the current entry
	 */
	E updateEntry();
	
	/**
	 * Replaces the entry with a new one
	 * @param entry
	 * @return the old entry
	 */
	E updateEntry(E entry);
	
	/**
	 * Returns the value used within the palette of this entry
	 * @return value
	 */
	int value();

	/**
	 * Returns the global value this entry retrieved by the palette's {@link GlobalValueProvider}
	 * @return global value
	 */
	int globalValue();

}
