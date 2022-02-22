package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

public final class ByteTag extends AbstractTag {

	private byte data;
	
	public ByteTag(String name, byte data) {
		this.name = name;
		this.data = data;
	}

	public ByteTag() {}

	@Override
	public Byte getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.BYTE;
	}

	@Override
	public void setData(Object data) {
		this.data = (byte) data;
	}
	
	@Override
	public ByteTag clone() {
		return (ByteTag) super.clone();
	}

}
