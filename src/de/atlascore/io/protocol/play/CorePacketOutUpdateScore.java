package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore.ScoreAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateScore extends CoreAbstractHandler<PacketOutUpdateScore> {

	@Override
	public void read(PacketOutUpdateScore packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntry(readString(in, 40));
		ScoreAction action = ScoreAction.getByID(in.readByte());
		packet.setAction(action);
		packet.setObjective(readString(in, 16));
		if (action == ScoreAction.REMOVE) 
			return;
		packet.setValue(readVarInt(in));
	}

	@Override
	public void write(PacketOutUpdateScore packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getEntry(), out);
		out.writeByte(packet.getAction().getID());
		writeString(packet.getObjective(), out);
		if (packet.getAction() == ScoreAction.REMOVE) 
			return;
		writeVarInt(packet.getValue(), out);
	}

	@Override
	public PacketOutUpdateScore createPacketData() {
		return new PacketOutUpdateScore();
	}

}
