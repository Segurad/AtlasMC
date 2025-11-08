package de.atlasmc.nbt.tag;

import java.io.IOException;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;

public final class ByteTag extends NumberTag {

	private byte data;
	
	public ByteTag(String name, byte data) {
		super(name);
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
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
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

	@Override
	public int asInteger() {
		return data;
	}

	@Override
	public byte asByte() {
		return data;
	}

	@Override
	public short asShort() {
		return data;
	}

	@Override
	public long asLong() {
		return data;
	}

	@Override
	public float asFloat() {
		return data;
	}

	@Override
	public double asDouble() {
		return data;
	}

}
