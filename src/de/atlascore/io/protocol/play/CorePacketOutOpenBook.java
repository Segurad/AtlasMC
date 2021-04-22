package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutOpenBook;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenBook extends AbstractPacket implements PacketOutOpenBook {

	private int hand;
	
	public CorePacketOutOpenBook() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutOpenBook(EquipmentSlot hand) {
		this();
		this.hand = hand == EquipmentSlot.HAND ? 0 : 1;
	}

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
