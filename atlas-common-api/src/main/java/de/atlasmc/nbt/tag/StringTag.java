package de.atlasmc.nbt.tag;

import java.io.IOException;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;

public final class StringTag extends AbstractTag {

	private String data;
	
	public StringTag(String name, String data) {
		this.data = data;
		this.name = name;
	}
	
	public StringTag() {}

	@Override
	public String getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.STRING;
	}

	@Override
	public void setData(Object data) {
		this.data = (String) data;
	}
	
	@Override
	public StringTag clone() {
		return (StringTag) super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		StringTag other = (StringTag) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.STRING)
			throw new NBTException("Can not read " + reader.getType().name() + " as STRING");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readStringTag();
	}

}
