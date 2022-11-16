package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

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
	
}
