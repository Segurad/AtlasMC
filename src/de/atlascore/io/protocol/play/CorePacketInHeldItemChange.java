package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInHeldItemChange;
import io.netty.buffer.ByteBuf;

public class CorePacketInHeldItemChange extends AbstractPacket implements PacketInHeldItemChange {

	public CorePacketInHeldItemChange() {
		super(0x25, CoreProtocolAdapter.VERSION);
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
