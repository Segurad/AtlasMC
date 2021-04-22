package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutPlayerAbilities extends Packet {
	
	public boolean isInvulnerable();
	public boolean isFlying();
	public boolean getAllowFly();
	public boolean getInstantBreak();
	public float getFlySpeed();
	public float getFOVModifier();
	
	@Override
	default int getDefaultID() {
		return 0x30;
	}

}
