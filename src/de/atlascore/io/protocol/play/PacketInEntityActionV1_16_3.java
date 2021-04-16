package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInEntityAction;
import io.netty.buffer.ByteBuf;

public class PacketInEntityActionV1_16_3 extends AbstractPacket implements PacketInEntityAction {

	public PacketInEntityActionV1_16_3() {
		super(0x1C, V1_16_3.version);
		
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
	public int EntityID() {
		return entityID;
	}

	@Override
	public int ActionID() {
		return actionID;
	}

	@Override
	public int JumpBoost() {
		return jumpboost;
	}

}
