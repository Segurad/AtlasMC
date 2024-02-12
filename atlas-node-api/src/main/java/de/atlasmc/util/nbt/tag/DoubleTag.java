package de.atlasmc.util.nbt.tag;

import java.io.IOException;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class DoubleTag extends AbstractTag {

	private double data;
	
	public DoubleTag(String name, double data) {
		this.name = name;
		this.data = data;
	}
	
	public DoubleTag() {}

	@Override
	public Double getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.DOUBLE;
	}

	@Override
	public void setData(Object data) {
		this.data = (double) data;
	}
	
	@Override
	public DoubleTag clone() {
		return (DoubleTag) super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		return data == ((DoubleTag) obj).data;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeDoubleTag(name, data);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.DOUBLE)
			throw new NBTException("Can not read " + reader.getType().name() + " as DOUBLE");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		data = reader.readDoubleTag();	
	}

}
