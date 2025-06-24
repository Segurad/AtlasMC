package de.atlasmc.util.nbt.tag;

import java.io.IOException;
import java.util.Arrays;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class LongArrayTag extends AbstractTag {

	private long[] data;
	
	public LongArrayTag(String name, long[] data) {
		this.data = data;
		this.name = name;
	}
	
	public LongArrayTag() {}

	@Override
	public long[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.LONG_ARRAY;
	}

	@Override
	public void setData(Object data) {
		this.data = (long[]) data;
	}
	
	@Override
	public LongArrayTag clone() {
		LongArrayTag clone = (LongArrayTag) super.clone();
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
		LongArrayTag other = (LongArrayTag) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeLongArrayTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.LONG_ARRAY)
			throw new NBTException("Can not read " + reader.getType().name() + " as LONG_ARRAY");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readLongArrayTag();
	}

}
