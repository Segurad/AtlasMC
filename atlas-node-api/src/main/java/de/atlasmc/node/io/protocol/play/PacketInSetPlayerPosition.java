package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.Location;
import de.atlasmc.node.io.protocol.PlayerConnection;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_POSITION, definition = "move_player_pos")
public class PacketInSetPlayerPosition extends PacketInSetPlayerMoveFlags implements PacketPlayIn {

	public double x;
	public double feetY;
	public double z;
	
	@Override
	public void updatePlayer(PlayerConnection con) {
		super.updatePlayer(con);
		Location loc = con.getClientLocation();
		loc.x = x;
		loc.y = feetY;
		loc.z = z;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_POSITION;
	}

}
