package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.world.WorldEvent;

@DefaultPacketID(PacketPlay.OUT_WORLD_EVENT)
public class PacketOutWorldEvent extends AbstractPacket implements PacketPlayOut {
	
	private WorldEvent event;
	private int data;
	private long position;
	private boolean disableRelativeVolume;
	
	public WorldEvent getEvent() {
		return event;
	}
	
	public void setEvent(WorldEvent event) {
		this.event = event;
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
		return OUT_WORLD_EVENT;
	}

}
