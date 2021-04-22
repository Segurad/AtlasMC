package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerBlockPlacement;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerBlockPlacement extends AbstractPacket implements PacketInPlayerBlockPlacement {

	public CorePacketInPlayerBlockPlacement() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	private int hand,face;
	private long pos;
	private float curposx,curposy,curposz;
	private boolean insideblock;

	@Override
	public void read(ByteBuf in) throws IOException {
		hand = readVarInt(in);
		face = readVarInt(in);
		pos = in.readLong();
		curposx = in.readFloat();
		curposy = in.readFloat();
		curposz = in.readFloat();	
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(hand, out);
		writeVarInt(face, out);
		out.writeLong(pos);
		out.writeFloat(curposx);
		out.writeFloat(curposy);
		out.writeFloat(curposz);
	}

	@Override
	public int getHand() {
		return hand;
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public int getFace() {
		return face;
	}

	@Override
	public float getCursurPositionX() {
		return curposx;
	}

	@Override
	public float getCursurPositionY() {
		return curposy;
	}

	@Override
	public float getCursurPositionZ() {
		return curposz;
	}

	@Override
	public boolean isInsideblock() {
		return insideblock;
	}

}
