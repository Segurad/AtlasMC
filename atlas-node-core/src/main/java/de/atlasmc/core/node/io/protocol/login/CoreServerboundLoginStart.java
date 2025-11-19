package de.atlasmc.core.node.io.protocol.login;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.codec.UUIDCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ServerboundLoginStart;
import io.netty.buffer.ByteBuf;

public class CoreServerboundLoginStart implements PacketIO<ServerboundLoginStart> {

	@Override
	public void read(ServerboundLoginStart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.name = StringCodec.readString(in, 16);
		packet.uuid = UUIDCodec.readUUID(in);
	}

	@Override
	public void write(ServerboundLoginStart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		StringCodec.writeString(packet.name, out);
		UUIDCodec.writeUUID(packet.uuid, out);
	}

	@Override
	public ServerboundLoginStart createPacketData() {
		return new ServerboundLoginStart();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundLoginStart.class);
	}

}
