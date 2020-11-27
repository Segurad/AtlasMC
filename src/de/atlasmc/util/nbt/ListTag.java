package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ListTag<T extends NBT> extends AbstractTag {

	private List<T> data;
	private TagType datatype;
	
	public ListTag(String name, TagType datatype) {
		data = new ArrayList<>();
		this.datatype = datatype;
		this.name = name;
	}
	
	public ListTag() {}

	@Override
	public List<T> getData() {
		return data;
	}
	
	public void addTag(T tag) {
		data.add(tag);
	}
	
	public TagType getDataType() {
		return datatype;
	}

	@Override
	public TagType getType() {
		return TagType.LIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	void readD(DataInput input, boolean readName) throws IOException {
		data.clear();
		byte id = input.readByte();
		if (id <= 0) return;
		datatype = TagType.getByID(id);
		int length = input.readInt();
		while(length > 0) {
			NBT dat = datatype.createTag();
			dat.read(input, false);
			data.add((T) dat);
			length--;
		}
	}

	@Override
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeByte(datatype.getID());
		if (data.size() == 0) {
			output.writeByte(0);
			return;
		}
		output.writeInt(data.size());
		for (NBT dat : data) {
			dat.write(output, false);
		}
	}

}
