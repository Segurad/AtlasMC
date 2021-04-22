package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutWindowConfirmation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWindowConfirmation extends AbstractPacket implements PacketOutWindowConfirmation {

	private byte windowID;
	private short actionnumber;
	private boolean accepted;
	
	public CorePacketOutWindowConfirmation() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutWindowConfirmation(byte windowID, short actionnumber, boolean accepted) {
		this();
		this.windowID = windowID;
		this.accepted = accepted;
		this.actionnumber = actionnumber;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		actionnumber = in.readShort();
		accepted = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		out.writeShort(actionnumber);
		out.writeBoolean(accepted);
	}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public short getActionNumber() {
		return actionnumber;
	}

	@Override
	public boolean isAccepted() {
		return accepted;
	}

}
