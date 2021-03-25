package de.atlasmc.util.nbt;

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

}
