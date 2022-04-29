package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.NBT;

public class NBTObjectWriter implements NBTWriter {
	
	private ArrayList<NBT> containers;
	private NBT highestContainer, masterContainer;
	
	public NBTObjectWriter() {
		containers = new ArrayList<NBT>();
		highestContainer = new CompoundTag();
		masterContainer = highestContainer;
	}

	@Override
	public void writeEndTag() throws IOException {
		if (highestContainer == masterContainer) throw new Error("No NBT to close available!");
		highestContainer = containers.remove(containers.size()-1);
	}

	@Override
	public void writeByteTag(String name, int value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createByteTag(name, value));
	}

	@Override
	public void writeShortTag(String name, int value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createShortTag(name, value));
	}

	@Override
	public void writeIntTag(String name, int value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createIntTag(name, value));
	}

	@Override
	public void writeLongTag(String name, long value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createLongTag(name, value));
	}

	@Override
	public void writeFloatTag(String name, float value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createFloatTag(name, value));
	}

	@Override
	public void writeDoubleTag(String name, double value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createDoubleTag(name, value));
	}

	@Override
	public void writeByteArrayTag(String name, byte[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createByteArrayTag(name, Arrays.copyOfRange(data, offset, offset + length)));
	}

	@Override
	public void writeStringTag(String name, String value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createStringTag(name, value));
	}

	@Override
	public void writeListTag(String name, TagType payload, int payloadsize) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		NBT tag = NBT.createListTag(name, payload);
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
	}

	@Override
	public void writeCompoundTag(String name) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		NBT tag = NBT.createCompoundTag(name);
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
	}
	
	@Override
	public void writeIntArrayTag(String name, int[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createIntArrayTag(name, Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(String name, long[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createLongArrayTag(name, Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(String name, int length, LongSupplier supplier) throws IOException {
		if (supplier == null)
			throw new IllegalArgumentException("Supplier can not be null!");
		long[] data = new long[length];
		for (int i = 0; i < length; i++)
			data[i] = supplier.getAsLong();
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createLongArrayTag(name, data));
	}

	@Override
	public void writeNBT(NBT nbt) throws IOException {
		highestContainer.setData(nbt);
	}

	public NBT toNBT() {
		return masterContainer;
	}

	@Override
	public void writeUUID(String name, UUID uuid) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		if (uuid == null) throw new IllegalArgumentException("UUID can not be null!");
		writeIntArrayTag(name, new int[] {
				(int) (uuid.getMostSignificantBits()>>32),
				(int) uuid.getMostSignificantBits(),
				(int) (uuid.getLeastSignificantBits()>>32),
				(int) uuid.getLeastSignificantBits()
		});
	}

	@Override
	public void close() throws IOException {
		containers = null;
		highestContainer = null;
		masterContainer = null;
	}

}
