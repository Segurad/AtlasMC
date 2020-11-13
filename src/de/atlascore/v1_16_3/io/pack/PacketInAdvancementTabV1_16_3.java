package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInAdvancementTab;

public class PacketInAdvancementTabV1_16_3 extends AbstractPacket implements PacketInAdvancementTab {

	public PacketInAdvancementTabV1_16_3() {
		super(0x22, V1_16_3.version);
	}
	
	private int action;
	private String tabID;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		action = input.readInt();
		tabID = readString(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int Action() {
		return action;
	}

	@Override
	public String TabID() {
		return tabID;
	}
	
	

}
