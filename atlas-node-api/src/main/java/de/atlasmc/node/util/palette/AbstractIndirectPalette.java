package de.atlasmc.node.util.palette;

import java.util.Collection;

import de.atlasmc.node.util.MathUtil;

public abstract class AbstractIndirectPalette<E> extends AbstractPalette<E> {
	
	protected int lowestEntry = 0; // lowest available index
	protected int currentMaxEntries; // number of entries stored before growing
	protected int highestEntry = -1;

	public AbstractIndirectPalette(int minBitsPerEntry, int capacity, int maxBitsPerEntry, GlobalValueProvider<E> provider) {
		super(minBitsPerEntry, capacity, maxBitsPerEntry, provider);
		this.currentMaxEntries = MathUtil.createBitMask(minBitsPerEntry)+1;
	}
	
	public AbstractIndirectPalette(int minBitsPerEntry, int maxBitsPerEntry, Palette<E> palette) {
		super(minBitsPerEntry, maxBitsPerEntry, palette);
		this.currentMaxEntries = MathUtil.createBitMask(minBitsPerEntry)+1;
	}
	
	@Override
	protected void setEntries(Collection<? extends PaletteEntry<E>> entries) {
		for (PaletteEntry<E> entry : entries) {
			Entry<E> newEntry = addEntry(entry.getEntry(), entry.value());
			newEntry.paletteValue = highestEntry = lowestEntry++;
		}
	}

	@Override
	protected Entry<E> addOrReplaceEntry(E entry, int index, boolean checkIndex) {
		Entry<E> pEntry = super.addOrReplaceEntry(entry, index, checkIndex);
		if (pEntry != null)
			return pEntry;
		if (lowestEntry < currentMaxEntries) { // insert value at valid position
			pEntry = addEntry(entry, lowestEntry);
			if (lowestEntry > highestEntry)
				highestEntry = lowestEntry;
 			for (lowestEntry++; lowestEntry < currentMaxEntries; lowestEntry++) { // search for next lowest
				PaletteEntry<E> tmp = valueToEntry.get(lowestEntry);
				if (tmp == null) {
					break;
				}
			}
 			return pEntry;
		}
		// grow palette
		currentMaxEntries <<= 1;
		int newBitsPerValue = values.getBitsPerValue()+1;
		if (newBitsPerValue >= maxBitsPerEntry && maxBitsPerEntry != 0) {
			return null;
		} else {
			values.resize(newBitsPerValue);
		}
		pEntry = addEntry(entry, lowestEntry++);
		if (lowestEntry > highestEntry)
			highestEntry = lowestEntry;
		return pEntry;
	}
	
	@Override
	protected boolean replaceEntry(Entry<E> oldEntry, Entry<E> newEntry) {
		if (!super.replaceEntry(oldEntry, newEntry))
			return false;
		if (oldEntry.paletteValue == highestEntry) {
			for (int i = highestEntry-1; i >= 0; i--) {
				PaletteEntry<E> entry = valueToEntry.get(i);
				if (entry != null) {
					highestEntry = i;
					break;
				}
			}
		}
		return true;
	}
	
	@Override
	public int size() {
		return highestEntry + 1;
	}
	
}
