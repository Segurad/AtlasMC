package de.atlasmc.util.nbt;

import java.io.IOException;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class AbstractNBTBase implements NBTHolder {

	static final NBTField<?> SKIP = (holder, reader) -> {
		reader.skipTag();
	};
	
	@Override
	public abstract void toNBT(NBTWriter writer, boolean systemData) throws IOException;

	/**
	 * Reads everything until the end of the current compound
	 */
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader == null) 
			throw new IllegalArgumentException("NBTReader can not be null!");
		@SuppressWarnings("unchecked")
		NBTFieldSet<NBTHolder> set = (NBTFieldSet<NBTHolder>) getFieldSetRoot();
		final NBTHolder holder = getHolder();
		NBTUtil.readNBT(set, holder, reader);
	}
	
	protected abstract NBTFieldSet<? extends NBTHolder> getFieldSetRoot();
	
	/**
	 * Returns the holder for this NBT data
	 * @implNote by default this method will return this instance of the holder
	 * @return the NBTHolder
	 */
	protected NBTHolder getHolder() {
		return this;
	}

}
