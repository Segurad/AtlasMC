package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerAbilities extends AbstractPacket implements PacketOutPlayerAbilities {

	private byte flags = 0;
	private float flySpeed, fovModifier;
	
	public CorePacketOutPlayerAbilities() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutPlayerAbilities(boolean invulnerable, boolean flying, boolean allowFly, boolean instantBreak, float flySpeed, float fovModifier) {
		this();
		if (invulnerable) flags += 0x01;
		if (flying) flags += 0x02;
		if (allowFly) flags += 0x04;
		if (instantBreak) flags += 0x08;
		this.flySpeed = flySpeed;
		this.fovModifier = fovModifier;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		flags = in.readByte();
		flySpeed = in.readFloat();
		fovModifier = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(flags);
		out.writeFloat(flySpeed);
		out.writeFloat(fovModifier);
	}

	@Override
	public boolean isInvulnerable() {
		return (flags & 0x01) == 0x01;
	}

	@Override
	public boolean isFlying() {
		return (flags & 0x02) == 0x02;
	}

	@Override
	public boolean getAllowFly() {
		return (flags & 0x04) == 0x04;
	}

	@Override
	public boolean getInstantBreak() {
		return (flags & 0x08) == 0x08;
	}

	@Override
	public float getFlySpeed() {
		return flySpeed;
	}

	@Override
	public float getFOVModifier() {
		return fovModifier;
	}

}
