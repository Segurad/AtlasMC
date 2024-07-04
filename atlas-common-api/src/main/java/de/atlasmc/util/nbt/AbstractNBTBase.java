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
		NBTFieldContainer<NBTHolder> container = (NBTFieldContainer<NBTHolder>) getFieldContainerRoot();
		final NBTHolder holder = getHolder();
		readCompound(container, holder, reader);
	}
	
	private void readCompound(NBTFieldContainer<NBTHolder> container, NBTHolder holder, NBTReader reader) throws IOException {
		final int depth = reader.getDepth(); // set the root depth
		while (depth <= reader.getDepth()) { // ensure to stay over or at root depth
			TagType type = reader.getType();
			if (type == TagType.TAG_END) {
				reader.readNextEntry(); // return in parent compound
				continue; // go to next iteration for further reading or break if reached end
			}
			final CharSequence key = reader.getFieldName();
			if (type == TagType.COMPOUND) {
				NBTFieldContainer<NBTHolder> childContainer = container.getContainer(key);
				if (childContainer != null) { // enter compound if not null
					reader.readNextEntry();
					readCompound(childContainer, holder, reader);
					continue;
				} // if compound is null try read as field
			}
			NBTField<NBTHolder> field = container.getField(key);
			if (field != null) { // if field exists set it
				field.setField(holder, reader);
			} else if (container.hasUnknownFieldHandler()) { // if field does not exist try to use unknown field handler
				field = container.getUnknownFieldHandler();
				field.setField(holder, reader);
			} else if (useCustomTagContainer()) { // if not handler is present try to write in custom tags
				getCustomTagContainer().addCustomTag(reader.readNBT());
			} else {
				reader.skipTag(); // fallback just skip
			}
		}
		if (reader.getType() == TagType.TAG_END) // escape from component
			reader.readNextEntry();
	}
	
	protected abstract NBTFieldContainer<? extends NBTHolder> getFieldContainerRoot();
	
	/**
	 * Returns weather or not the child class has a {@link CustomTagContainer} that should be used to store unknown field
	 * @return should use {@link CustomTagContainer}
	 */
	protected boolean useCustomTagContainer() {
		return false;
	}
	
	/**
	 * If the child class has a {@link CustomTagContainer} that should be used to store unknown field this method should be overridden 
	 * @return a {@link CustomTagContainer}
	 */
	protected CustomTagContainer getCustomTagContainer() {
		return null;
	}
	
	/**
	 * Returns the holder for this NBT data
	 * @implNote by default this method will return this instance of the holder
	 * @return the NBTHolder
	 */
	protected NBTHolder getHolder() {
		return this;
	}

}
