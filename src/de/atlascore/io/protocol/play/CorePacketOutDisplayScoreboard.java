package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDisplayScoreboard;
import de.atlasmc.scoreboard.Position;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisplayScoreboard extends AbstractPacket implements PacketOutDisplayScoreboard {

	private int pos;
	private String name;
	
	public CorePacketOutDisplayScoreboard() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutDisplayScoreboard(Position pos, String name) {
		this();
		this.pos = pos.getID();
		this.name = name;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readByte();
		name = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(pos);
		writeString(name, out);
	}

	@Override
	public Position getPosition() {
		return Position.getByID(pos);
	}

	@Override
	public String getName() {
		return name;
	}

}
