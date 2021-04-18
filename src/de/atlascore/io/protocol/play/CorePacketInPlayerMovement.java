package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerMovement;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerMovement extends AbstractPacket implements PacketInPlayerMovement {

	public CorePacketInPlayerMovement() {
		super(0x15, CoreProtocolAdapter.VERSION);
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
