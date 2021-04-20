package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData;
import de.atlasmc.util.ByteDataBuffer;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.io.NBTIOReader;
import de.atlasmc.util.nbt.io.NBTReader;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockEntityData extends AbstractPacket implements PacketOutBlockEntityData {

	private long pos;
	private int action;
	private byte[] data;
	
	public CorePacketOutBlockEntityData() {
		super(0x09, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutBlockEntityData(long position, Action action,  byte[] data) {
		this();
		pos = position;
		this.action = action.getID();
		this.data = data;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		action = in.readUnsignedByte();
		data = new byte[in.readableBytes()];
		in.readBytes(data);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public Action getAction() {
		return Action.getByID(action);
	}

	@Override
	public NBT getNBT() {
		try {
			return getNBTReader().readNBT();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NBTReader getNBTReader() {
		return new NBTIOReader(new ByteDataBuffer(data));
	}
	
	@Override
	public byte[] getRawNBT() {
		return data;
	}

}
