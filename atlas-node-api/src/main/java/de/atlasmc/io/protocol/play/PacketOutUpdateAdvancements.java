package de.atlasmc.io.protocol.play;

import java.util.List;
import java.util.Map;

import de.atlasmc.advancement.Advancement;
import de.atlasmc.advancement.AdvancementProgress;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UPDATE_ADVANCEMENTS)
public class PacketOutUpdateAdvancements extends AbstractPacket implements PacketPlayOut {
	
	private boolean reset;
	private List<String> remove;
	private Map<String, Advancement> advancements;
	private Map<String, AdvancementProgress> progress;
	
	public boolean isReset() {
		return reset;
	}
	
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
	public List<String> getRemove() {
		return remove;
	}
	
	public void setRemove(List<String> remove) {
		this.remove = remove;
	}
	
	public Map<String, Advancement> getAdvancements() {
		return advancements;
	}
	
	public void setAdvancements(Map<String, Advancement> advancements) {
		this.advancements = advancements;
	}
	
	public Map<String, AdvancementProgress> getProgress() {
		return progress;
	}
	
	public void setProgress(Map<String, AdvancementProgress> progress) {
		this.progress = progress;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_ADVANCEMENTS;
	}

}
