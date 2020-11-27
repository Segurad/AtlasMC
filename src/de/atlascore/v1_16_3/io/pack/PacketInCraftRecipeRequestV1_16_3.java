package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInCraftRecipeRequest;

public class PacketInCraftRecipeRequestV1_16_3 extends AbstractPacket implements PacketInCraftRecipeRequest {

	public PacketInCraftRecipeRequestV1_16_3() {
		super(0x19, V1_16_3.version);
	}
	
	private byte windowID;
	private String recipe;
	private boolean makeall;

	@Override
	public void read(int length, DataInput input) throws IOException {
		windowID = input.readByte();
		recipe = readString(input);
		makeall = input.readBoolean();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public byte WindowID() {
		return windowID;
	}

	@Override
	public String Recipe() {
		return recipe;
	}

	@Override
	public boolean makeAll() {
		return makeall;
	}
	
	

}
