package de.atlasmc.io.protocol.play;

import de.atlasmc.Effect;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_EFFECT)
public class PacketOutEffect extends AbstractPacket implements PacketPlayOut {
	
	private Effect effect;
	private int data;
	private long position;
	private boolean disableRelativeVolume;
	
	public Effect getEffect() {
		return effect;
	}
	
	public void setEffect(Effect effect) {
		this.effect = effect;
	}
	
	public int getData() {
		return data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	public boolean getDisableRelativeVolume() {
		return disableRelativeVolume;
	}
	
	public void setDisableRelativeVolume(boolean disableRelativVolume) {
		this.disableRelativeVolume = disableRelativVolume;
	}
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_EFFECT;
	}

}
