package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInChatCommand;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketInChatCommand implements PacketIO<PacketInChatCommand> {

	@Override
	public void read(PacketInChatCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.command = readString(in);
		packet.commandTimestamp = in.readLong();
		packet.salt = in.readLong();
		final int argsCount = readVarInt(in);
		if (argsCount > 0) {
			Map<String, byte[]> args = packet.arguments = new HashMap<>(argsCount);
			for (int i = 0; i < argsCount; i++) {
				byte[] signature = new byte[256];
				String arg = readString(in);
				in.readBytes(signature);
				args.put(arg, signature);
			}
		}
		packet.messageCount = readVarInt(in);
		packet.acknowledge = readBitSet(in, 20);
	}

	@Override
	public void write(PacketInChatCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.command, out);
		out.writeLong(packet.commandTimestamp);
		out.writeLong(packet.salt);
		if (packet.arguments != null && !packet.arguments.isEmpty()) {
			final Map<String, byte[]> args = packet.arguments;
			writeVarInt(args.size(), out);
			for (String key : args.keySet()) {
				writeString(key, out);
				out.writeBytes(args.get(key));
			}
		} else {
			writeVarInt(0, out);
		}
		writeVarInt(packet.messageCount, out);
		writeBitSet(packet.acknowledge, out);
	}

	@Override
	public PacketInChatCommand createPacketData() {
		return new PacketInChatCommand();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChatCommand.class);
	}

}
