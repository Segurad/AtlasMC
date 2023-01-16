package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutScoreboardObjective;
import de.atlasmc.io.protocol.play.PacketOutScoreboardObjective.Mode;
import de.atlasmc.scoreboard.RenderType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutScoreboardObjective extends PacketIO<PacketOutScoreboardObjective> {

	@Override
	public void read(PacketOutScoreboardObjective packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setName(readString(in));
		packet.setMode(Mode.getByID(in.readByte()));
		if (packet.getMode() == Mode.REMOVE) 
			return;
		packet.setDisplayName(readString(in));
		packet.setRenderType(RenderType.getByID(readVarInt(in)));
	}

	@Override
	public void write(PacketOutScoreboardObjective packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getName(), out);
		out.writeByte(packet.getMode().getID());
		if (packet.getMode() == Mode.REMOVE) 
			return;
		writeString(packet.getDisplayName(), out);
		writeVarInt(packet.getRenderType().getID(), out);
	}

	@Override
	public PacketOutScoreboardObjective createPacketData() {
		return new PacketOutScoreboardObjective();
	}

}
