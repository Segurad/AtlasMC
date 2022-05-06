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
	public void writeByteTag(CharSequence name, int value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createByteTag(name != null ? name.toString() : null, value));
	}

	@Override
	public void writeShortTag(CharSequence name, int value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createShortTag(name != null ? name.toString() : null, value));
	}

	@Override
	public void writeIntTag(CharSequence name, int value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createIntTag(name != null ? name.toString() : null, value));
	}

	@Override
	public void writeLongTag(CharSequence name, long value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createLongTag(name != null ? name.toString() : null, value));
	}

	@Override
	public void writeFloatTag(CharSequence name, float value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createFloatTag(name != null ? name.toString() : null, value));
	}

	@Override
	public void writeDoubleTag(CharSequence name, double value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createDoubleTag(name != null ? name.toString() : null, value));
	}

	@Override
	public void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createByteArrayTag(name != null ? name.toString() : null, Arrays.copyOfRange(data, offset, offset + length)));
	}

	@Override
	public void writeStringTag(CharSequence name, String value) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createStringTag(name != null ? name.toString() : null, value));
	}

	@Override
	public void writeListTag(CharSequence name, TagType payload, int payloadsize) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		NBT tag = NBT.createListTag(name != null ? name.toString() : null, payload);
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
	}

	@Override
	public void writeCompoundTag(CharSequence name) throws IOException {
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		NBT tag = NBT.createCompoundTag(name != null ? name.toString() : null);
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
	}
	
	@Override
	public void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createIntArrayTag(name != null ? name.toString() : null, Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createLongArrayTag(name != null ? name.toString() : null, Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException {
		if (supplier == null)
			throw new IllegalArgumentException("Supplier can not be null!");
		long[] data = new long[length];
		for (int i = 0; i < length; i++)
			data[i] = supplier.getAsLong();
		if (highestContainer.getType() == TagType.LIST)
			name = null;
		highestContainer.setData(NBT.createLongArrayTag(name != null ? name.toString() : null, data));
	}

	@Override
	public void writeNBT(NBT nbt) throws IOException {
		highestContainer.setData(nbt);
	}

	public NBT toNBT() {
		return masterContainer;
	}

	@Override
	public void writeUUID(CharSequence name, UUID uuid) throws IOException {
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
