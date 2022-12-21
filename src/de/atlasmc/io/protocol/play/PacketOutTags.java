package de.atlasmc.io.protocol.play;

import java.util.Map;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_TAGS)
public class PacketOutTags extends AbstractPacket implements PacketPlayOut {
	
	private Map<String, int[]> blockTags, itemTags, entityTags, fluidTags;
	
	public Map<String, int[]> getBlockTags() {
		return blockTags;
	}

	public Map<String, int[]> getItemTags() {
		return itemTags;
	}

	public Map<String, int[]> getEntityTags() {
		return entityTags;
	}

	public Map<String, int[]> getFluidTags() {
		return fluidTags;
	}

	public void setBlockTags(Map<String, int[]> blockTags) {
		this.blockTags = blockTags;
	}

	public void setItemTags(Map<String, int[]> itemTags) {
		this.itemTags = itemTags;
	}

	public void setEntityTags(Map<String, int[]> entityTags) {
		this.entityTags = entityTags;
	}

	public void setFluidTags(Map<String, int[]> fluidTags) {
		this.fluidTags = fluidTags;
	}

	@Override
	public int getDefaultID() {
		return OUT_TAGS;
	}

}
