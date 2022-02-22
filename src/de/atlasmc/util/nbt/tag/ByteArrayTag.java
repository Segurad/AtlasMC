package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

public final class ByteArrayTag extends AbstractTag {

	private byte[] data;
	
	public ByteArrayTag(String name, byte[] data) {
		this.name = name;
		this.data = data;
	}
	
	public ByteArrayTag() {}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.BYTE_ARRAY;
	}

	@Override
	public void setData(Object data) {
		this.data = (byte[]) data;
	}
	
	@Override
	public ByteArrayTag clone() {
		ByteArrayTag clone = (ByteArrayTag) super.clone();
		if (clone == null)
			return null;
		if (data != null)
			clone.setData(data.clone());
		return clone;
	}

}
