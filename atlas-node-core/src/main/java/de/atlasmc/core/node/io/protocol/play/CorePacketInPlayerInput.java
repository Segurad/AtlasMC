package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInPlayerInput;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerInput implements PacketIO<PacketInPlayerInput> {

	@Override
	public void read(PacketInPlayerInput packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.sideways = in.readFloat();
		packet.forward = in.readFloat();
		packet.flags = in.readByte();
	}

	@Override
	public void write(PacketInPlayerInput packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.sideways);
		out.writeFloat(packet.forward);
		out.writeByte(packet.flags);
	}
	
	@Override
	public PacketInPlayerInput createPacketData() {
		return new PacketInPlayerInput();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlayerInput.class);
	}

}
