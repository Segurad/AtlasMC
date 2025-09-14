package de.atlasmc.node.util.palette;

class BasePaletteEntry<E> implements PaletteEntry<E> {
	
	int globalValue;
	int paletteValue;	
	E entry;
	int count;
	
	protected BasePaletteEntry(int paletteValue, int globalValue, E entry) {
		this.paletteValue = paletteValue;
		this.globalValue = globalValue;
		this.entry = entry;
	}
	
	@Override
	public int count() {
		return count;
	}
	
	@Override
	public E getEntry() {
		return entry;
	}
	
	@Override
	public E updateEntry() {
		return updateEntry(entry);
	}
	
	@Override
	public E updateEntry(E entry) {
		E oldEntry = this.entry;
		this.entry = entry;
		return oldEntry;
	}
	
	@Override
	public int value() {
		return paletteValue;
	}
	
	@Override
	public int globalValue() {
		return globalValue;
	}

}
