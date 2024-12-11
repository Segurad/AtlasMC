package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_BEACON_EFFECT)
public class PacketInSetBeaconEffect extends AbstractPacket implements PacketPlayIn {
	
	private boolean hasPrimaryEffect;
	private boolean hasSecondaryEffect;
	private int primaryEffect;
	private int secondaryEffect;
	
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
	
	public void setHasPrimaryEffect(boolean hasPrimaryEffect) {
		this.hasPrimaryEffect = hasPrimaryEffect;
	}
	
	public void setHasSecondaryEffect(boolean hasSecondaryEffect) {
		this.hasSecondaryEffect = hasSecondaryEffect;
	}
	
	public boolean hasPrimaryEffect() {
		return hasPrimaryEffect;
	}
	
	public boolean hasSecondaryEffect() {
		return hasSecondaryEffect;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_BEACON_EFFECT;
	}

}
