package de.atlasmc.util.nbt;

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

}
