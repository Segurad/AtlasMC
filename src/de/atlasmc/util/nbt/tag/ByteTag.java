package de.atlasmc.util.nbt.tag;

import java.io.IOException;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class ByteTag extends AbstractTag {

	private byte data;
	
	public ByteTag(String name, byte data) {
		this.name = name;
		this.data = data;
	}

	public ByteTag() {}

	@Override
	public Byte getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.BYTE;
	}

	@Override
	public void setData(Object data) {
		this.data = (byte) data;
	}
	
	@Override
	public ByteTag clone() {
		return (ByteTag) super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		return data == ((ByteTag) obj).data;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeByteTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.BYTE)
			throw new NBTException("Can not read " + reader.getType().name() + " as BYTE");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readByteTag();
	}

}
