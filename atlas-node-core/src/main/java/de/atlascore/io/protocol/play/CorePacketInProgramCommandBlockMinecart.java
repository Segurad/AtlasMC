package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInProgramCommandBlockMinecart;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramCommandBlockMinecart implements PacketIO<PacketInProgramCommandBlockMinecart> {

	@Override
	public void read(PacketInProgramCommandBlockMinecart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.command = readString(in, 32767);
		packet.trackOutput = in.readBoolean();
	}

	@Override
	public void write(PacketInProgramCommandBlockMinecart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		writeString(packet.command, out);
		out.writeBoolean(packet.trackOutput);
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
