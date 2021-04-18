package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateStructureBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateStructureBlock extends AbstractPacket implements PacketInUpdateStructureBlock {

	public CorePacketInUpdateStructureBlock() {
		super(0x2A, CoreProtocolAdapter.VERSION);
	}
	
	private SimpleLocation pos;
	private int action,mode,mirror,roation;
	private String name,metadata;
	private byte offx,offy,offz,sizex,sizey,sizez,flags;
	private float integrity;
	private long seed;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = readPosition(in);
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
		writePosition(pos, out);
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
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public int Action() {
		return action;
	}

	@Override
	public int Mode() {
		return mode;
	}

	@Override
	public String Name() {
		return name;
	}

	@Override
	public byte OffsetX() {
		return offx;
	}

	@Override
	public byte OffsetY() {
		return offy;
	}

	@Override
	public byte OffsetZ() {
		return offz;
	}

	@Override
	public byte SizeX() {
		return sizex;
	}

	@Override
	public byte SizeY() {
		return sizey;
	}

	@Override
	public byte SizeZ() {
		return sizez;
	}

	@Override
	public int Mirror() {
		return mirror;
	}

	@Override
	public int Rotation() {
		return roation;
	}

	@Override
	public String Metadata() {
		return metadata;
	}

	@Override
	public float Integrity() {
		return integrity;
	}

	@Override
	public long Seed() {
		return seed;
	}

	@Override
	public byte Flags() {
		return flags;
	}

}
