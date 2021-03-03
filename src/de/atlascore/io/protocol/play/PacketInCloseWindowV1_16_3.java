package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInCloseWindow;

public class PacketInCloseWindowV1_16_3 extends AbstractPacket implements PacketInCloseWindow {

	public PacketInCloseWindowV1_16_3() {
		super(0x0A, V1_16_3.version);
	}

	private byte windowID;
	
	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public void read(int length, DataInput input) throws IOException {
		windowID = input.readByte();
	}

	@Override
	public void write(DataOutput output) throws IOException {}


}
