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
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		StringTag other = (StringTag) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}
