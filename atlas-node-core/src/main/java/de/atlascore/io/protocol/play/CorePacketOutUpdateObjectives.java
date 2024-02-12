package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateObjectives;
import de.atlasmc.io.protocol.play.PacketOutUpdateObjectives.Mode;
import de.atlasmc.scoreboard.RenderType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateObjectives implements PacketIO<PacketOutUpdateObjectives> {

	@Override
	public void read(PacketOutUpdateObjectives packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setName(readString(in));
		packet.setMode(Mode.getByID(in.readByte()));
		if (packet.getMode() == Mode.REMOVE) 
			return;
		packet.setDisplayName(readString(in));
		packet.setRenderType(RenderType.getByID(readVarInt(in)));
	}

	@Override
	public void write(PacketOutUpdateObjectives packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getName(), out);
		out.writeByte(packet.getMode().getID());
		if (packet.getMode() == Mode.REMOVE) 
			return;
		writeString(packet.getDisplayName(), out);
		writeVarInt(packet.getRenderType().getID(), out);
	}

	@Override
	public PacketOutUpdateObjectives createPacketData() {
		return new PacketOutUpdateObjectives();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateObjectives.class);
	}

}
