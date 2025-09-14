package de.atlasmc.node.io.protocol.common;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketDisconnect extends AbstractPacket {
	
	public Chat reason;

}
