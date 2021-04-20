package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInCraftRecipeRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInCraftRecipeRequest extends AbstractPacket implements PacketInCraftRecipeRequest {

	public CorePacketInCraftRecipeRequest() {
		super(0x19, CoreProtocolAdapter.VERSION);
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
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public String getRecipe() {
		return recipe;
	}

	@Override
	public boolean getMakeAll() {
		return makeall;
	}
	
	

}
