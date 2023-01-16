package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlockMinecart;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateCommandBlockMinecart extends PacketIO<PacketInUpdateCommandBlockMinecart> {

	@Override
	public void read(PacketInUpdateCommandBlockMinecart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setCommand(readString(in));
		packet.setTrackOutput(in.readBoolean());
	}

	@Override
	public void write(PacketInUpdateCommandBlockMinecart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		writeString(packet.getCommand(), out);
		out.writeBoolean(packet.getTrackOutput());
	}

	@Override
	public PacketInUpdateCommandBlockMinecart createPacketData() {
		return new PacketInUpdateCommandBlockMinecart();
	}

}
