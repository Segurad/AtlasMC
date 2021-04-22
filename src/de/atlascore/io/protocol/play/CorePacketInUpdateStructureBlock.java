package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateStructureBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateStructureBlock extends AbstractPacket implements PacketInUpdateStructureBlock {

	public CorePacketInUpdateStructureBlock() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	private int action,mode,mirror,roation;
	private String name,metadata;
	private byte offx,offy,offz,sizex,sizey,sizez,flags;
	private float integrity;
	private long seed, pos;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		action = readVarInt(in);
		mode = readVarInt(in);
		mirror = readVarInt(in);
		roation = readVarInt(in);
		name = readString(in);
		metadata = readString(in);
		offx = in.readByte();
		offy = in.readByte();
		offz = in.readByte();
		sizex = in.readByte();
		sizey = in.readByte();
		sizez = in.readByte();
		flags = in.readByte();
		integrity = in.readFloat();
		seed = readVarLong(in);
		
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
		writeVarInt(action, out);
		writeVarInt(mode, out);
		writeVarInt(mirror, out);
		writeVarInt(roation, out);
		writeString(name, out);
		writeString(metadata, out);
		out.writeByte(offx);
		out.writeByte(offy);
		out.writeByte(offz);
		out.writeByte(sizex);
		out.writeByte(sizey);
		out.writeByte(sizez);
		out.writeByte(flags);
		out.writeFloat(integrity);
		writeVarLong(seed, out);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public int getAction() {
		return action;
	}

	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public byte getOffsetX() {
		return offx;
	}

	@Override
	public byte getOffsetY() {
		return offy;
	}

	@Override
	public byte getOffsetZ() {
		return offz;
	}

	@Override
	public byte getSizeX() {
		return sizex;
	}

	@Override
	public byte getSizeY() {
		return sizey;
	}

	@Override
	public byte getSizeZ() {
		return sizez;
	}

	@Override
	public int getMirror() {
		return mirror;
	}

	@Override
	public int getRotation() {
		return roation;
	}

	@Override
	public String getMetadata() {
		return metadata;
	}

	@Override
	public float getIntegrity() {
		return integrity;
	}

	@Override
	public long getSeed() {
		return seed;
	}

	@Override
	public byte getFlags() {
		return flags;
	}

}
