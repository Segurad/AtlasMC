package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

public final class LongArrayTag extends AbstractTag {

	private long[] data;
	
	public LongArrayTag(String name, long[] data) {
		this.data = data;
		this.name = name;
	}
	
	public LongArrayTag() {}

	@Override
	public long[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.LONG_ARRAY;
	}

	@Override
	public void setData(Object data) {
		this.data = (long[]) data;
	}
	
	@Override
	public LongArrayTag clone() {
		LongArrayTag clone = (LongArrayTag) super.clone();
		if (clone == null)
			return null;
		if (data != null)
			clone.setData(data.clone());
		return clone;
	}

}
