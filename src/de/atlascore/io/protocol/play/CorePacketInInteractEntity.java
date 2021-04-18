package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInInteractEntity;
import io.netty.buffer.ByteBuf;

public class CorePacketInInteractEntity extends AbstractPacket implements PacketInInteractEntity {

	public CorePacketInInteractEntity() {
		super(0x0E, CoreProtocolAdapter.VERSION);
	}
	
	private int entityID,type,hand;
	private float x,y,z;
	private boolean sneaking;

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		type = readVarInt(in);
		hand = readVarInt(in);
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
		sneaking = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		writeVarInt(type, out);
		writeVarInt(hand, out);
		out.writeFloat(x);
		out.writeFloat(y);
		out.writeFloat(z);
		out.writeBoolean(sneaking);
	}

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