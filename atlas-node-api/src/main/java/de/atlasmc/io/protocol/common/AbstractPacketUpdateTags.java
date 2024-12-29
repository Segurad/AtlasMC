package de.atlasmc.io.protocol.common;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.tag.ProtocolTag;
import de.atlasmc.util.map.Multimap;

public abstract class AbstractPacketUpdateTags extends AbstractPacket {
	
	public Multimap<NamespacedKey, ProtocolTag<?>> tags;

}
