package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSteerVehicle;
import io.netty.buffer.ByteBuf;

public class CorePacketInSteerVehicle extends AbstractPacket implements PacketInSteerVehicle {

	public CorePacketInSteerVehicle() {
		super(CoreProtocolAdapter.VERSION);
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
	public float getSideways() {
		return sideways;
	}

	@Override
	public float getForward() {
		return forward;
	}

	@Override
	public byte getFlags() {
		return flags;
	}

}
