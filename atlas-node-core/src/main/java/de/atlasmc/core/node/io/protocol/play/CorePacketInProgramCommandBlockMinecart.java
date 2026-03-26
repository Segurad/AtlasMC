package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInProgramCommandBlockMinecart;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramCommandBlockMinecart implements PacketCodec<PacketInProgramCommandBlockMinecart> {

	@Override
	public void deserialize(PacketInProgramCommandBlockMinecart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.command = StringCodec.readString(in);
		packet.trackOutput = in.readBoolean();
	}

	@Override
	public void serialize(PacketInProgramCommandBlockMinecart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		StringCodec.writeString(packet.command, out);
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
