package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutChangeGameState;
import de.atlasmc.io.protocol.play.PacketOutChangeGameState.ChangeReason;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChangeGameState extends PacketIO<PacketOutChangeGameState> {

	@Override
	public void read(PacketOutChangeGameState packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setReason(ChangeReason.getByID(in.readUnsignedByte()));
		packet.setValue(in.readFloat());
	}

	@Override
	public void write(PacketOutChangeGameState packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getReason().getID());
		out.writeFloat(packet.getValue());
	}
	
	@Override
	public PacketOutChangeGameState createPacketData() {
		return new PacketOutChangeGameState();
	}
	
}
