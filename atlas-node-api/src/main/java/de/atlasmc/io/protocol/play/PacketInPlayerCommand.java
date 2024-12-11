package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_COMMAND)
public class PacketInPlayerCommand extends AbstractPacket implements PacketPlayIn {
	
	private int entityID;
	private int actionID;
	private int jumpboost;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public int getActionID() {
		return actionID;
	}
	
	public void setActionID(int actionID) {
		this.actionID = actionID;
	}
	
	public int getJumpboost() {
		return jumpboost;
	}
	
	public void setJumpboost(int jumpboost) {
		this.jumpboost = jumpboost;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_COMMAND;
	}

}
