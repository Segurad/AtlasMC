package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_TIME_UPDATE)
public class PacketOutTimeUpdate extends AbstractPacket implements PacketPlayOut {
	
	private long worldAge, timeOfDay;
	
	public long getWorldAge() {
		return worldAge;
	}
	
	public void setWorldAge(long worldAge) {
		this.worldAge = worldAge;
	}
	
	public long getTimeOfDay() {
		return timeOfDay;
	}
	
	public void setTimeOfDay(long timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_TIME_UPDATE;
	}

}
