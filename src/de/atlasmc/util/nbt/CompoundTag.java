package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CompoundTag extends AbstractTag {

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

	@Override
	void readD(DataInput input, boolean readName) throws IOException {
		data.clear();
		while(true) {
			byte id = input.readByte();
			if (id <= 0) return;
			TagType type = TagType.getByID(id);
			NBT dat = type.createTag();
			dat.read(input, true);
			data.add(dat);
		}
	}

	@Override
	void writeD(DataOutput output, boolean readName) throws IOException {
		for (NBT dat : data) {
			dat.write(output, true);
		}
		output.writeByte(0x00);
	}

	public CompoundTag addCompoundTag(String string) {
		CompoundTag tag = new CompoundTag(name);
		this.data.add(tag);
		return tag;
	}

}
