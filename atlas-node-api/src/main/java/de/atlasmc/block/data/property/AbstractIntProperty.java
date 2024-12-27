package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class AbstractIntProperty extends BlockDataProperty<Integer> {

	public AbstractIntProperty(String key) {
		super(key);
	}

	@Override
	public TagType getType() {
		return TagType.INT;
	}

	@Override
	public Integer fromNBT(NBTReader reader) throws IOException {
		return reader.readIntTag();
	}

	@Override
	public void toNBT(Integer value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeIntTag(key, 0);
	}
	
	@Override
	public Integer fromString(String value) {
		try {
			return Integer.parseInt(value);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	@Override
	public String toString(Integer value) {
		return value.toString();
	}

}
