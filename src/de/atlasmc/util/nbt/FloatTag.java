package de.atlasmc.util.nbt;

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

}
