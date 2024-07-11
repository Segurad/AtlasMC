package de.atlasmc.util.nbt.tag;

import java.io.IOException;
import java.util.Arrays;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class IntArrayTag extends AbstractTag {

	private int[] data;
	
	public IntArrayTag(String name, int[] data) {
		this.data = data;
		this.name = name;
	}
	
	public IntArrayTag() {}

	@Override
	public int[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.INT_ARRAY;
	}

	@Override
	public void setData(Object data) {
		this.data = (int[]) data;
	}
	
	@Override
	public IntArrayTag clone() {
		IntArrayTag clone = (IntArrayTag) super.clone();
		if (clone == null)
			return null;
		if (data != null)
			clone.setData(data.clone());
		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		IntArrayTag other = (IntArrayTag) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeIntArrayTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.INT_ARRAY)
			throw new NBTException("Can not read " + reader.getType().name() + " as INT_ARRAY");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readIntArrayTag();
	}
	
}
