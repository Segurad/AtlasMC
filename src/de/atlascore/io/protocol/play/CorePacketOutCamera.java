package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutCamera;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCamera extends AbstractPacket implements PacketOutCamera {

	private int entityID;
	
	public CorePacketOutCamera() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutCamera(int entityID) {
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
