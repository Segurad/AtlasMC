package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInWindowConfirmation;

public class PacketInWindowConfirmationV1_16_3 extends AbstractPacket implements PacketInWindowConfirmation {

	public PacketInWindowConfirmationV1_16_3() {
		super(0x07, V1_16_3.version);
	}

	private byte windowID;
	private short actionNumber;
	private boolean accepted;
	
	@Override
	public void read(int length, DataInputStream input) throws IOException {
		windowID = input.readByte();
		actionNumber = input.readShort();
		accepted = input.readBoolean();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

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
