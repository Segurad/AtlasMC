package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSetBeaconEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetBeaconEffect implements PacketIO<PacketInSetBeaconEffect> {
	
	@Override
	public void read(PacketInSetBeaconEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		if (packet.hasPrimaryEffect = in.readBoolean())
			packet.primaryEffect = readVarInt(in);
		if (packet.hasSecondaryEffect = in.readBoolean());
			packet.secondaryEffect = readVarInt(in);	
	}

	@Override
	public void write(PacketInSetBeaconEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeBoolean(packet.hasPrimaryEffect);
		if (packet.hasPrimaryEffect)
			writeVarInt(packet.primaryEffect, out);
		out.writeBoolean(packet.hasSecondaryEffect);
		if (packet.hasSecondaryEffect)
			writeVarInt(packet.secondaryEffect, out);
	}

	@Override
	public PacketInSetBeaconEffect createPacketData() {
		return new PacketInSetBeaconEffect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetBeaconEffect.class);
	}

}
