package de.atlasmc.io.protocol.play;

import java.util.Map;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_TAGS)
public interface PacketOutTags extends PacketPlay, PacketOutbound {
	
	public Map<String, int[]> getBlockTags();
	public Map<String, int[]> getItemTags();
	public Map<String, int[]> getEntityTags();
	public Map<String, int[]> getFluidTags();
	
	@Override
	public default int getDefaultID() {
		return OUT_TAGS;
	}

}
