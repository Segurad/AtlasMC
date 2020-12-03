package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlockMinecart;

public class PacketInUpdateCommandBlockMinecartV1_16_3 extends AbstractPacket implements PacketInUpdateCommandBlockMinecart {

	public PacketInUpdateCommandBlockMinecartV1_16_3() {
		super(0x27, V1_16_3.version);
	}
	
	private int entityID;
	private String cmd;
	private boolean trackoutput;

	@Override
	public void read(int length, DataInput input) throws IOException {
		entityID = readVarInt(input);
		cmd = readString(input);
		trackoutput = input.readBoolean();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

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
