package de.atlasmc.io.protocol.play;

import java.util.List;
import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.advancement.Advancement;
import de.atlasmc.advancement.AdvancementProgress;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_ADVANCEMENTS, definition = "update_advancements")
public class PacketOutUpdateAdvancements extends AbstractPacket implements PacketPlayOut {
	
	public boolean reset;
	public List<NamespacedKey> remove;
	public Map<String, Advancement> advancements;
	public Map<String, AdvancementProgress> progress;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_ADVANCEMENTS;
	}

}
