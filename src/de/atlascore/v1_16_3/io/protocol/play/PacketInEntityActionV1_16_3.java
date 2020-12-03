package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInEntityAction;

public class PacketInEntityActionV1_16_3 extends AbstractPacket implements PacketInEntityAction {

	public PacketInEntityActionV1_16_3() {
		super(0x1C, V1_16_3.version);
		
	}

	private int entityID;
	private int actionID;
	private int jumpboost;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		entityID = readVarInt(input);
		actionID = readVarInt(input);
		jumpboost = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

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
