package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInClickWindowButton;

public class PacketInClickWindowButtonV1_16_3 extends AbstractPacket implements PacketInClickWindowButton {

	public PacketInClickWindowButtonV1_16_3() {
		super(0x08, V1_16_3.version);
	}

	private byte windowID, buttonID;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		windowID = input.readByte();
		buttonID = input.readByte();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public byte getButtonID() {
		return buttonID;
	}

}
