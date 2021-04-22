package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInWindowConfirmation;
import io.netty.buffer.ByteBuf;

public class CorePacketInWindowConfirmation extends AbstractPacket implements PacketInWindowConfirmation {

	public CorePacketInWindowConfirmation() {
		super(CoreProtocolAdapter.VERSION);
	}

	private byte windowID;
	private short actionNumber;
	private boolean accepted;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		actionNumber = in.readShort();
		accepted = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		out.writeShort(actionNumber);
		out.writeBoolean(accepted);
	}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public short getActionNumber() {
		return actionNumber;
	}

	@Override
	public boolean getAccepted() {
		return accepted;
	}

}
