package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_PLAYER_ABILITIES)
public interface PacketOutPlayerAbilities extends PacketPlay, PacketOutbound {
	
	public boolean isInvulnerable();
	public boolean isFlying();
	public boolean getAllowFly();
	public boolean getInstantBreak();
	public float getFlySpeed();
	public float getFOVModifier();
	
	@Override
	default int getDefaultID() {
		return OUT_PLAYER_ABILITIES;
	}

}
