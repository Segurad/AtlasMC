package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInClientStatus;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientStatus extends AbstractPacket implements PacketInClientStatus {

	private int actionID;
	
	public CorePacketInClientStatus() {
		super(0x04, CoreProtocolAdapter.VERSION);
	}
	
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
