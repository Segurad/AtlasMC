package de.atlasmc.util.nbt.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class CompoundTag extends AbstractTag implements Iterable<NBT> {

	private List<NBT> data;
	
	public CompoundTag(String name) {
		this.name = name;
		this.data = new ArrayList<>();
	}
	
	public CompoundTag() {}

	@Override
	public List<NBT> getData() {
		return data;
	}
	
	public NBT getTag(String name) {
		for (NBT nbt : data) {
			if (nbt.getName().equals(name)) return nbt;
		}
		return null;
	}

	@Override
	public TagType getType() {
		return TagType.COMPOUND;
	}
	
	public void addTag(NBT tag) {
		data.add(tag);
	}
	
	public void addByteArrayTag(String name, byte[] value) {
		this.data.add(new ByteArrayTag(name, value));
	}
	
	public void addByteTag(String name, byte value) {
		this.data.add(new ByteTag(name, value));
	}
	
	public void addDoubleTag(String name, double value) {
		this.data.add(new DoubleTag(name, value));
	}
	
	public void addFloatTag(String name, float value) {
		this.data.add(new FloatTag(name, value));
	}
	
	public void addIntArrayTag(String name, int[] value) {
		this.data.add(new IntArrayTag(name, value));
	}
	
	public void addIntTag(String name, int value) {
		this.data.add(new IntTag(name, value));
	}
	
	public ListTag<NBT> addListTag(String name, TagType type) {
		ListTag<NBT> tag = new ListTag<>(name, type);
		this.data.add(tag);
		return tag;
	}
	
	public void addLongArrayTag(String name, long[] value) {
		this.data.add(new LongArrayTag(name, value));
	}
	
	public void addLongTag(String name, long value) {
		this.data.add(new LongTag(name, value));
	}
	
	public void addShortTag(String name, short value) {
		this.data.add(new ShortTag(name, value));
	}
	
	public void addStringTag(String name, String value) {
		this.data.add(new StringTag(name, value));
	}
	
	public void removeTag(NBT tag) {
		data.remove(tag);
	}

	public CompoundTag addCompoundTag(String name) {
		CompoundTag tag = new CompoundTag(name);
		this.data.add(tag);
		return tag;
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
	public CompoundTag clone() {
		CompoundTag clone = (CompoundTag) super.clone();
		if (clone == null)
			return null;
		if (data != null) {
			List<NBT> list = new ArrayList<>(data.size());
			for (NBT nbt : data) {
				list.add(nbt.clone());
			}
			clone.setData(list);
		}
		return clone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		CompoundTag other = (CompoundTag) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(name);
		if (data != null)
			for (NBT entry : data) {
				writer.writeNBT(entry);
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

}
