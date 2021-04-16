package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInClientStatus;
import io.netty.buffer.ByteBuf;

public class PacketInClientStatusV1_16_3 extends AbstractPacket implements PacketInClientStatus {

	public PacketInClientStatusV1_16_3() {
		super(0x04, V1_16_3.version);
	}

	private int actionID;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		actionID = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(actionID, out);
	}

	@Override
	public int getActionID() {
		return actionID;
	}

}
