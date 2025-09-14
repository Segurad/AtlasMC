package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_TELEPORT_VEHICLE, definition = "teleport_entity")
public class PacketOutTeleportVehicle extends PacketOutTeleportEntity implements PacketPlayOut {
	
	/**
	 * <ul>
	 * <li>0x0001 = relative X</li>
	 * <li>0x0002 = relative Y</li>
	 * <li>0x0004 = relative Z</li>
	 * <li>0x0008 = relative yaw</li>
	 * <li>0x0010 = relative pitch</li>
	 * <li>0x0020 = relative velocity X</li>
	 * <li>0x0040 = relative velocity Y</li>
	 * <li>0x0080 = relative velocity Z</li>
	 * <li>0x0100 = rotate velocity by rotation change</li>
	 * </ul>
	 */
	public int flags;
	
	@Override
	public int getDefaultID() {
		return OUT_TELEPORT_VEHICLE;
	}

}
