package de.atlasmc.util.nbt.tag;

import java.io.IOException;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class FloatTag extends AbstractTag {

	private float data;
	
	public FloatTag(String name, float data) {
		this.name = name;
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
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
	
}
