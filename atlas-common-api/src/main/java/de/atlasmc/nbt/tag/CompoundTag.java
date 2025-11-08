package de.atlasmc.nbt.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;

public class CompoundTag extends AbstractCollectionTag<CompoundTag, List<NBT>> {
	
	public CompoundTag(String name) {
		super(name);
	}
	
	public CompoundTag() {
		super();
	}
	
	public NBT getTag(String name) {
		for (NBT nbt : data) {
			if (nbt.getName().equals(name)) 
				return nbt;
		}
		return null;
	}

	@Override
	public void setData(Object data) {
		addTag((NBT) data);
	}

	@Override
	public Iterator<NBT> iterator() {
		return data.iterator();
	}
	
	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(name);
		if (data != null && !data.isEmpty()) {
			for (NBT entry : data) {
				writer.writeNBT(entry);
			}
		}
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.COMPOUND)
			throw new NBTException("Can not read " + reader.getType().name() + " as COMPOUND");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		while (reader.getType() != TagType.TAG_END) {
			reader.readNextEntry();
			addTag(reader.readNBT());
		}
		reader.readNextEntry();
	}

	@Override
	protected List<NBT> createCollection() {
		return new ArrayList<>();
	}

	@Override
	protected CompoundTag getThis() {
		return this;
	}

	@Override
	public TagType getType() {
		return TagType.COMPOUND;
	}

}
