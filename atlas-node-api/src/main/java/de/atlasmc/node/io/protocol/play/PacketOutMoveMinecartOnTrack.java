package de.atlasmc.node.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_MOVE_MINECART_ON_TRACK, definition = "move_minecart_along_track")
public class PacketOutMoveMinecartOnTrack extends AbstractPacket implements PacketPlayOut {

	public int entityID;
	public List<Step> steps;
	
	public static class Step {
		
		public double x;
		public double y;
		public double z;
		public double velocityX;
		public double velocityY;
		public double velocityZ;
		public float yaw;
		public float pitch;
		public float weight;
		
	}
	
	@Override
	public int getDefaultID() {
		return OUT_MOVE_MINECART_ON_TRACK;
	}

}
