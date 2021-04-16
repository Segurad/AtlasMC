package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSteerVehicle;
import io.netty.buffer.ByteBuf;

public class PacketInSteerVehicleV1_16_3 extends AbstractPacket implements PacketInSteerVehicle {

	public PacketInSteerVehicleV1_16_3() {
		super(0x1D, V1_16_3.version);
	}
	
	private float sideways,forward;
	private byte flags;

	@Override
	public void read(ByteBuf in) throws IOException {
		sideways = in.readFloat();
		forward = in.readFloat();
		flags = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeFloat(sideways);
		out.writeFloat(forward);
		out.writeByte(flags);
	}

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
