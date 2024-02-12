package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_PLAYER_SESSION)
public class PacketInPlayerSession extends AbstractPacket implements PacketPlayIn {

	public UUID sessionID;
	public long expiresAt;
	public byte[] publicKey;
	public byte[] keySignature;
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_PLAYER_SESSION;
	}

}
