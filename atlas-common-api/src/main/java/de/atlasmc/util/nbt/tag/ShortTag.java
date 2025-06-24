package de.atlasmc.util.nbt.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class ShortTag extends NumberTag {

	private short data;
	
	public ShortTag(String name, short data) {
		super(name);
		this.data = data;
	}

	public ShortTag() {}

	@Override
	public Short getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.SHORT;
	}
	
	void readD(DataInput input, boolean readName) throws IOException {
		data = input.readShort();
	}
	
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeShort(data);
	}

	@Override
	public void setData(Object data) {
		this.data = (short) data;
	}
	
	@Override
	public ShortTag clone() {
		return (ShortTag) super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		return data == ((ShortTag) obj).data;
	}

	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeShortTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.SHORT)
			throw new NBTException("Can not read " + reader.getType().name() + " as SHORT");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readShortTag();
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
