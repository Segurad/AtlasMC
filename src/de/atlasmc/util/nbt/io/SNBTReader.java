package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.Reader;
import java.util.UUID;

import com.google.gson.stream.JsonReader;

import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

/**
 * {@link NBTReader} implementation to read NBT Json
 */
public class SNBTReader extends JsonReader implements NBTReader {

	public SNBTReader(Reader in) {
		super(in);
	}

	@Override
	public TagType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TagType getListType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRestPayload() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte readByteTag() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short readShortTag() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readIntTag() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long readLongTag() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float readFloatTag() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double readDoubleTag() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] readByteArrayTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readStringTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readNextEntry() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] readLongArrayTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NBT readNBT() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID readUUID() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void skipTag() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mark() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search(String key, TagType type, boolean list) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
