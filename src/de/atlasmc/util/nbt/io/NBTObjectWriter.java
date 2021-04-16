package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

public class NBTObjectWriter implements NBTWriter {
	
	private final ArrayList<NBT> containers;
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
		highestContainer.setData(NBT.createByteTag(name, value));
	}

	@Override
	public void writeShortTag(String name, int value) throws IOException {
		highestContainer.setData(NBT.createShortTag(name, value));
	}

	@Override
	public void writeIntTag(String name, int value) throws IOException {
		highestContainer.setData(NBT.createIntTag(name, value));
	}

	@Override
	public void writeLongTag(String name, long value) throws IOException {
		highestContainer.setData(NBT.createLongTag(name, value));
	}

	@Override
	public void writeFloatTag(String name, float value) throws IOException {
		highestContainer.setData(NBT.createFloatTag(name, value));
	}

	@Override
	public void writeDoubleTag(String name, double value) throws IOException {
		highestContainer.setData(NBT.createDoubleTag(name, value));
	}

	@Override
	public void writeByteArrayTag(String name, byte[] data) throws IOException {
		highestContainer.setData(NBT.createByteArrayTag(name, data));
	}

	@Override
	public void writeStringTag(String name, String value) throws IOException {
		highestContainer.setData(NBT.createStringTag(name, value));
	}

	@Override
	public void writeListTag(String name, TagType payload, int payloadsize) throws IOException {
		NBT tag = NBT.createListTag(name, payload);
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
	}

	@Override
	public void writeCompoundTag(String name) throws IOException {
		NBT tag = NBT.createCompoundTag(name);
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
	}

	@Override
	public void writeIntArrayTag(String name, int[] data) throws IOException {
		highestContainer.setData(NBT.createIntArrayTag(name, data));
	}

	@Override
	public void writeLongArrayTag(String name, long[] data) throws IOException {
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
		Validate.notNull(uuid, "UUID can not be null!");
		writeIntArrayTag(name, new int[] {
				(int) (uuid.getMostSignificantBits()>>32),
				(int) uuid.getMostSignificantBits(),
				(int) (uuid.getLeastSignificantBits()>>32),
				(int) uuid.getLeastSignificantBits()
		});
	}

}
