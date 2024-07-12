package de.atlasmc.util.nbt.tag;

public abstract class NumberTag extends AbstractTag {
	
	public NumberTag(String name) {
		this.name = name;
	}
	
	public NumberTag() {}
	
	public abstract int asInteger();
	
	public abstract byte asByte();
	
	public abstract short asShort();
	
	public abstract long asLong();
	
	public abstract float asFloat();
	
	public abstract double asDouble();
	
	@Override
	public abstract Number getData();

}
