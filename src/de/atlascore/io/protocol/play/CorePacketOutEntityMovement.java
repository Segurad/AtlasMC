package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityMovement;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityMovement extends AbstractPacket implements PacketOutEntityMovement {

	private int entityID;
	
	public CorePacketOutEntityMovement() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityMovement(int entityID) {
		this();
		this.entityID = entityID;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

}
