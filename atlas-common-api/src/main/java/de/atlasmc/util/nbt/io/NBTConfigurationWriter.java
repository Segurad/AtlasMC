package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * NBT writing for configurations
 */
public class NBTConfigurationWriter implements NBTWriter {
	
	private LinkedList<ConfigurationSection> stack;
	private ConfigurationSection current;
	private ConfigurationSection root;
	
	public NBTConfigurationWriter() {
		this(new MemoryConfiguration());
	}
	
	public NBTConfigurationWriter(ConfigurationSection section) {
		setConfiguration(section);
	}
	
	ConfigurationSection getConfiguration() {
		return root;
	}
	
	public void setConfiguration(ConfigurationSection section) {
		if (stack != null)
			stack.clear();
		this.root = section;
		this.current = section;
	}

	@Override
	public void close() throws IOException {
		stack = null;
		current = null;
		root = null;
	}

	@Override
	public void writeEndTag() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeByteTag(CharSequence name, int value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeShortTag(CharSequence name, int value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeIntTag(CharSequence name, int value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeLongTag(CharSequence name, long value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeFloatTag(CharSequence name, float value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDoubleTag(CharSequence name, double value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeStringTag(CharSequence name, String value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeListTag(CharSequence name, TagType payloadType, int payloadsize) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeCompoundTag(CharSequence name) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeUUID(CharSequence name, UUID uuid) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeNBT(NBT nbt) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeNamespacedKey(CharSequence name, NamespacedKey key) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
