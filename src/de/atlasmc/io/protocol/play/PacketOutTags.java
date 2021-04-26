package de.atlasmc.io.protocol.play;

import java.util.Map;

import de.atlasmc.io.Packet;

public interface PacketOutTags extends Packet {
	
	public Map<String, int[]> getBlockTags();
	public Map<String, int[]> getItemTags();
	public Map<String, int[]> getEntityTags();
	public Map<String, int[]> getFluidTags();
	
	@Override
	public default int getDefaultID() {
		return 0x5B;
	}

}
