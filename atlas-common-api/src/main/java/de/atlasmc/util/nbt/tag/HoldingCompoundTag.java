package de.atlasmc.util.nbt.tag;

import java.io.IOException;

import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

/**
 * CompoundTag that stores a NBTHolder
 */
public class HoldingCompoundTag extends AbstractTag {

	private NBTHolder value;
	
	@Override
	public NBTHolder getData() {
		return value;
	}

	@Override
	public void setData(Object data) {
		if (!(data instanceof NBTHolder))
			throw new IllegalArgumentException("Data must be a instance of NBTHolder: " + data.getClass().getName());
		this.value = (NBTHolder) data;
	}

	@Override
	public TagType getType() {
		return TagType.COMPOUND;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(name);
		value.toNBT(writer, systemData);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		value.fromNBT(reader);
	}

}
