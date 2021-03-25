package de.atlasmc.util.nbt;

import java.io.IOException;

public class NBTObjectReader implements NBTReader {

	private final NBT nbt;
	
	public NBTObjectReader(NBT nbt) {
		this.nbt = nbt;
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
	public String getName() {
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
	public void readListTag() throws IOException {
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
	public Object readTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
