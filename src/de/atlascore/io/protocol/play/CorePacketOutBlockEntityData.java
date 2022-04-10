package de.atlascore.io.protocol.play;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData;
import de.atlasmc.util.nbt.io.NBTIOReader;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.tag.NBT;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockEntityData extends AbstractPacket implements PacketOutBlockEntityData {

	private long pos;
	private int action;
	private byte[] data;
	
	public CorePacketOutBlockEntityData() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutBlockEntityData(long position, TileUpdateAction action,  byte[] data) {
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
	public TileUpdateAction getAction() {
		return TileUpdateAction.getByID(action);
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
		return new NBTIOReader(new ByteArrayInputStream(data));
	}
	
	@Override
	public byte[] getRawNBT() {
		return data;
	}

}
