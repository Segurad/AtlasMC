package de.atlasmc.util.nbt;

import java.io.IOException;
import java.util.LinkedList;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class AbstractNBTBase implements NBTHolder {

	@Override
	public abstract void toNBT(NBTWriter writer, boolean systemData) throws IOException;

	/**
	 * Reads everything until the end of the current compound
	 */
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader == null) 
			throw new IllegalArgumentException("NBTReader can not be null!");
		final int depth = reader.getDepth(); // set the root depth
		LinkedList<NBTFieldContainer> containers = null; // used for move into deeper compounds
		NBTFieldContainer highestContainer = getFieldContainerRoot();
		final NBTHolder holder = getHolder();
		while (depth <= reader.getDepth()) { // ensure to stay over or at root depth
			TagType type = reader.getType();
			if (type == TagType.TAG_END) {
				if (containers == null || containers.isEmpty())
					break;
				highestContainer = containers.poll(); // fetch the highest container
				reader.readNextEntry(); // return in parent compound
				continue; // go to next iteration for further reading or break if reached end
			}
			final CharSequence key = reader.getFieldName();
			if (type == TagType.COMPOUND) {
				NBTFieldContainer container = highestContainer.getContainer(key);
				if (container != null) { // enter compound if not null
					if (containers == null)
						containers = new LinkedList<>();
					containers.addFirst(highestContainer);
					highestContainer = container;
					reader.readNextEntry(); // move pointer to first compound entry
					continue;
				} // if compound is null try read as field
			}
			NBTField field = highestContainer.getField(key);
			if (field != null) { // if field exists set it
				field.setField(this, reader);
			} else if (highestContainer.hasUnknownFieldHandler()) { // if field does not exist try to use unknown field handler
				highestContainer.getUnknownFieldHandler().setField(holder, reader);
			} else if (useCustomTagContainer()) { // if not handler is present try to write in custom tags
				getCustomTagContainer().addCustomTag(reader.readNBT());
			} else reader.skipTag(); // fallback just skip
		}
		if (reader.getType() == TagType.TAG_END) // escape from component
			reader.readNextEntry();
	}
	
	protected abstract NBTFieldContainer getFieldContainerRoot();
	
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
