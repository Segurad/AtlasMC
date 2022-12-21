package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.Effect;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEffect extends CoreAbstractHandler<PacketOutEffect> {

	@Override
	public void read(PacketOutEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEffect(Effect.getByID(in.readInt()));
		packet.setPosition(in.readLong());
		packet.setData(in.readInt());
		packet.setDisableRelativeVolume(in.readBoolean());
	}

	@Override
	public void write(PacketOutEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getEffect().getID());
		out.writeLong(packet.getPosition());
		out.writeInt(packet.getData());
		out.writeBoolean(packet.getDisableRelativeVolume());
	}
	
	@Override
	public PacketOutEffect createPacketData() {
		return new PacketOutEffect();
	}

}
