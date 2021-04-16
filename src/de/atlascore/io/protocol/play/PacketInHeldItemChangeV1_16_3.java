package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInHeldItemChange;
import io.netty.buffer.ByteBuf;

public class PacketInHeldItemChangeV1_16_3 extends AbstractPacket implements PacketInHeldItemChange {

	public PacketInHeldItemChangeV1_16_3() {
		super(0x25, V1_16_3.version);
	}
	
	private short slot;

	@Override
	public void read(ByteBuf in) throws IOException {
		slot = in.readShort();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeShort(slot);
	}

	@Override
	public short Slot() {
		return slot;
	}

}
