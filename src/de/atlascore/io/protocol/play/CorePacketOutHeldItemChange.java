package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutHeldItemChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutHeldItemChange extends AbstractPacket implements PacketOutHeldItemChange {

	private int slot;
	
	public CorePacketOutHeldItemChange() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutHeldItemChange(int slot) {
		this();
		this.slot = slot;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(slot);
	}

	@Override
	public int getSlot() {
		return slot;
	}

	@Override
	public void setSlot(int slot) {
		this.slot = slot;
	}

}
