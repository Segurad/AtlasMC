package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerMovement;
import io.netty.buffer.ByteBuf;

public class PacketInPlayerMovementV1_16_3 extends AbstractPacket implements PacketInPlayerMovement {

	public PacketInPlayerMovementV1_16_3() {
		super(0x15, V1_16_3.version);
	}
	
	private boolean onGround;

	@Override
	public void read(ByteBuf in) throws IOException {
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeBoolean(onGround);
	}

	@Override
	public boolean OnGround() {
		return onGround;
	}
	
	

}
