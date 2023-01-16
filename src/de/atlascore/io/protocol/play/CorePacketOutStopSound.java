package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.SoundCategory;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutStopSound;
import io.netty.buffer.ByteBuf;

public class CorePacketOutStopSound extends PacketIO<PacketOutStopSound> {

	@Override
	public void read(PacketOutStopSound packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		int flags = in.readByte();
		if (flags == 0) 
			return;
		if ((flags & 0x1) == 0x1) {
			packet.setCategory(SoundCategory.getByID(readVarInt(in)));
		}
		if ((flags & 0x2) == 0x2) {
			packet.setSound(readString(in));
		}
	}

	@Override
	public void write(PacketOutStopSound packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		int flags = 0;
		if (packet.getCategory() != null)
			flags |= 0x1;
		if (packet.getSound() != null)
			flags |= 0x2;
		out.writeByte(flags);
		if (packet.getCategory() != null)
			writeVarInt(packet.getCategory().getID(), out);
		if (packet.getSound() != null)
			writeString(packet.getSound(), out);
	}
	
	@Override
	public PacketOutStopSound createPacketData() {
		return new PacketOutStopSound();
	}

}
