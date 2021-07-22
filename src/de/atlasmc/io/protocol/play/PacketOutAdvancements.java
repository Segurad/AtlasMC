package de.atlasmc.io.protocol.play;

import java.util.List;
import java.util.Map;

import de.atlasmc.advancement.Advancement;
import de.atlasmc.advancement.AdvancementProgress;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ADVANCEMENTS)
public interface PacketOutAdvancements extends PacketPlay, PacketOutbound {
	
	public boolean isReset();
	public Map<String, Advancement> getAdvancements();
	public List<String> getRemoved();
	public Map<String, AdvancementProgress> getProgress();
	
	@Override
	public default int getDefaultID() {
		return OUT_ADVANCEMENTS;
	}

}
