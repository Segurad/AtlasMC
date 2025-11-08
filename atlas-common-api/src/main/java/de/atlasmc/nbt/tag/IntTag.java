package de.atlasmc.nbt.tag;

import java.io.IOException;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;

public final class IntTag extends NumberTag {

	private int data;
	
	public IntTag(String name, int data) {
		this.name = name;
		this.data = data;
	}

	public IntTag() {}

	@Override
	public Integer getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.INT;
	}

	@Override
	public void setData(Object data) {
		this.data = (int) data;
	}
	
	@Override
	public IntTag clone() {
		return (IntTag) super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		return data == ((IntTag) obj).data;
	}

	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeIntTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.INT)
			throw new NBTException("Can not read " + reader.getType().name() + " as INT");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readIntTag();
	}
	
	@Override
	public int asInteger() {
		return data;
	}

	@Override
	public byte asByte() {
		return (byte) data;
	}

	@Override
	public short asShort() {
		return (short) data;
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
