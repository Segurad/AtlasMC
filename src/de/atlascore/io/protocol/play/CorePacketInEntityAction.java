package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInEntityAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInEntityAction extends AbstractPacket implements PacketInEntityAction {

	public CorePacketInEntityAction() {
		super(0x1C, CoreProtocolAdapter.VERSION);
		
	}

	private int entityID;
	private int actionID;
	private int jumpboost;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		actionID = readVarInt(in);
		jumpboost = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		writeVarInt(actionID, out);
		writeVarInt(jumpboost, out);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public int getActionID() {
		return actionID;
	}

	@Override
	public int getJumpBoost() {
		return jumpboost;
	}

}
