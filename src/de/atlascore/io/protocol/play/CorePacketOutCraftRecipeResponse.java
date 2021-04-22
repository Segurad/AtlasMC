package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutCraftRecipeResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCraftRecipeResponse extends AbstractPacket implements PacketOutCraftRecipeResponse {

	private int windowID;
	private String recipe;
	
	public CorePacketOutCraftRecipeResponse() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutCraftRecipeResponse(int windowID, String recipe) {
		this();
		this.windowID = windowID;
		this.recipe = recipe;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		recipe = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		writeString(recipe, out);
	}

	@Override
	public int getWindowID() {
		return windowID;
	}

	@Override
	public String getRecipe() {
		return recipe;
	}

}
