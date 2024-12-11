package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetBeaconEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetBeaconEffect implements PacketIO<PacketInSetBeaconEffect> {
	
	@Override
	public void read(PacketInSetBeaconEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setHasPrimaryEffect(in.readBoolean());
		packet.setPrimaryEffect(readVarInt(in));
		packet.setHasSecondaryEffect(in.readBoolean());
		packet.setSecondaryEffect(readVarInt(in));	
	}

	@Override
	public void write(PacketInSetBeaconEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeBoolean(packet.hasPrimaryEffect());
		writeVarInt(packet.getPrimaryEffect(), out);
		out.writeBoolean(packet.hasSecondaryEffect());
		writeVarInt(packet.getSecondaryEffect(), out);
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
