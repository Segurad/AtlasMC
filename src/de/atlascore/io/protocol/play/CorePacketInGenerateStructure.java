package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInGenerateStructure;
import io.netty.buffer.ByteBuf;

public class CorePacketInGenerateStructure extends AbstractPacket implements PacketInGenerateStructure {

	public CorePacketInGenerateStructure() {
		super(0x0F, CoreProtocolAdapter.VERSION);
	}

	private SimpleLocation simploc;
	private int levels;
	private boolean keepJigsaws;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		simploc = readPosition(in);
		levels = readVarInt(in);
		keepJigsaws = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writePosition(simploc, out);
		writeVarInt(levels, out);
		out.writeBoolean(keepJigsaws);
	}

	@Override
	public SimpleLocation Position() {
		return simploc;
	}

	@Override
	public int Levels() {
		return levels;
	}

	@Override
	public boolean Keep_Jigsaws() {
		return keepJigsaws;
	}

}
