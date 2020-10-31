package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInInteractEntity;

public class PacketInInteractEntityV1_16_3 extends AbstractPacket implements PacketInInteractEntity {

	public PacketInInteractEntityV1_16_3() {
		super(0x0E, V1_16_3.version);
	}
	
	private int entityID,type,hand;
	private float x,y,z;
	private boolean sneaking;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		entityID = readVarInt(input);
		type = readVarInt(input);
		hand = readVarInt(input);
		x = input.readFloat();
		y = input.readFloat();
		z = input.readFloat();
		sneaking = input.readBoolean();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int EntityID() {
		return entityID;
	}

	@Override
	public int Type() {
		return type;
	}

	@Override
	public float X() {
		return x;
	}

	@Override
	public float Y() {
		return y;
	}

	@Override
	public float Z() {
		return z;
	}

	@Override
	public int Hand() {
		return hand;
	}

	@Override
	public boolean Sneaking() {
		return sneaking;
	}

}
