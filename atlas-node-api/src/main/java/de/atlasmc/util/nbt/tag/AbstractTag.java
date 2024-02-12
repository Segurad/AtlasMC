package de.atlasmc.util.nbt.tag;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTag other = (AbstractTag) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (getType() != other.getType())
			return false;
		return true;
	}
	
}
