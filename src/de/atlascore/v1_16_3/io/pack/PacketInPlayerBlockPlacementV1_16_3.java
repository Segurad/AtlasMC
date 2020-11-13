package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInPlayerBlockPlacement;

public class PacketInPlayerBlockPlacementV1_16_3 extends AbstractPacket implements PacketInPlayerBlockPlacement {

	public PacketInPlayerBlockPlacementV1_16_3() {
		super(0x2E, V1_16_3.version);
	}
	
	private int hand,face;
	private SimpleLocation pos;
	private float curposx,curposy,curposz;
	private boolean insideblock;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		hand = readVarInt(input);
		face = readVarInt(input);
		pos = readPosition(input);
		curposx = input.readFloat();
		curposy = input.readFloat();
		curposz = input.readFloat();	
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int Hand() {
		return hand;
	}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public int Face() {
		return face;
	}

	@Override
	public float CursurPositionX() {
		return curposx;
	}

	@Override
	public float CursurPositionY() {
		return curposy;
	}

	@Override
	public float CursurPositionZ() {
		return curposz;
	}

	@Override
	public boolean Insideblock() {
		return insideblock;
	}

}
