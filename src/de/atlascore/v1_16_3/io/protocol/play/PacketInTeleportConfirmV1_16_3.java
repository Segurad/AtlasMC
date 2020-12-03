package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInTeleportConfirm;

public class PacketInTeleportConfirmV1_16_3 extends AbstractPacket implements PacketInTeleportConfirm {

	public PacketInTeleportConfirmV1_16_3() {
		super(0x00, V1_16_3.version);
	}

	private int teleportID;

	@Override
	public void read(int length, DataInput input) throws IOException {
		teleportID = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) {
		
	}

	@Override
	public int getTeleportID() {
		return teleportID;
	}

}
