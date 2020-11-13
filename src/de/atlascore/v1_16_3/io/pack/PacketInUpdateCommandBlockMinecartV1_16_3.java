package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInUpdateCommandBlockMinecart;

public class PacketInUpdateCommandBlockMinecartV1_16_3 extends AbstractPacket implements PacketInUpdateCommandBlockMinecart {

	public PacketInUpdateCommandBlockMinecartV1_16_3() {
		super(0x27, V1_16_3.version);
	}
	
	private int entityID;
	private String cmd;
	private boolean trackoutput;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		entityID = readVarInt(input);
		cmd = readString(input);
		trackoutput = input.readBoolean();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int EntityID() {
		return entityID;
	}

	@Override
	public String Command() {
		return cmd;
	}

	@Override
	public boolean TrackOutput() {
		return trackoutput;
	}

}
