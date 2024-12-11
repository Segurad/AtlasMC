package de.atlasmc.io.protocol.common;

import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketAddResourcePack extends AbstractPacket {

	public UUID uuid;
	public String url;
	public String hash;
	public boolean force;
	public Chat promptMessage;
	
}
