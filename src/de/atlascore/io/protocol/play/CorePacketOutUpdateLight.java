package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateLight;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateLight extends PacketIO<PacketOutUpdateLight> {
	
	@Override
	public void read(PacketOutUpdateLight packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setChunkX(readVarInt(in));
		packet.setChunkZ(readVarInt(in));
		packet.setTrustEdges(in.readBoolean());
		packet.setSkyMask(readVarInt(in));
		packet.setBlockMask(readVarInt(in));
		packet.setEmptySkyMask(readVarInt(in));
		packet.setEmptyBlockMask(readVarInt(in));
		int length = readVarInt(in);
		byte[] skyLight = new byte[length];
		in.readBytes(skyLight);
		packet.setSkyLight(skyLight);
		length = readVarInt(in);
		byte[] blockLight = new byte[length];
		packet.setBlockLight(blockLight);
	}

	@Override
	public void write(PacketOutUpdateLight packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getChunkX(), out);
		writeVarInt(packet.getChunkZ(), out);
		out.writeBoolean(packet.getTrustEdges());
		writeVarInt(packet.getSkyMask(), out);
		writeVarInt(packet.getBlockMask(), out);
		writeVarInt(packet.getEmptySkyMask(), out);
		writeVarInt(packet.getEmptyBlockMask(), out);
		writeVarInt(packet.getSkyLight().length, out);
		out.writeBytes(packet.getSkyLight());
		writeVarInt(packet.getBlockLight().length, out);
		out.writeBytes(packet.getBlockLight());
	}
	
	@Override
	public PacketOutUpdateLight createPacketData() {
		return new PacketOutUpdateLight();
	}

}
