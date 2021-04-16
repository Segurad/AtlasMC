package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInWindowConfirmation;
import io.netty.buffer.ByteBuf;

public class PacketInWindowConfirmationV1_16_3 extends AbstractPacket implements PacketInWindowConfirmation {

	public PacketInWindowConfirmationV1_16_3() {
		super(0x07, V1_16_3.version);
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
