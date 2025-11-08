package de.atlasmc.nbt.tag;

import java.io.IOException;
import java.util.Arrays;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;

public final class ByteArrayTag extends AbstractTag {

	private byte[] data;
	
	public ByteArrayTag(String name, byte[] data) {
		this.name = name;
		this.data = data;
	}
	
	public ByteArrayTag() {}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.BYTE_ARRAY;
	}

	@Override
	public void setData(Object data) {
		this.data = (byte[]) data;
	}
	
	@Override
	public ByteArrayTag clone() {
		ByteArrayTag clone = (ByteArrayTag) super.clone();
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
		ByteArrayTag other = (ByteArrayTag) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!Arrays.equals(data, other.data)) {
			return false;
		}
		return true;
	}
	
	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeByteArrayTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.BYTE_ARRAY)
			throw new NBTException("Can not read " + reader.getType().name() + " as BYTE_ARRAY");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readByteArrayTag();
	}
	
}
