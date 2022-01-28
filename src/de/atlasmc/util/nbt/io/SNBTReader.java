package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.Reader;
import java.util.UUID;

import com.google.gson.stream.JsonReader;

import de.atlasmc.util.ByteDataBuffer;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

/**
 * {@link NBTReader} implementation to read NBT Json<br>
 * Only able to detect Compound, String, Number, List/Array
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
		return (byte) nextInt();
	}

	@Override
	public short readShortTag() throws IOException {
		return (short) nextInt();
	}

	@Override
	public int readIntTag() throws IOException {
		return nextInt();
	}

	@Override
	public long readLongTag() throws IOException {
		return nextLong();
	}

	@Override
	public float readFloatTag() throws IOException {
		return (float) nextDouble();
	}

	@Override
	public double readDoubleTag() throws IOException {
		return nextDouble();
	}

	@Override
	public byte[] readByteArrayTag() throws IOException {
		beginArray();
		ByteDataBuffer buff = new ByteDataBuffer(32);
		while (hasNext()) {
			buff.writeByte(nextInt());
		}
		endArray();
		return buff.toByteArray();
	}

	@Override
	public String readStringTag() throws IOException {
		return nextString();
	}

	@Override
	public void readNextEntry() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		beginArray();
		ByteDataBuffer buff = new ByteDataBuffer(32);
		int count = 0;
		while (hasNext()) {
			buff.writeInt(nextInt());
			count++;
		}
		endArray();
		int[] data = new int[count];
		for (int i = 0; i < count; i++) {
			data[i] = buff.readInt();
		}
		return data; 
	}

	@Override
	public long[] readLongArrayTag() throws IOException {
		beginArray();
		ByteDataBuffer buff = new ByteDataBuffer(32);
		int count = 0;
		while (hasNext()) {
			buff.writeLong(nextLong());
			count++;
		}
		endArray();
		long[] data = new long[count];
		for (int i = 0; i < count; i++) {
			data[i] = buff.readLong();
		}
		return data; 
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
		throw new UnsupportedOperationException("Mark is not implemented for this reader!");
	}

	@Override
	public void reset() {}

	@Override
	public void search(String key, TagType type, boolean list) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
