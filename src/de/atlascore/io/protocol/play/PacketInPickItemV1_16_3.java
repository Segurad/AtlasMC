package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPickItem;
import io.netty.buffer.ByteBuf;

public class PacketInPickItemV1_16_3 extends AbstractPacket implements PacketInPickItem {

	public PacketInPickItemV1_16_3() {
		super(0x18, V1_16_3.version);
		
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
