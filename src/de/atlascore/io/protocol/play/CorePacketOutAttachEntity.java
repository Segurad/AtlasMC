package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutAttachEntity;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAttachEntity extends AbstractPacket implements PacketOutAttachEntity {

	private int holderID, attachedID;
	
	public CorePacketOutAttachEntity() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutAttachEntity(int holderID, int attachedID) {
		this();
		this.holderID = holderID;
		this.attachedID = attachedID;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		attachedID = in.readInt();
		holderID = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(attachedID);
		out.writeInt(holderID);
	}

	@Override
	public int getAttachedEntityID() {
		return attachedID;
	}

	@Override
	public int getHoldingEntityID() {
		return holderID;
	}

}
