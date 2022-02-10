package de.atlasmc.util.nbt;

abstract class AbstractTag implements NBT {
	
	protected String name;
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public AbstractTag clone() {
		AbstractTag clone = null;
		try {
			clone = (AbstractTag) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
	
}
