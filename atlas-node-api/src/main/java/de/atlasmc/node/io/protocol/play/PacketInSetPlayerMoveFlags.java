package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.PlayerConnection;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_MOVE_FLAGS, definition = "move_player_status_only")
public class PacketInSetPlayerMoveFlags extends AbstractPacket implements PacketPlayIn {
	
	public static final int
	FLAG_ON_GROUND = 0x01,
	FLAG_PUSH_WALL = 0x02;
	
	public int flags;
	
	public void updatePlayer(PlayerConnection con) {
		con.setClientPushWall((flags & FLAG_PUSH_WALL) == FLAG_PUSH_WALL);
		con.setClientOnGround((flags & FLAG_ON_GROUND) == FLAG_ON_GROUND);
		con.setClientLocationChanged(true);
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_MOVE_FLAGS;
	}

}
