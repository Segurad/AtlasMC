package de.atlasmc.util.nbt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	public ListTag<? extends NBT> addListTag(String name, TagType type) {
		ListTag<NBT> tag = new ListTag<NBT>(name, type);
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

	public CompoundTag addCompoundTag(String string) {
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

}
