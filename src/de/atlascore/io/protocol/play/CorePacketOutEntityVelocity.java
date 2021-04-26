package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityVelocity;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityVelocity extends AbstractPacket implements PacketOutEntityVelocity {

	private int entityID;
	private short x, y, z;
	
	public CorePacketOutEntityVelocity() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityVelocity(int entityID, double x, double y, double z) {
		this();
		this.entityID = entityID;
		this.x = (short) (x*8000);
		this.y = (short) (y*8000);
		this.z = (short) (z*8000);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		x = in.readShort();
		y = in.readShort();
		z = in.readShort();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(z);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public short getVelocityX() {
		return x;
	}

	@Override
	public short getVelocityY() {
		return y;
	}

	@Override
	public short getVelocityZ() {
		return z;
	}

}
