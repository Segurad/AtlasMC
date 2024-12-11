package de.atlasmc.io.protocol.common;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketCookieData extends AbstractPacket {

	public NamespacedKey key;
	public byte[] payload;
	
}
