package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.TagType;

public final class StringTag extends AbstractTag {

	private String data;
	
	public StringTag(String name, String data) {
		this.data = data;
		this.name = name;
	}
	
	public StringTag() {}

	@Override
	public String getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.STRING;
	}

	@Override
	public void setData(Object data) {
		this.data = (String) data;
	}
	
	@Override
	public StringTag clone() {
		return (StringTag) super.clone();
	}

}
