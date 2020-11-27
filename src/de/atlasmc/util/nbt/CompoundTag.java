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

}
