package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInCraftRecipeRequest;
import io.netty.buffer.ByteBuf;

public class PacketInCraftRecipeRequestV1_16_3 extends AbstractPacket implements PacketInCraftRecipeRequest {

	public PacketInCraftRecipeRequestV1_16_3() {
		super(0x19, V1_16_3.version);
	}
	
	private byte windowID;
	private String recipe;
	private boolean makeall;

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		recipe = readString(in);
		makeall = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		writeString(recipe, out);
		out.writeBoolean(makeall);
	}

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
