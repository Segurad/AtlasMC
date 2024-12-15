package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutStepTick;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutStepTick implements PacketIO<PacketOutStepTick> {

	@Override
	public void read(PacketOutStepTick packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.steps = readVarInt(in);
	}

	@Override
	public void write(PacketOutStepTick packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.steps, out);
	}

	@Override
	public PacketOutStepTick createPacketData() {
		return new PacketOutStepTick();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutStepTick.class);
	}

}
