package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInTeleportConfirm;
import io.netty.buffer.ByteBuf;

public class PacketInTeleportConfirmV1_16_3 extends AbstractPacket implements PacketInTeleportConfirm {

	public PacketInTeleportConfirmV1_16_3() {
		super(0x00, V1_16_3.version);
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
