package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_PLAYER_ABILITIES)
public class PacketOutPlayerAbilities extends AbstractPacket implements PacketPlayOut {
	
	private int flags = 0;
	private float flySpeed, fovModifier;
	
	/**
	 * <li>0x01 - Invulnerable
	 * <li>0x02 - Flying
	 * <li>0x04 - Allow Fly
	 * <li>0x08 - Instant Break
	 * @param flags
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public boolean isInvulnerable() {
		return (flags & 0x01) == 0x01;
	}

	public boolean isFlying() {
		return (flags & 0x02) == 0x02;
	}

	public boolean getAllowFly() {
		return (flags & 0x04) == 0x04;
	}

	public boolean getInstantBreak() {
		return (flags & 0x08) == 0x08;
	}
	
	public float getFlySpeed() {
		return flySpeed;
	}
	
	public void setFlySpeed(float flySpeed) {
		this.flySpeed = flySpeed;
	}
	
	public float getFovModifier() {
		return fovModifier;
	}
	
	public void setFovModifier(float fovModifier) {
		this.fovModifier = fovModifier;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_PLAYER_ABILITIES;
	}

}
