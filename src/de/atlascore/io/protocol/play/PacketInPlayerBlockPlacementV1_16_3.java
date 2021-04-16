package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerBlockPlacement;
import io.netty.buffer.ByteBuf;

public class PacketInPlayerBlockPlacementV1_16_3 extends AbstractPacket implements PacketInPlayerBlockPlacement {

	public PacketInPlayerBlockPlacementV1_16_3() {
		super(0x2E, V1_16_3.version);
	}
	
	private int hand,face;
	private SimpleLocation pos;
	private float curposx,curposy,curposz;
	private boolean insideblock;

	@Override
	public void read(ByteBuf in) throws IOException {
		hand = readVarInt(in);
		face = readVarInt(in);
		pos = readPosition(in);
		curposx = in.readFloat();
		curposy = in.readFloat();
		curposz = in.readFloat();	
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(hand, out);
		writeVarInt(face, out);
		writePosition(pos, out);
		out.writeFloat(curposx);
		out.writeFloat(curposy);
		out.writeFloat(curposz);
	}

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
