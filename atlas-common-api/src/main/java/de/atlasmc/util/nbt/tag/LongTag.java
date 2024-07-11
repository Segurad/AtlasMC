package de.atlasmc.util.nbt.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class LongTag extends AbstractTag {

	private long data;
	
	public LongTag(String name, long data) {
		this.name = name;
		this.data = data;
	}

	public LongTag() {}

	@Override
	public Long getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.LONG;
	}
	
	void readD(DataInput input, boolean readName) throws IOException {
		data = input.readLong();
	}
	
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeLong(data);
	}

	@Override
	public void setData(Object data) {
		this.data = (long) data;
	}
	
	@Override
	public LongTag clone() {
		return (LongTag) super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		return data == ((LongTag) obj).data;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeLongTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.LONG)
			throw new NBTException("Can not read " + reader.getType().name() + " as LONG");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readLongTag();
	}

}
