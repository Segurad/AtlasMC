package de.atlasmc.io.protocol.common;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;

public abstract class AbstractPacketCookieData extends AbstractPacket {

	public NamespacedKey key;
	public ByteBuf payload;
	
}
