package de.atlasmc.io.protocol.play;

import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.tag.Tag;
import de.atlasmc.util.map.Multimap;

@DefaultPacketID(PacketPlay.OUT_UPDATE_TAGS)
public class PacketOutUpdateTags extends AbstractPacket implements PacketPlayOut {
	
	public Multimap<NamespacedKey, Tag<?>> tags;
	public Map<NamespacedKey, Map<NamespacedKey, int[]>> rawTags;

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_TAGS;
	}

}
