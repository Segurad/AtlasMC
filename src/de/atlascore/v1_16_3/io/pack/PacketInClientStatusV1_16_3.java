package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInClientStatus;

public class PacketInClientStatusV1_16_3 extends AbstractPacket implements PacketInClientStatus {

	public PacketInClientStatusV1_16_3() {
		super(0x04, V1_16_3.version);
	}

	private int actionID;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		actionID = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int getActionID() {
		return actionID;
	}

}
