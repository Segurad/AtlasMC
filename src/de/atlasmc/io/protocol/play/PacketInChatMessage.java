package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInChatMessage extends Packet {

	public String getMessage();
}
