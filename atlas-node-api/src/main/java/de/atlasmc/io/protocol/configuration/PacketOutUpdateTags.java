package de.atlasmc.io.protocol.configuration;

import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.tag.Tag;
import de.atlasmc.util.map.Multimap;

@DefaultPacketID(PacketConfiguration.OUT_UPDATE_TAGS)
public class PacketOutUpdateTags extends AbstractPacket implements PacketConfigurationOut {

	public Multimap<NamespacedKey, Tag<?>> tags;
	public Map<NamespacedKey, Map<NamespacedKey, int[]>> rawTags;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_TAGS;
	}
	
}
