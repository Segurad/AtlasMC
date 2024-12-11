package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_PLAYER_ABILITIES, definition = "player_abilities")
public class PacketOutPlayerAbilities extends AbstractPacket implements PacketPlayOut {
	
	/**
	 * <ul>
	 * <li>0x01 - Invulnerable</li>
	 * <li>0x02 - Flying</li>
	 * <li>0x04 - Allow Fly</li>
	 * <li>0x08 - Instant Break</li>
	 * </ul>
	 */
	public int flags;
	public float flySpeed;
	public float fovModifier;
	
	@Override
	public int getDefaultID() {
		return OUT_PLAYER_ABILITIES;
	}

}
