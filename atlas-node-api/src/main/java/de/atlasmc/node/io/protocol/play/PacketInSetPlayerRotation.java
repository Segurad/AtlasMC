package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.Location;
import de.atlasmc.node.io.protocol.PlayerConnection;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_ROTATION, definition = "move_player_rot")
public class PacketInSetPlayerRotation extends PacketInSetPlayerMoveFlags implements PacketPlayIn {
	
	public float yaw;
	public float pitch;
	
	@Override
	public void updatePlayer(PlayerConnection con) {
		Location loc = con.getClientLocation();
		loc.yaw = yaw;
		loc.pitch = pitch;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_ROTATION;
	}

}
