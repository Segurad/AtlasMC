package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInTeleportConfirm extends Packet {

	public int getTeleportID();
}
