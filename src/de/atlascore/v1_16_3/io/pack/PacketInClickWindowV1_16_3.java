package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInClickWindow;

public class PacketInClickWindowV1_16_3 extends AbstractPacket implements PacketInClickWindow {

	public PacketInClickWindowV1_16_3() {
		super(0x09, V1_16_3.version);
	}
	
	private byte windowID, button;
	private short slot, action;
	private int mode;

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public short getSlot() {
		return slot;
	}

	@Override
	public byte getButton() {
		return button;
	}

	@Override
	public short getActionNumber() {
		return action;
	}

	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

}
