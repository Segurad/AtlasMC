package de.atlasmc.node.block.data.property;

import java.io.IOException;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.NumberConversion;

public abstract class AbstractIntProperty extends PropertyType<Integer> {

	public AbstractIntProperty(String key) {
		super(key);
	}

	@Override
	public TagType getType() {
		return TagType.INT;
	}
	
	@Override
	public void set(BlockData data, Integer value) {
		setInt(data, value);
	}
	
	public abstract void setInt(BlockData data, int value);

	@Override
	public Integer get(BlockData data) {
		return getInt(data);
	}
	
	public abstract int getInt(BlockData data);
	
	@Override
	public void dataFromNBT(BlockData data, NBTReader reader) throws IOException {
		setInt(data, reader.readIntTag());
	}
	
	@Override
	public void dataFromNBTString(BlockData data, NBTReader reader) throws IOException {
		setInt(data, NumberConversion.toInt(reader.readStringTag()));
	}
	
	@Override
	public void dataToNBT(BlockData data, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeIntTag(key, getInt(data));
	}
	
	@Override
	public void dataToNBTString(BlockData data, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, Integer.toString(getInt(data)));
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
		return NumberConversion.toInt(value);
	}
	
	@Override
	public String toString(Integer value) {
		return value.toString();
	}

}
