package de.atlasmc.node.block.data.property;

import java.io.IOException;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;

public abstract class AbstractBooleanProperty extends PropertyType<Boolean> {

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
		switch (value) {
		case "false":
			return false;
		case "true":
			return true;
		default:
			return null;
		}
	}
	
	@Override
	public String toString(Boolean value) {
		return value.toString();
	}

}
