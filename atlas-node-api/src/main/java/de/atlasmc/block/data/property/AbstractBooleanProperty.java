package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class AbstractBooleanProperty extends BlockDataProperty<Boolean> {

	public AbstractBooleanProperty(String key) {
		super(key);
	}

	@Override
	public TagType getType() {
		return TagType.BYTE;
	}

	@Override
	public Boolean fromNBT(NBTReader reader) throws IOException {
		return reader.readBoolean();
	}

	@Override
	public void toNBT(Boolean value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeByteTag(key, value);
	}
	
	@Override
	public Boolean fromString(String value) {
		return Boolean.valueOf(value);
	}
	
	@Override
	public String toString(Boolean value) {
		return value.toString();
	}

}
