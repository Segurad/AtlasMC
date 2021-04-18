package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInNameItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInNameItem extends AbstractPacket implements PacketInNameItem {

	public CorePacketInNameItem() {
		super(0x20, CoreProtocolAdapter.VERSION);
	}

	private String itemname;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		itemname = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(itemname, out);
	}

	@Override
	public String ItemName() {
		return itemname;
	}

}
