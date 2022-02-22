package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

public final class IntArrayTag extends AbstractTag {

	private int[] data;
	
	public IntArrayTag(String name, int[] data) {
		this.data = data;
		this.name = name;
	}
	
	public IntArrayTag() {}

	@Override
	public int[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.INT_ARRAY;
	}

	@Override
	public void setData(Object data) {
		this.data = (int[]) data;
	}
	
	@Override
	public IntArrayTag clone() {
		IntArrayTag clone = (IntArrayTag) super.clone();
		if (clone == null)
			return null;
		if (data != null)
			clone.setData(data.clone());
		return clone;
	}

}
