package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

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

}
