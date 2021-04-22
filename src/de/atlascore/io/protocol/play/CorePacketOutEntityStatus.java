package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityStatus;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityStatus extends AbstractPacket implements PacketOutEntityStatus {

	private int entityID, status;
	
	public CorePacketOutEntityStatus() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityStatus(int entityID, int status) {
		this();
		this.entityID = entityID;
		this.status = status;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = in.readInt();
		status = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(entityID);
		out.writeByte(status);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public int getStatus() {
		return status;
	}

}
