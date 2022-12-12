package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_SET_BEACON_EFFECT)
public class PacketInSetBeaconEffect extends AbstractPacket implements PacketPlayIn {
	
	private int primaryEffect, secondaryEffect;
	
	public int getPrimaryEffect() {
		return primaryEffect;
	}
	
	public int getSecondaryEffect() {
		return secondaryEffect;
	}
	
	public void setPrimaryEffect(int primaryEffect) {
		this.primaryEffect = primaryEffect;
	}
	
	public void setSecondaryEffect(int secondaryEffect) {
		this.secondaryEffect = secondaryEffect;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_BEACON_EFFECT;
	}

}
