package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSignedChatCommand;
import io.netty.buffer.ByteBuf;

public class CorePacketInSignedChatCommand implements PacketIO<PacketInSignedChatCommand> {

	@Override
	public void read(PacketInSignedChatCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.command = StringCodec.readString(in);
		packet.commandTimestamp = in.readLong();
		packet.salt = in.readLong();
		final int argsCount = readVarInt(in);
		if (argsCount > 0) {
			Map<String, byte[]> args = packet.arguments = new HashMap<>(argsCount);
			for (int i = 0; i < argsCount; i++) {
				String arg = StringCodec.readString(in, 16);
				byte[] signature = new byte[256];
				in.readBytes(signature);
				args.put(arg, signature);
			}
		}
		packet.messageCount = readVarInt(in);
		packet.acknowledge = readBitSet(in, 20);
	}

	@Override
	public void write(PacketInSignedChatCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		StringCodec.writeString(packet.command, out);
		out.writeLong(packet.commandTimestamp);
		out.writeLong(packet.salt);
		if (packet.arguments != null && !packet.arguments.isEmpty()) {
			final Map<String, byte[]> args = packet.arguments;
			writeVarInt(args.size(), out);
			for (String key : args.keySet()) {
				StringCodec.writeString(key, out);
				out.writeBytes(args.get(key));
			}
		} else {
			writeVarInt(0, out);
		}
		writeVarInt(packet.messageCount, out);
		writeBitSet(packet.acknowledge, out);
	}

	@Override
	public PacketInSignedChatCommand createPacketData() {
		return new PacketInSignedChatCommand();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSignedChatCommand.class);
	}

}
