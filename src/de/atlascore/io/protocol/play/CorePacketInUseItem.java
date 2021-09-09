package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUseItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInUseItem extends AbstractPacket implements PacketInUseItem {

	public CorePacketInUseItem() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	private int hand;

	@Override
	public void read(ByteBuf in) throws IOException {
		hand = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(hand, out);
	}

	@Override
	public EquipmentSlot getHand() {
		return hand == 0 ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
	}

}
