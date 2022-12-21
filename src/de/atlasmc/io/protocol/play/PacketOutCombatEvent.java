package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_COMBAT_EVENT)
public class PacketOutCombatEvent extends AbstractPacket implements PacketPlayOut {
	
	private int event, duration, entityID, playerID;
	private String deathMessage;
	
	public int getEvent() {
		return event;
	}
	
	public void setEvent(int event) {
		this.event = event;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	public String getDeathMessage() {
		return deathMessage;
	}
	
	public void setDeathMessage(String deathMessage) {
		this.deathMessage = deathMessage;
	}
	
	public void setDeathMessage(Chat deathMessage) {
		this.deathMessage = deathMessage.getText();
	}
	
	@Override
	public int getDefaultID() {
		return OUT_COMBAT_EVENT;
	}

}
