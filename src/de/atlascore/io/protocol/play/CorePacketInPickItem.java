package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPickItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInPickItem extends AbstractPacket implements PacketInPickItem {

	public CorePacketInPickItem() {
		super(0x18, CoreProtocolAdapter.VERSION);
		
	}

	private int slottouse;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		slottouse = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(slottouse, out);
	}

	@Override
	public int SlotToUse() {
		return slottouse;
	}

}
