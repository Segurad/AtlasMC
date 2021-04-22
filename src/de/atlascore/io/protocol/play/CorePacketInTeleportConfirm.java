package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInTeleportConfirm;
import io.netty.buffer.ByteBuf;

public class CorePacketInTeleportConfirm extends AbstractPacket implements PacketInTeleportConfirm {

	public CorePacketInTeleportConfirm() {
		super(CoreProtocolAdapter.VERSION);
	}

	private int teleportID;

	@Override
	public void read(ByteBuf in) throws IOException {
		teleportID = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) {
		writeVarInt(teleportID, out);
	}

	@Override
	public int getTeleportID() {
		return teleportID;
	}

}
