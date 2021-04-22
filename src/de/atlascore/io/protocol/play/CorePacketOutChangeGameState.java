package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutChangeGameState;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChangeGameState extends AbstractPacket implements PacketOutChangeGameState {

	private int reason;
	private float value;
	
	public CorePacketOutChangeGameState() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutChangeGameState(ChangeReason reason, float value) {
		this();
		this.reason = reason.ordinal();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		reason = in.readUnsignedByte();
		value = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(reason);
		out.writeFloat(value);
	}

	@Override
	public ChangeReason getReason() {
		return ChangeReason.getByID(reason);
	}

	@Override
	public float getValue() {
		return value;
	}
}
