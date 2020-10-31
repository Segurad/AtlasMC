package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInCloseWindow;

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
	public void read(int length, DataInputStream input) throws IOException {
		windowID = input.readByte();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}


}
