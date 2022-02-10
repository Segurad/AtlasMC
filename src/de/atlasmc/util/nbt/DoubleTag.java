package de.atlasmc.util.nbt;

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

}
