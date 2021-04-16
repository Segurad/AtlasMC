package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInNameItem;
import io.netty.buffer.ByteBuf;

public class PacketInNameItemV1_16_3 extends AbstractPacket implements PacketInNameItem {

	public PacketInNameItemV1_16_3() {
		super(0x20, V1_16_3.version);
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
