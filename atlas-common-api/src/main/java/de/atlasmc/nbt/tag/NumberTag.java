package de.atlasmc.nbt.tag;

public abstract class NumberTag extends AbstractTag {
	
	public NumberTag(String name) {
		super(name);
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
