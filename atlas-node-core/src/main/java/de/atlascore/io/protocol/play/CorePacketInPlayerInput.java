package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerInput;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerInput implements PacketIO<PacketInPlayerInput> {

	@Override
	public void read(PacketInPlayerInput packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setSideways(in.readFloat());
		packet.setForward(in.readFloat());
		packet.setFlags(in.readByte());
	}

	@Override
	public void write(PacketInPlayerInput packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.getSideways());
		out.writeFloat(packet.getForward());
		out.writeByte(packet.getFlags());
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
