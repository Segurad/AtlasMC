package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDisplayScoreboard;
import de.atlasmc.scoreboard.DisplaySlot;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisplayScoreboard extends AbstractPacket implements PacketOutDisplayScoreboard {

	private int pos;
	private String objective;
	
	public CorePacketOutDisplayScoreboard() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutDisplayScoreboard(DisplaySlot pos, String name) {
		this();
		this.pos = pos.getID();
		this.objective = name;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readByte();
		objective = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(pos);
		writeString(objective, out);
	}

	@Override
	public DisplaySlot getPosition() {
		return DisplaySlot.getByID(pos);
	}

	@Override
	public String getObjective() {
		return objective;
	}

	@Override
	public void setPosition(DisplaySlot slot) {
		this.pos = slot.getID();
	}

	@Override
	public void setObjective(String name) {
		this.objective = name;
	}

}
