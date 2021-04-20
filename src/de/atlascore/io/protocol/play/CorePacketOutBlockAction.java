package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutBlockAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockAction extends AbstractPacket implements PacketOutBlockAction {

	private int actionID, actionParam, type;
	private long pos;
	
	public CorePacketOutBlockAction() {
		super(0x0A, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutBlockAction(long pos, int actionID, int actionParam, int blockType) {
		this();
		this.actionID = actionID;
		this.pos = pos;
		this.actionParam = actionParam;
		this.type = blockType;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		actionID = in.readUnsignedByte();
		actionParam = in.readUnsignedByte();
		type = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
		out.writeByte(actionID);
		out.writeByte(actionParam);
		writeVarInt(type, out);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public int getActionID() {
		return actionID;
	}

	@Override
	public int getActionParam() {
		return actionParam;
	}

	@Override
	public int getBlockType() {
		return type;
	}

}
