package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

import com.google.gson.stream.JsonWriter;

import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

/**
 * {@link NBTWriter} implementation for NBT Json
 */
public class SNBTWriter extends JsonWriter implements NBTWriter {

	public SNBTWriter(Writer out) {
		super(out);
	}

	@Override
	public void writeEndTag() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeByteTag(String name, int value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeShortTag(String name, int value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeIntTag(String name, int value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeLongTag(String name, long value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeFloatTag(String name, float value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDoubleTag(String name, double value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeByteArrayTag(String name, byte[] data) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeStringTag(String name, String value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeListTag(String name, TagType payloadType, int payloadsize) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeCompoundTag(String name) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeIntArrayTag(String name, int[] data) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeLongArrayTag(String name, long[] data) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeNBT(NBT nbt) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeUUID(String name, UUID uuid) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
