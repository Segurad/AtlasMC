package de.atlasmc.nbt.tag;

import java.io.IOException;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;

public final class FloatTag extends NumberTag {

	private float data;
	
	public FloatTag(String name, float data) {
		super(name);
		this.data = data;
	}

	public FloatTag() {}

	@Override
	public Float getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.FLOAT;
	}

	@Override
	public void setData(Object data) {
		this.data = (float) data;
	}
	
	@Override
	public FloatTag clone() {
		return (FloatTag) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		return data == ((FloatTag) obj).data;
	}

	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeFloatTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.FLOAT)
			throw new NBTException("Can not read " + reader.getType().name() + " as FLOAT");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readFloatTag();
	}
	
	@Override
	public int asInteger() {
		return (int) data;
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
		return (long) data;
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
