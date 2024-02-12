package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInProgramCommandBlockMinecart;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramCommandBlockMinecart implements PacketIO<PacketInProgramCommandBlockMinecart> {

	@Override
	public void read(PacketInProgramCommandBlockMinecart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setCommand(readString(in, 32767));
		packet.setTrackOutput(in.readBoolean());
	}

	@Override
	public void write(PacketInProgramCommandBlockMinecart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		writeString(packet.getCommand(), out);
		out.writeBoolean(packet.getTrackOutput());
	}

	@Override
	public PacketInProgramCommandBlockMinecart createPacketData() {
		return new PacketInProgramCommandBlockMinecart();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInProgramCommandBlockMinecart.class);
	}

}
