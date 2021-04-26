package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateScore extends AbstractPacket implements PacketOutUpdateScore {

	private String name, objective;
	private int action, value;
	
	public CorePacketOutUpdateScore() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutUpdateScore(String name, ScoreAction action, String objective, int value) {
		this();
		this.name = name;
		this.action = action.ordinal();
		this.objective = objective;
		this.value = value;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		name = readString(in);
		action = in.readByte();
		if (action == 1) return;
		objective = readString(in);
		value = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(name, out);
		out.writeByte(action);
		if (action == 1) return;
		writeString(objective, out);
		writeVarInt(value, out);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getObjectiveName() {
		return objective;
	}

	@Override
	public ScoreAction getAction() {
		return ScoreAction.getByID(action);
	}

	@Override
	public int getValue() {
		return value;
	}

}
