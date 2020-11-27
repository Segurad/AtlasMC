package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInSteerVehicle;

public class PacketInSteerVehicleV1_16_3 extends AbstractPacket implements PacketInSteerVehicle {

	public PacketInSteerVehicleV1_16_3() {
		super(0x1D, V1_16_3.version);
	}
	
	private float sideways,forward;
	private byte flags;

	@Override
	public void read(int length, DataInput input) throws IOException {
		sideways = input.readFloat();
		forward = input.readFloat();
		flags = input.readByte();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public float Sideways() {
		return sideways;
	}

	@Override
	public float Forward() {
		return forward;
	}

	@Override
	public byte Flags() {
		return flags;
	}

}
