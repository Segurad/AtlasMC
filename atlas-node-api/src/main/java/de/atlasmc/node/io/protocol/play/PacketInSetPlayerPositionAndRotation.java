package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.Location;
import de.atlasmc.node.io.protocol.PlayerConnection;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_POSITION_AND_ROTATION, definition = "move_player_pos_rot")
public class PacketInSetPlayerPositionAndRotation extends PacketInSetPlayerPosition implements PacketPlayIn {
	
	public float yaw;
	public float pitch;
	
	@Override
	public void updatePlayer(PlayerConnection con) {
		super.updatePlayer(con);
		Location loc = con.getClientLocation();
		loc.pitch = pitch;
		loc.yaw = yaw;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_POSITION_AND_ROTATION;
	}

}
