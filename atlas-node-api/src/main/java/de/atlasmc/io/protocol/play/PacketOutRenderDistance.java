package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_RENDER_DISTANCE)
public class PacketOutRenderDistance extends AbstractPacket implements PacketPlayOut {
	
	private int distance;
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_RENDER_DISTANCE;	
	}

}
