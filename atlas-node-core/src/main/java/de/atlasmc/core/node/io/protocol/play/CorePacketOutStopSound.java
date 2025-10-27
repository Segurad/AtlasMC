package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.io.protocol.play.PacketOutStopSound;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutStopSound implements PacketIO<PacketOutStopSound> {

	@Override
	public void read(PacketOutStopSound packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		int flags = in.readByte();
		if (flags == 0) 
			return;
		if ((flags & 0x1) == 0x1) {
			packet.category = EnumUtil.getByID(SoundCategory.class, readVarInt(in));
		}
		if ((flags & 0x2) == 0x2) {
			packet.sound = readIdentifier(in);
		}
	}

	@Override
	public void write(PacketOutStopSound packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		int flags = 0;
		if (packet.category != null)
			flags |= 0x1;
		if (packet.sound != null)
			flags |= 0x2;
		out.writeByte(flags);
		if (packet.category != null)
			writeVarInt(packet.category.getID(), out);
		if (packet.sound != null)
			writeIdentifier(packet.sound, out);
	}
	
	@Override
	public PacketOutStopSound createPacketData() {
		return new PacketOutStopSound();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutStopSound.class);
	}

}
