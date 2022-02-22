package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

public final class IntTag extends AbstractTag {

	private int data;
	
	public IntTag(String name, int data) {
		this.name = name;
		this.data = data;
	}

	public IntTag() {}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.INT;
	}

	@Override
	public void setData(Object data) {
		this.data = (int) data;
	}
	
	@Override
	public IntTag clone() {
		return (IntTag) super.clone();
	}

}
