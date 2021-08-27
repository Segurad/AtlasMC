package de.atlasmc.io.protocol.play;

import de.atlasmc.Location;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_PLAYER_POSITION)
public interface PacketInPlayerPosition extends PacketPlay, PacketInbound {
	
	public double getX();
	public double getFeedY();
	public double getZ();
	public boolean isOnGround();
	
	/**
	 * Applies all Location changes to the Location
	 * @param loc
	 */
	public void getLocation(Location loc);
	
	@Override
	default int getDefaultID() {
		return IN_PLAYER_POSITION;
	}

}
