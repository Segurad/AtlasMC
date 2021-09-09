package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.block.BlockFace;
import de.atlasmc.inventory.EquipmentSlot;
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
	public EquipmentSlot getHand() {
		return hand == 0 ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public BlockFace getFace() {
		if (face < 0 || face > 5)
			return BlockFace.UP;
		return CorePacketInPlayerDigging.faces[face];
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
