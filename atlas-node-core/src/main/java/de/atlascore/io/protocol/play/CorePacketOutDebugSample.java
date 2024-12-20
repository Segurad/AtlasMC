package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutDebugSample;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutDebugSample implements PacketIO<PacketOutDebugSample> {

	@Override
	public void read(PacketOutDebugSample packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int length = readVarInt(in);
		long[] samples = new long[length];
		for (int i = 0; i < length; i++) {
			samples[i] = in.readLong();
		}
		packet.samples = samples;
		packet.type = readVarInt(in);
	}

	@Override
	public void write(PacketOutDebugSample packet, ByteBuf out, ConnectionHandler con) throws IOException {
		long[] samples = packet.samples;
		final int length = samples.length;
		for (int i = 0; i < length; i++) {
			out.writeLong(samples[i]);
		}
		writeVarInt(packet.type, out);
	}

	@Override
	public PacketOutDebugSample createPacketData() {
		return new PacketOutDebugSample();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDebugSample.class);
	}

}
