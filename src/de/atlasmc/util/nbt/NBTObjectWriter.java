package de.atlasmc.util.nbt;

import java.io.IOException;
import java.util.ArrayList;

public class NBTObjectWriter implements NBTWriter {
	
	private final ArrayList<NBT> data;
	private NBT lastContainer, masterContainer;
	
	public NBTObjectWriter() {
		data = new ArrayList<NBT>();
		lastContainer = new CompoundTag();
		masterContainer = lastContainer;
		data.add(lastContainer);
	}

	@Override
	public void writeEndTag() throws IOException {
		if (lastContainer == masterContainer) throw new Error("No NBT to close available!");
		data.remove(data.size()-1);
		lastContainer = data.get(data.size()-1);
	}

	@Override
	public void writeByteTag(String name, int value) throws IOException {
		lastContainer.setData(NBT.createByteTag(name, value));
	}

	@Override
	public void writeShortTag(String name, int value) throws IOException {
		lastContainer.setData(NBT.createShortTag(name, value));
	}

	@Override
	public void writeIntTag(String name, int value) throws IOException {
		lastContainer.setData(NBT.createIntTag(name, value));
	}

	@Override
	public void writeLongTag(String name, long value) throws IOException {
		lastContainer.setData(NBT.createLongTag(name, value));
	}

	@Override
	public void writeFloatTag(String name, float value) throws IOException {
		lastContainer.setData(NBT.createFloatTag(name, value));
	}

	@Override
	public void writeDoubleTag(String name, double value) throws IOException {
		lastContainer.setData(NBT.createDoubleTag(name, value));
	}

	@Override
	public void writeByteArrayTag(String name, byte[] data) throws IOException {
		lastContainer.setData(NBT.createByteArrayTag(name, data));
	}

	@Override
	public void writeStringTag(String name, String value) throws IOException {
		lastContainer.setData(NBT.createStringTag(name, value));
	}

	@Override
	public void writeListTag(String name, TagType payload, int payloadsize) throws IOException {
		NBT tag = NBT.createListTag(name, payload);
		data.add(tag);
		lastContainer.setData(tag);
		lastContainer = tag;
	}

	@Override
	public void writeCompoundTag(String name) throws IOException {
		NBT tag = NBT.createCompoundTag(name);
		data.add(tag);
		lastContainer.setData(tag);
		lastContainer = tag;
	}

	@Override
	public void writeIntArrayTag(String name, int[] data) throws IOException {
		lastContainer.setData(NBT.createIntArrayTag(name, data));
	}

	@Override
	public void writeLongArrayTag(String name, long[] data) throws IOException {
		lastContainer.setData(NBT.createLongArrayTag(name, data));
	}

	@Override
	public void writeNBT(NBT nbt) throws IOException {
		lastContainer.setData(nbt);
	}

	public NBT toNBT() {
		return masterContainer;
	}

}
