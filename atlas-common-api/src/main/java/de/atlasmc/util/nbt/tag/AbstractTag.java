package de.atlasmc.util.nbt.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;

import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.io.SNBTWriter;

abstract class AbstractTag implements NBT {
	
	protected String name;
	
	public AbstractTag(String name) {
		this.name = name;
	}
	
	public AbstractTag() {}
	
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
		return getType() == other.getType();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		toNBT(name, writer, systemData);
	}
	
	@Override
	public String toString() {
		StringWriter writer = new StringWriter();
		SNBTWriter nbtWriter = new SNBTWriter(writer);
		try {
			nbtWriter.writeNBT(this);
			nbtWriter.close();
		} catch (IOException e) {
			throw new UncheckedIOException("Error while writing NBT to string! (" + writer + ")", e);
		}
		return writer.toString();
	}
	
}
